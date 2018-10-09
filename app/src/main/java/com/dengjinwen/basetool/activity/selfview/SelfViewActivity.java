package com.dengjinwen.basetool.activity.selfview;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.dengjinwen.basetool.R;
import com.dengjinwen.basetool.activity.BaseActivity;
import com.dengjinwen.basetool.adapter.MainAdapter;
import com.dengjinwen.basetool.entity.MainItem;

import java.util.ArrayList;

public class SelfViewActivity extends BaseActivity implements View.OnClickListener{
    private TextView head_text_title;
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
        MainItem circleimageview=new MainItem("CircleImageView","");
        data.add(circleimageview);
    }

    private void initview() {
        findViewById(R.id.head_img_left).setOnClickListener(this);
        head_text_title=findViewById(R.id.head_text_title);
        head_text_title.setText("自定义View");

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
                //CircleImageView
            case 5:
                startIntent(mContext,CircleImageViewDemoActivity.class);
                break;
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
