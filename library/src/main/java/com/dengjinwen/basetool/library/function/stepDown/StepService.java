package com.dengjinwen.basetool.library.function.stepDown;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.dengjinwen.basetool.library.R;
import com.dengjinwen.basetool.library.function.stepDown.interfaces.IStepDBHelper;
import com.dengjinwen.basetool.library.function.stepDown.interfaces.IStepValuePassListener;
import com.dengjinwen.basetool.library.function.stepDown.interfaces.IUpdateUiCallBack;
import com.dengjinwen.basetool.library.tool.DateUtils;
import com.dengjinwen.basetool.library.tool.log;

import static com.dengjinwen.basetool.library.function.stepDown.SportStepJsonUtils.getCalorieByStep;
import static com.dengjinwen.basetool.library.function.stepDown.SportStepJsonUtils.getDistanceByStep;

public class StepService extends Service implements SensorEventListener {

    public static final String notification="notification";

    /**
     * binder服务与activity交互桥梁
     */
    private LcBinder lcBinder = new LcBinder();
    /**
     * 当前步数
     */
    private int nowStepCount=0;
    /**
     * 传感器对象
     */
    private SensorManager sensorManager;
    /**
     * 计步传感器类型  Sensor.TYPE_STEP_COUNTER或者Sensor.TYPE_STEP_DETECTOR
     */
    private static int stepSensorType = -1;
    /**
     * 加速度传感器中获取的步数
     */
    private StepCount mStepCount;
    /**
     * 数据回调接口，通知上层调用者数据刷新
     */
    private IUpdateUiCallBack mCallback;
    /**
     * 每次第一次启动记步服务时是否从系统中获取了已有的步数记录
     */
    private boolean hasRecord = false;
    /**
     * 系统中获取到的已有的步数
     */
    private int hasStepCount = 0;
    /**
     * 上一次的步数
     */
    private int previousStepCount = 0;

    private IStepDBHelper stepDBHelper;

    private NotificationManager nm;
    private NotificationApiCompat mNotificationApiCompat;
    /**
     * 点击通知栏广播requestCode
     */
    private static final int BROADCAST_REQUEST_CODE = 100;
    private static final String STEP_CHANNEL_ID = "stepChannelId";
    /**
     * 步数通知ID
     */
    private static final int NOTIFY_ID = 1000;

    /**
     * 是否显示通知
     */
    private boolean isNofi=false;

    @Override
    public void onCreate() {
        super.onCreate();
        stepDBHelper=StepDBHelper.factory(getApplicationContext());
        log.e("BindService—onCreate:开启计步");
        new Thread(new Runnable() {
            @Override
            public void run() {
                startStepDetector();
                log.e("BindService—子线程 startStepDetector()");
            }
        }).start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        isNofi=intent.getBooleanExtra(notification,false);
        if(isNofi){
            initNotification(nowStepCount);
        }
        return lcBinder;
    }

    /**
     * 选择计步数据采集的传感器
     * SDK大于等于19，开启计步传感器，小于开启加速度传感器
     */
    private void startStepDetector(){
        if(sensorManager!=null){
            sensorManager=null;
        }
        sensorManager= (SensorManager) getSystemService(SENSOR_SERVICE);
        int versionCodes=Build.VERSION.SDK_INT;
        if(versionCodes>=19){
            //SDK版本大于等于19开启计步传感器
            addCountStepListener();
        }else {
            //小于就使用加速度传感器
            addBasePedometerListener();
        }
    }

    /**
     * 启动计步传感器计步
     */
    private void addCountStepListener(){
        Sensor countSensor=sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        Sensor detectorSensor=sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if(countSensor!=null){
            stepSensorType=Sensor.TYPE_STEP_COUNTER;
            sensorManager.registerListener(StepService.this,
                    countSensor,SensorManager.SENSOR_DELAY_NORMAL);
//        } else if(detectorSensor!=null){
//            stepSensorType=Sensor.TYPE_STEP_DETECTOR;
//            sensorManager.registerListener(StepService.this,detectorSensor,
//                    SensorManager.SENSOR_DELAY_NORMAL);
        }else {  //两种记步传感器都没有则启动加速度传感器计步
            addBasePedometerListener();
        }
    }

