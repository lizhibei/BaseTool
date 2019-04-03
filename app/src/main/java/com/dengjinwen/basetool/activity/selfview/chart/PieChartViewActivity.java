package com.dengjinwen.basetool.activity.selfview.chart;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.dengjinwen.basetool.R;
import com.dengjinwen.basetool.activity.BaseActivity;
import com.dengjinwen.basetool.library.function.screenAdaptation.ScreenAdapterTools;
import com.dengjinwen.basetool.library.view.chart.data.PieChartData;
import com.dengjinwen.basetool.library.view.chart.chart.PieChart;

import java.util.ArrayList;
import java.util.List;

public class PieChartViewActivity extends BaseActivity implements View.OnClickListener {

    private TextView head_text_title;
    private PieChart pie_view;

    private List<PieChartData> data=new ArrayList<>();
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pei_chart_view_activity);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        mContext=this;
        initview();
    }

    private void initview() {
        findViewById(R.id.head_img_left).setOnClickListener(this);
        head_text_title=findViewById(R.id.head_text_title);
        head_text_title.setText("PieChartView");

        pie_view=findViewById(R.id.pie_view);

        data.add(new PieChartData(2.0f,ContextCompat.getColor(mContext,android.R.color.darker_gray),"我的"));
        data.add(new PieChartData(5.0f,ContextCompat.getColor(mContext,android.R.color.black),"他的"));
        data.add(new PieChartData(3.0f,ContextCompat.getColor(mContext,android.R.color.holo_red_dark),"你的"));
        data.add(new PieChartData(6.0f,ContextCompat.getColor(mContext,android.R.color.holo_green_dark),"她的"));

        pie_view.setData(data);
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
