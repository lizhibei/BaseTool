package com.dengjinwen.basetool;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dengjinwen.basetool.activity.AnimViewDemoActivity;
import com.dengjinwen.basetool.activity.BannerLayoutActivity;
import com.dengjinwen.basetool.activity.BaseActivity;
import com.dengjinwen.basetool.activity.BottomNavigationActivity;
import com.dengjinwen.basetool.activity.CheckViewDemoActivity;
import com.dengjinwen.basetool.activity.CreditCardActivity;
import com.dengjinwen.basetool.activity.NumberProgressBarActivity;
import com.dengjinwen.basetool.activity.SelectImageAndActivity;
import com.dengjinwen.basetool.adapter.MainAdapter;
import com.dengjinwen.basetool.entity.MainItem;
import com.dengjinwen.basetool.util.FilePicker;

import java.util.ArrayList;

public class MainActivity extends BaseActivity  {

    private ListView list_lv;

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
    }


    private void initview() {

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
        MainItem bootonNar=new MainItem("BottomNavigation","底部导航栏");
        data.add(bootonNar);
        MainItem numberProgress=new MainItem("NumberProgress","数字进度条");
        data.add(numberProgress);
        MainItem bannerLayout=new MainItem("BannerLayout","轮播图");
        data.add(bannerLayout);
        MainItem checkView=new MainItem("CheckView","实现Checkable的自定义视图");
        data.add(checkView);
        MainItem animView=new MainItem("AnimView","实现ViewAnimationUtils动画的自定义View");
        data.add(animView);
        MainItem credit=new MainItem("CreditCard","信用卡账单");
        data.add(credit);
        MainItem fileS=new MainItem("文件系统","文件系统实例");
        data.add(fileS);
        MainItem selectImage=new MainItem("选择本地图片","选择本地图片实例");
        data.add(selectImage);
    }

    private void click(int position){
        switch (position){
            //底部导航栏
            case 0:
                startIntent(mContext,BottomNavigationActivity.class);
                break;
                //数字进度条
            case 1:
                startIntent(mContext,NumberProgressBarActivity.class);
                break;
                //轮播图
            case 2:
                startIntent(mContext,BannerLayoutActivity.class);
                break;
                //实现Checkable的自定义视图
            case 3:
                startIntent(mContext,CheckViewDemoActivity.class);
                break;
                //实现ViewAnimationUtils动画的自定义View
            case 4:
                startIntent(mContext,AnimViewDemoActivity.class);
                break;
                //信用卡账单
            case 5:
                startIntent(mContext,CreditCardActivity.class);
                break;
                //文件系统
            case 6:
                new FilePicker().withActivity(this)
                        .withRequestCode(0)
                        .withHiddenFiles(true)
                        .withTitle("Sample title")
                        .start();
                break;
                //选择本地图片
            case 7:
                startIntent(mContext,SelectImageAndActivity.class);
                break;
        }
    }


}
