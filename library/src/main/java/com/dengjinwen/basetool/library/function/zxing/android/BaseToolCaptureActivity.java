package com.dengjinwen.basetool.library.function.zxing.android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dengjinwen.basetool.library.R;
import com.dengjinwen.basetool.library.function.screenAdaptation.ScreenAdapterTools;
import com.dengjinwen.basetool.library.function.selectImage.AndSelectImage;
import com.dengjinwen.basetool.library.function.selectImage.ItemEntity;
import com.dengjinwen.basetool.library.function.zxing.bean.ZxingConfig;
import com.dengjinwen.basetool.library.function.zxing.camera.CameraManager;
import com.dengjinwen.basetool.library.function.zxing.common.Constant;
import com.dengjinwen.basetool.library.function.zxing.decode.DecodeImgCallback;
import com.dengjinwen.basetool.library.function.zxing.decode.DecodeImgThread;
import com.dengjinwen.basetool.library.function.zxing.decode.ImageUtil;
import com.dengjinwen.basetool.library.function.zxing.view.ViewfinderView;
import com.dengjinwen.basetool.library.tool.ImageProgressTool;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;


/**
 *
 *
 * 扫一扫
 */

public class BaseToolCaptureActivity extends AppCompatActivity implements SurfaceHolder.Callback, View.OnClickListener {

    private final static int SELECT_IMAGE=20001;

    private TextView head_text_title,open_or_close,right_tv;
    private SurfaceView previewView;
    private ViewfinderView viewfinderView;
    private ImageView flashLight_iv;

    private static final String TAG = BaseToolCaptureActivity.class.getSimpleName();
    public ZxingConfig config;
    private boolean hasSurface;
    private InactivityTimer inactivityTimer;
    private BeepManager beepManager;
    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private SurfaceHolder surfaceHolder;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public static Intent createIntent(Context context){
        Intent intent=new Intent(context,BaseToolCaptureActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configuration();
        setContentView(R.layout.activity_scanner);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        initView();
    }

    private void initView() {
        previewView = findViewById(R.id.preview_view);
        previewView.setOnClickListener(this);

        viewfinderView = findViewById(R.id.viewfinder_content);
        viewfinderView.setZxingConfig(config);


        findViewById(R.id.head_img_left).setOnClickListener(this);
        head_text_title=findViewById(R.id.head_text_title);
        head_text_title.setText("");

        right_tv=findViewById(R.id.right_tv);
        right_tv.setVisibility(View.VISIBLE);
        right_tv.setOnClickListener(this);
        right_tv.setText("相册");

        findViewById(R.id.open_sdt).setOnClickListener(this);

        flashLight_iv = findViewById(R.id.flashLight_iv);
        open_or_close = findViewById(R.id.open_or_close);


        hasSurface = false;

        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);
        beepManager.setPlayBeep(config.isPlayBeep());
        beepManager.setVibrate(config.isShake());
    }