    /**
     * 启动加速度传感器计步
     */
    private void addBasePedometerListener(){
        mStepCount=new StepCount();
        long time=System.currentTimeMillis();
        String currrentDay=DateUtils.dateFormat(time,"yyyy-MM-dd");
        StepData front=PreferencesHelper.getLastSensorStep(getApplicationContext());
        if(currrentDay.equals(front.getToday())){  //本地保存最近的数据是当天 设置当天本地保存的步数
            nowStepCount= (int) front.getStep();
        }else {  //不是当天起始步数为0
            nowStepCount=0;
        }
        mStepCount.setSteps(nowStepCount);
        Sensor sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        boolean isAvailable=sensorManager.registerListener(mStepCount.getStepDetector(),
                sensor,SensorManager.SENSOR_DELAY_UI);
        mStepCount.initListener(new IStepValuePassListener() {
            @Override
            public void stepChanged(int steps) {
                long time=System.currentTimeMillis();
                String currrentDay=DateUtils.dateFormat(time,"yyyy-MM-dd");
                nowStepCount=steps;
                StepData stepData=new StepData();
                stepData.setToday(currrentDay);
                stepData.setStep(nowStepCount);
                stepData.setLastSenorStep(-1);
                stepData.setDate(time);
                PreferencesHelper.saveLastSensorStep(getApplicationContext(),stepData);
                updateNotification();
            }
        });
    }

    /**
     * 通知调用者步数更新 数据交互
     */
    private void updateNotification() {
        if (mCallback != null) {
            Log.i("BindService", "数据更新");
            mCallback.updateUi(nowStepCount);
        }
        if(isNofi){
            updateNotification(nowStepCount);
        }
    }

