package com.dengjinwen.basetool;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.dengjinwen.basetool.activity.BaseActivity;
import com.dengjinwen.basetool.activity.CreditCardActivity;
import com.dengjinwen.basetool.activity.DialogFactoryActivity;
import com.dengjinwen.basetool.activity.LiveDataDemoActivity;
import com.dengjinwen.basetool.activity.SelectImageAndActivity;
import com.dengjinwen.basetool.activity.StepDownActivity;
import com.dengjinwen.basetool.activity.okhttp.OkHttpDemoActivity;
import com.dengjinwen.basetool.activity.okio.OkioDemoActivity;
import com.dengjinwen.basetool.activity.selfview.SelfViewActivity;
import com.dengjinwen.basetool.adapter.MainAdapter;
import com.dengjinwen.basetool.entity.MainItem;
import com.dengjinwen.basetool.library.function.dialog.product.LoadDialog;
import com.dengjinwen.basetool.library.function.screenAdaptation.ScreenAdapterTools;
import com.dengjinwen.basetool.library.function.zxing.android.BaseToolCaptureActivity;
import com.dengjinwen.basetool.library.function.zxing.common.Constant;
import com.dengjinwen.basetool.library.tool.NotificationsUtils;
import com.dengjinwen.basetool.library.tool.screen.ScreenUitl;
import com.dengjinwen.basetool.library.tool.filter.inputFilter.DecimalNumberInputFilter;
import com.dengjinwen.basetool.library.tool.log;
import com.dengjinwen.basetool.library.tool.screen.StatusBarUtil;
import com.dengjinwen.basetool.library.tool.singleClick.MySingleClick;
import com.dengjinwen.basetool.util.FilePicker;

import java.util.ArrayList;

public class MainActivity extends BaseActivity  {

    private ListView list_lv;
    private ImageView head_img_left;
    private TextView head_text_title;

    private Context mContext;
    private ArrayList<MainItem> data=new ArrayList<>();
    private MainAdapter adapter;
    private final int QR_CODE=11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
//        StatusBarUtil.setStatusBar(this, ContextCompat.getColor(this,R.color.white));
        mContext=this;
        initData();
        initview();

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @MySingleClick
            @Override
            public void onClick(View v) {
                log.e("11111执行了");
            }
        });


        if(!NotificationsUtils.isNotificationEnabled(mContext)){
            NotificationsUtils.toNotificationSetting(this);
        }

        String url=BuildConfig.SERVER_URL;
    }


    private void initview() {

        head_img_left= (ImageView) findViewById(R.id.head_img_left);
        head_img_left.setVisibility(View.GONE);
        head_text_title= (TextView) findViewById(R.id.head_text_title);
        head_text_title.setText("Demo");

        list_lv= (ListView) findViewById(R.id.list_lv);
        adapter=new MainAdapter(mContext,data);
        list_lv.setAdapter(adapter);
        list_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                click(position);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == QR_CODE) {
                String scanResult = data.getStringExtra(Constant.CODED_CONTENT);
                log.e("scanResult:" + scanResult);
            }
        }

        if (requestCode == NotificationsUtils.GET_PER) {
            log.e("获取权限回调");
        }
    }

    private void initData() {
        MainItem selfview=new MainItem("自定义View","",0);
        data.add(selfview);
        MainItem fileS=new MainItem("文件系统","文件系统实例",1);
        data.add(fileS);
        MainItem selectImage=new MainItem("选择本地图片","选择本地图片实例",2);
        data.add(selectImage);
        MainItem dialogFactory=new MainItem("DialogFactory","对话框工厂",3);
        data.add(dialogFactory);
        MainItem stepDown=new MainItem("StepService","本地记步",4);
        data.add(stepDown);
        MainItem okhttp=new MainItem("OkHttp","",5);
        data.add(okhttp);
        MainItem okio=new MainItem("okio","",6);
        data.add(okio);
        MainItem liveData=new MainItem("LiveData","",7);
        data.add(liveData);
    }

    private void click(int position){
        switch (data.get(position).getId()){
            //自定义View
            case 0:
                startIntent(mContext,SelfViewActivity.class);
                break;
                //文件系统
            case 1:
                new FilePicker().withActivity(this)
                        .withRequestCode(0)
                        .withHiddenFiles(true)
                        .withTitle("Sample title")
                        .start();
                break;
                //选择本地图片
            case 2:
                startIntent(mContext,SelectImageAndActivity.class);
                break;
                //对话框工厂
            case 3:
                startIntent(mContext,DialogFactoryActivity.class);
                break;
                //本地记步
            case 4:
                startIntent(mContext,StepDownActivity.class);
                break;
                //okhttp
            case 5:
                startIntent(mContext, OkHttpDemoActivity.class);
                break;
                //
            case 6:
                startIntent(mContext, OkioDemoActivity.class);
                break;
                //
            case 7:
                startIntent(mContext, LiveDataDemoActivity.class);
                break;
            case 8:
                break;

        }
    }


}
