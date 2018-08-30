package com.dengjinwen.basetool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dengjinwen.basetool.activity.BannerLayoutActivity;
import com.dengjinwen.basetool.activity.BaseActivity;
import com.dengjinwen.basetool.activity.BottomNavigationActivity;
import com.dengjinwen.basetool.activity.CheckViewDemoActivity;
import com.dengjinwen.basetool.activity.NumberProgressBarActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
    }

    private void initview() {
        findViewById(R.id.bottom_navigation_tv).setOnClickListener(this);
        findViewById(R.id.number_progress_bar_tv).setOnClickListener(this);
        findViewById(R.id.bannerlayout_tv).setOnClickListener(this);
        findViewById(R.id.checkview_tv).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //
            case R.id.bottom_navigation_tv:
                Intent toBottomNavigationActivity=new Intent(this,BottomNavigationActivity.class);
                startActivity(toBottomNavigationActivity);
                break;
                //
            case R.id.number_progress_bar_tv:
                Intent toNumberProgressBarActivity=new Intent(this,NumberProgressBarActivity.class);
                startActivity(toNumberProgressBarActivity);
                break;
                //
            case R.id.bannerlayout_tv:
                Intent toBannerLayoutActivity=new Intent(this, BannerLayoutActivity.class);
                startActivity(toBannerLayoutActivity);
                break;
                //
            case R.id.checkview_tv:
                Intent toCheckViewDemoActivity=new Intent(this,CheckViewDemoActivity.class);
                startActivity(toCheckViewDemoActivity);
                break;
        }
    }
}
