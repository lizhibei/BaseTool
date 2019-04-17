package com.dengjinwen.basetool.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.dengjinwen.basetool.R;
import com.dengjinwen.basetool.library.function.screenAdaptation.ScreenAdapterTools;
import com.dengjinwen.basetool.library.function.stepDown.StepService;
import com.dengjinwen.basetool.library.function.stepDown.interfaces.IUpdateUiCallBack;

/**
 * 本地记步
 */
public class StepDownActivity extends BaseActivity implements View.OnClickListener {
    private TextView head_text_title,step_count_tv;

    private Context mContext;
    private StepService stepService;
    private boolean isBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_down_acitivity);
        mContext=this;
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        initview();
    }

    private void initview() {
        findViewById(R.id.head_img_left).setOnClickListener(this);
        head_text_title=findViewById(R.id.head_text_title);
        head_text_title.setText("记步");

        step_count_tv=findViewById(R.id.step_count_tv);

        Intent intent=new Intent(mContext,StepService.class);
        intent.putExtra(StepService.notification,true);
        isBind=bindService(intent,serviceConnection,Context.BIND_AUTO_CREATE);
        startService(intent);
    }

    /**
     * 和绷定服务数据交换的桥梁，可以通过IBinder service获取服务的实例来调用服务的方法或者数据
     */
    private ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            StepService.LcBinder lcBinder= (StepService.LcBinder) service;
            stepService=lcBinder.getService();
            stepService.registerCallback(new IUpdateUiCallBack() {
                @Override
                public void updateUi(int stepCount) {
                    Message message = Message.obtain();
                    message.what = 1;
                    message.arg1 = stepCount;
                    handler.sendMessage(message);
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private Handler.Callback callback=new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 1) {
                step_count_tv.setText(msg.arg1 + "");
            }
            return false;
        }
    };
    private Handler handler =new Handler(callback);

    @Override
    public void onDestroy() {  //app被关闭之前，service先解除绑定
        super.onDestroy();
        if (isBind) {
            this.unbindService(serviceConnection);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.head_img_left:
                finish();
                break;
        }
    }
}