    /**
     * 配置信息
     */
    private void configuration(){
        // 保持Activity处于唤醒状态
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.BLACK);
        }

        /*先获取配置信息*/
        try {
            config = (ZxingConfig) getIntent().getExtras().get(Constant.INTENT_ZXING_CONFIG);
        } catch (Exception e) {
        }
        if (config == null) {
            config = new ZxingConfig();
        }
    }

    /**
     * 扫码二维码图片
     * @return
     */
    private Result scanningImage(String path){
        if(path==null){
            return null;
        }

        Hashtable<DecodeHintType,String> hints=new Hashtable<>();
        hints.put(DecodeHintType.CHARACTER_SET,"UTF-8");

        Bitmap scanBitmap = ImageProgressTool.getImage(path);
        int[] data=new int[scanBitmap.getWidth()*scanBitmap.getHeight()];
        scanBitmap.getPixels(data,0,scanBitmap.getWidth(),0,0,scanBitmap.getWidth(),scanBitmap.getHeight());
        RGBLuminanceSource source = new RGBLuminanceSource(scanBitmap.getWidth(),scanBitmap.getHeight(),data);
        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();

        try {
            return reader.decode(bitmap1,hints);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * @param pm
     * @return 是否有闪光灯
     */
    public static boolean isSupportCameraLedFlash(PackageManager pm) {
        if (pm != null) {
            FeatureInfo[] features = pm.getSystemAvailableFeatures();
            if (features != null) {
                for (FeatureInfo f : features) {
                    if (f != null && PackageManager.FEATURE_CAMERA_FLASH.equals(f.name)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param flashState 切换闪光灯图片
     */
    public void switchFlashImg(int flashState) {

        if (flashState == Constant.FLASH_OPEN) {
//            flashLight_iv.setImageResource(R.drawable.ic_open);
            open_or_close.setText("关闭闪光灯");
        } else {
//            flashLight_iv.setImageResource(R.drawable.ic_close);
            open_or_close.setText("打开闪光灯");
        }

    }

    /**
     * @param rawResult 返回的扫描结果
     */
    public void handleDecode(Result rawResult) {

        inactivityTimer.onActivity();

        beepManager.playBeepSoundAndVibrate();

        Intent intent = getIntent();
        intent.putExtra(Constant.CODED_CONTENT, rawResult.getText());
        setResult(RESULT_OK, intent);
        this.finish();


    }


    private void switchVisibility(View view, boolean b) {
        if (b) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        cameraManager = new CameraManager(getApplication(), config);

        viewfinderView.setCameraManager(cameraManager);
        handler = null;

        surfaceHolder = previewView.getHolder();
        if (hasSurface) {

            initCamera(surfaceHolder);
        } else {
            // 重置callback，等待surfaceCreated()来初始化camera
            surfaceHolder.addCallback(this);
        }

        beepManager.updatePrefs();
        inactivityTimer.onResume();

    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            return;
        }
        try {
            // 打开Camera硬件设备
            cameraManager.openDriver(surfaceHolder);
            // 创建一个handler来打开预览，并抛出一个运行时异常
            if (handler == null) {
                handler = new CaptureActivityHandler(this, cameraManager);
            }
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
            displayFrameworkBugMessageAndExit();
        } catch (RuntimeException e) {
            Log.w(TAG, "Unexpected error initializing camera", e);
            displayFrameworkBugMessageAndExit();
        }
    }

    private void displayFrameworkBugMessageAndExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("扫一扫");
        builder.setMessage(getString(R.string.msg_camera_framework_bug));
        builder.setPositiveButton(R.string.button_ok, new FinishListener(this));
        builder.setOnCancelListener(new FinishListener(this));
        builder.show();
    }

    @Override
    protected void onPause() {

        Log.i("CaptureActivity","onPause");
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
        beepManager.close();
        cameraManager.closeDriver();

        if (!hasSurface) {

            surfaceHolder.removeCallback(this);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(resultCode==RESULT_OK){
            Bundle bundle=intent.getExtras();
            if(requestCode==Constant.REQUEST_IMAGE){
                String path = ImageUtil.getImageAbsolutePath(this, intent.getData());

                new DecodeImgThread(path, new DecodeImgCallback() {
                    @Override
                    public void onImageDecodeSuccess(Result result) {
                        handleDecode(result);
                    }

                    @Override
                    public void onImageDecodeFailed() {
                        Toast.makeText(BaseToolCaptureActivity.this, "抱歉，解析失败,换个图片试试.", Toast.LENGTH_SHORT).show();
                    }
                }).run();
            }else if(requestCode==SELECT_IMAGE){  //相册选择图片返回
                ArrayList<ItemEntity> list=bundle.getParcelableArrayList(AndSelectImage.SELECT_IMAGE);
                if(list!=null&&list.size()>0){
                    ItemEntity id=list.get(0);
                    Result result=scanningImage(id.getPath());
                    handleDecode(result);
                }
            }
        }
    }


    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.head_img_left){
            finish();
        }else if(view.getId()==R.id.open_sdt){
            cameraManager.switchFlashLight(handler);
        }else if(view.getId()==R.id.right_tv){  //选择图片
            new AndSelectImage().withActivity(this)
                    .withNumber(1)
                    .withRequestCode(SELECT_IMAGE)
                    .withType(AndSelectImage.TYPE_IMAGE)
                    .start();
        }
    }
}