    /**
     * 计步传感器数据变化回调接口
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(stepSensorType==Sensor.TYPE_STEP_COUNTER){  //返回一段时间步数
            //获取当前传感器返回的临时步数
            int tempStep= (int) event.values[0];
            log.e("传感器返回的步数："+tempStep);
            //首次如果没有获取手机系统中已有的步数,则获取一次系统中APP还未开始记步的步数
//            if(!hasRecord){
//                hasRecord=true;
//                hasStepCount=tempStep;
//            }else {
//                //获取APP打开到现在的总步数=本次系统回调的总步数-APP打开之前已有的步数
//                int thisStepCount = tempStep - hasStepCount;
//                //本次有效步数=（APP打开后所记录的总步数-上一次APP打开后所记录的总步数）
//                int thisStep = thisStepCount - previousStepCount;
//                //总步数=现有的步数+本次有效步数
//                nowStepCount += (thisStep);
//                //记录最后一次APP打开到现在的总步数
//                previousStepCount = thisStepCount;

            long time=System.currentTimeMillis();
            String currrentDay=DateUtils.dateFormat(time,"yyyy-MM-dd");
            StepData front=PreferencesHelper.getLastSensorStep(getApplicationContext());
            StepData stepDataNewDay=new StepData();
            stepDataNewDay.setDate(time);
            stepDataNewDay.setToday(currrentDay);
            stepDataNewDay.setLastSenorStep(tempStep);
            if(front!=null){  //APP不是第一次安装
                if(front.getToday().equals(currrentDay)){  //最后一次回调是当天
                    if(front.getLastSenorStep()!=-1){
                        nowStepCount= (int) (front.getStep()+tempStep-front.getLastSenorStep());
                    }else {
                        nowStepCount=DBConstants.ERROR;
                    }
                }else {  //最后一次回调不是当天
                    nowStepCount=DBConstants.ERROR;
                }
            }else {  //APP第一次安装
                nowStepCount=DBConstants.ERROR;
            }
            stepDataNewDay.setStep(nowStepCount);
            PreferencesHelper.saveLastSensorStep(getApplicationContext(),stepDataNewDay);

//            }
        } //else if(stepSensorType==Sensor.TYPE_STEP_DETECTOR){  //返回单个步伐变化
//            if (event.values[0] == 1.0) {
//                nowStepCount++;
//            }
//        }

        updateNotification();
    }


    /**
     * 计步传感器精度变化回调接口
     * @param sensor
     * @param accuracy
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * 数据传递接口
     *
     * @param paramICallback
     */
    public void registerCallback(IUpdateUiCallBack paramICallback) {
        this.mCallback = paramICallback;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //返回START_STICKY ：在运行onStartCommand后service进程被kill后，那将保留在开始状态，但是不保留那些传入的intent。
        // 不久后service就会再次尝试重新创建，因为保留在开始状态，在创建     service后将保证调用onstartCommand。
        // 如果没有传递任何开始命令给service，那将获取到null的intent。
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消前台进程
        stopForeground(true);

    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    /**
     * 绑定回调接口
     */
    public class LcBinder extends Binder {
        public StepService getService() {
            return StepService.this;
        }
    }

    /**
     * 更新通知
     */
    private synchronized void updateNotification(int stepCount) {
        if (null != mNotificationApiCompat) {
            String km = getDistanceByStep(stepCount);
            String calorie = getCalorieByStep(stepCount);
            String contentText = calorie + " 千卡  " + km + " 公里";
            mNotificationApiCompat.updateNotification(NOTIFY_ID, getString(R.string.
                    title_notification_bar, String.valueOf(stepCount)), contentText);
        }
    }

    private synchronized void initNotification(int currentStep) {

        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        int smallIcon = getResources().getIdentifier("icon_step_small", "mipmap", getPackageName());
        if (0 == smallIcon) {
            smallIcon = R.mipmap.ic_launcher;
        }
        String receiverName = getReceiver(getApplicationContext());
        PendingIntent contentIntent = PendingIntent.getBroadcast(this, BROADCAST_REQUEST_CODE, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);
        if (!TextUtils.isEmpty(receiverName)) {
            try {
                contentIntent = PendingIntent.getBroadcast(this, BROADCAST_REQUEST_CODE, new Intent(this, Class.forName(receiverName)), PendingIntent.FLAG_UPDATE_CURRENT);
            } catch (Exception e) {
                e.printStackTrace();
                contentIntent = PendingIntent.getBroadcast(this, BROADCAST_REQUEST_CODE, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);
            }
        }
        String km = getDistanceByStep(currentStep);
        String calorie = getCalorieByStep(currentStep);
        String contentText = calorie + " 千卡  " + km + " 公里";
        int largeIcon = getResources().getIdentifier("ic_launcher", "mipmap", getPackageName());
        Bitmap largeIconBitmap = null;
        if (0 != largeIcon) {
            largeIconBitmap = BitmapFactory.decodeResource(getResources(), largeIcon);
        } else {
            largeIconBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        }
        mNotificationApiCompat = new NotificationApiCompat.Builder(this,
                nm,
                STEP_CHANNEL_ID,
                getString(R.string.step_channel_name),
                smallIcon)
                .setContentIntent(contentIntent)
                .setContentText(contentText)
                .setContentTitle(getString(R.string.title_notification_bar, String.valueOf(currentStep)))
                .setTicker(getString(R.string.app_name))
                .setOngoing(true)
                .setPriority(Notification.PRIORITY_MIN)
                .setLargeIcon(largeIconBitmap)
                .setOnlyAlertOnce(true)
                .builder();
        mNotificationApiCompat.startForeground(this, NOTIFY_ID);
        mNotificationApiCompat.notify(NOTIFY_ID);

    }

    public static String getReceiver(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_RECEIVERS);
            ActivityInfo[] activityInfos = packageInfo.receivers;
            if (null != activityInfos && activityInfos.length > 0) {
                for (int i = 0; i < activityInfos.length; i++) {
                    String receiverName = activityInfos[i].name;
                    Class superClazz = Class.forName(receiverName).getSuperclass();
                    int count = 1;
                    while (null != superClazz) {
                        if (superClazz.getName().equals("java.lang.Object")) {
                            break;
                        }
                        if (superClazz.getName().equals(BaseClickBroadcast.class.getName())) {
                            return receiverName;
                        }
                        if (count > 20) {
                            //用来做容错，如果20个基类还不到Object直接跳出防止while死循环
                            break;
                        }
                        count++;
                        superClazz = superClazz.getSuperclass();

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
