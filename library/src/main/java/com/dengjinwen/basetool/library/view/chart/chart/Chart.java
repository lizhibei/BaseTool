package com.dengjinwen.basetool.library.view.chart.chart;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.dengjinwen.basetool.library.view.chart.interfaces.IChartData;

import java.util.ArrayList;

/**
 * 图标基类
 */
public abstract class Chart<T extends IChartData> extends View {

    /**
     * 去除padding的宽高
     */
    protected int mWidth,mHeight;

    /**
     * 视图宽高
     */
    private int mViewWidth,mViewHeight;

    /**
     * 图标数据
     */
    private ArrayList<T> mDatas=new ArrayList<>();

    public Chart(Context context) {
        this(context,null);
    }

    public Chart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Chart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mViewWidth=w;
        mViewHeight=h;

        mWidth=mViewWidth-getPaddingLeft()-getPaddingRight();
        mHeight=mViewHeight-getPaddingTop()-getPaddingBottom();
    }

    public abstract void setData(ArrayList<T> chartDatas);
}
