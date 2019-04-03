package com.dengjinwen.basetool.library.view.chart.chart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.dengjinwen.basetool.library.R;
import com.dengjinwen.basetool.library.view.chart.data.PieChartData;
import com.dengjinwen.basetool.library.view.chart.interfaces.IPieChartData;
import com.dengjinwen.basetool.library.view.chart.render.PieRender;

import java.util.ArrayList;
import java.util.List;

public class PieChart extends Chart<IPieChartData> {

    /**
     * 半径
     */
    private float radius;


    private float textSize;

    private List<PieChartData> mDatas=new ArrayList<>();

    private PieRender renderer;


    public PieChart(Context context) {
        this(context,null);
    }

    public PieChart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PieChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta=getContext().obtainStyledAttributes(attrs, R.styleable.pieChartView,defStyleAttr,0);

        radius=ta.getDimension(R.styleable.pieChartView_radius,radius);
        textSize=ta.getDimension(R.styleable.pieChartView_hintTextSize,textSize);
        ta.recycle();

        initView();
    }

    @Override
    public void setData(ArrayList chartDatas) {

    }

    private void initView() {

        renderer=new PieRender(getContext());
        renderer.setRadius(radius);
        renderer.setTextSize(textSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        renderer.setWidthAndHeight(getWidth(),getHeight());
        renderer.drawAllSectors(canvas,mDatas);
    }

    public void setData(List<PieChartData> pieChartDatas){
        if(mDatas!=null){
            mDatas.clear();
            mDatas.addAll(pieChartDatas);
        }
        invalidate();
    }

    public void setMarkerLineLength(float markerLineLength){
        renderer.setMarkerLineLength(markerLineLength);
    }
}
