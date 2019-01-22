package com.dengjinwen.basetool;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dengjinwen.basetool.activity.BaseActivity;
import com.dengjinwen.basetool.activity.CreditCardActivity;
import com.dengjinwen.basetool.activity.DialogFactoryActivity;
import com.dengjinwen.basetool.activity.SelectImageAndActivity;
import com.dengjinwen.basetool.activity.selfview.SelfViewActivity;
import com.dengjinwen.basetool.adapter.MainAdapter;
import com.dengjinwen.basetool.entity.MainItem;
import com.dengjinwen.basetool.util.FilePicker;

import java.util.ArrayList;

public class MainActivity extends BaseActivity  {

    private ListView list_lv;
    private ImageView head_img_left;
    private TextView head_text_title;

    private Context mContext;
    private ArrayList<MainItem> data=new ArrayList<>();
    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;
        initData();
        initview();

//        DisplayMetrics dm=new DisplayMetrics();
//        WindowManager windowManager= (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
//        windowManager.getDefaultDisplay().getMetrics(dm);
//        log.e("density:"+dm.density);
    }


    private void initview() {

        head_img_left=findViewById(R.id.head_img_left);
        head_img_left.setVisibility(View.GONE);
        head_text_title=findViewById(R.id.head_text_title);
        head_text_title.setText("Demo");

        list_lv=  findViewById(R.id.list_lv);
        adapter=new MainAdapter(mContext,data);
        list_lv.setAdapter(adapter);
        list_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                click(position);
            }
        });

    }

    private void initData() {
        MainItem selfview=new MainItem("自定义View","");
        data.add(selfview);
        MainItem credit=new MainItem("CreditCard","信用卡账单");
        data.add(credit);
        MainItem fileS=new MainItem("文件系统","文件系统实例");
        data.add(fileS);
        MainItem selectImage=new MainItem("选择本地图片","选择本地图片实例");
        data.add(selectImage);
        MainItem dialogFactory=new MainItem("DialogFactory","对话框工厂");
        data.add(dialogFactory);
    }

    private void click(int position){
        switch (position){
            //自定义View
            case 0:
                startIntent(mContext,SelfViewActivity.class);
                break;
                //信用卡账单
            case 1:
                startIntent(mContext,CreditCardActivity.class);
                break;
                //文件系统
            case 2:
                new FilePicker().withActivity(this)
                        .withRequestCode(0)
                        .withHiddenFiles(true)
                        .withTitle("Sample title")
                        .start();
                break;
                //选择本地图片
            case 3:
                startIntent(mContext,SelectImageAndActivity.class);
                break;
                //对话框工厂
            case 4:
                startIntent(mContext,DialogFactoryActivity.class);
                break;
        }
    }


}
