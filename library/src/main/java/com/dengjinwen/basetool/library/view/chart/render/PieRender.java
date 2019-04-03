package com.dengjinwen.basetool.library.view.chart.render;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.TypedValue;

import com.dengjinwen.basetool.library.view.chart.data.PieChartData;

import java.util.List;

public class PieRender {

    /**
     * 标记线长度
     */
    private float markerLineLength=30f;

    /**
     * 半径
     */
    private float radius=180;

    private int width=400;

    private int height=400;

    private Paint textPaint;
    /**
     * 标记文本大小
     */
    private float textSize=13;

    /**
     * 标记文本的高度
     */
    private float textHeight=100;
    private float textBottom;

    /**
     * 饼状图的外切矩形
     */
    private RectF pieChartCircleRectf=new RectF();

    private Context mContext;

    public PieRender(Context context ) {
        mContext=context;
        init();
    }

    private void init(){
        //文本画笔
        textPaint=new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextAlign(Paint.Align.LEFT);

       getHeightAndBottom();
       initRectF();
    }

    /**
     * 初始化饼状图的外切矩形
     */
    private void initRectF() {
        pieChartCircleRectf.left=width/2-radius;
        pieChartCircleRectf.top=height/2-radius;
        pieChartCircleRectf.right=pieChartCircleRectf.left+radius*2;
        pieChartCircleRectf.bottom=pieChartCircleRectf.top+radius*2;
    }

    /**
     * 绘制图形
     */
    public void drawAllSectors(Canvas canvas,List<PieChartData> data){
        float sum1=0f;
        for(PieChartData pieChartData:data){
            sum1+=pieChartData.getValue();
        }

        float sum2=0f;
        for(PieChartData pieChartData:data){
            float startAngel=sum2/sum1*360;
            sum2+=pieChartData.getValue();
            float sweepAngel=pieChartData.getValue()/sum1*360;
            drawSectors(canvas,pieChartData.getColor(),startAngel,sweepAngel);
            drawTag(canvas,pieChartData.getColor(),startAngel+sweepAngel/2,pieChartData.getMarker());
        }
    }


    /**
     * 获取标记文本的高度bottom
     */
    private void getHeightAndBottom(){
        textPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,textSize,
                mContext.getResources().getDisplayMetrics()));
        Paint.FontMetrics fontMetrics=textPaint.getFontMetrics();
        textHeight=fontMetrics.descent-fontMetrics.ascent;
        textBottom=fontMetrics.bottom;
    }

    /**
     * 绘制扇形
     * @param color
     * @param startAngel
     * @param sweepAngel
     */
    private void drawSectors(Canvas canvas,int color,float startAngel,float sweepAngel){

        Paint paint=new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);

        canvas.drawArc(pieChartCircleRectf,startAngel,sweepAngel,true,paint);
    }

    /**
     * 绘制标记
     * @param color
     * @param rotateAngel
     * @param text
     */
    private void drawTag(Canvas canvas,int color,float rotateAngel,String text){

        drawTagLine(canvas,rotateAngel,color);
        drawTagText(canvas,rotateAngel,color,text);
    }

    /**
     * 绘制标记线
     * @param rotateAngel
     */
    private void drawTagLine(Canvas canvas,float rotateAngel,int color){
        Paint paint=new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);

        Path path=new Path();
        path.close();
        path.moveTo(width/2,height/2);
        PointF pointF=getTurningPoint(rotateAngel);
        path.lineTo(pointF.x,pointF.y);
        float landLineX=0f;
        if(rotateAngel>90f&&rotateAngel<270f){
            landLineX=pointF.x-20;
        }else {
            landLineX=pointF.x+20;
        }
        path.lineTo(landLineX,pointF.y);
        canvas.drawPath(path,paint);
    }

    /**
     * 绘制标记文本
     * @param rotateAngel
     * @param color
     */
    private void drawTagText( Canvas canvas,float rotateAngel, int color, String text){
        textPaint.setColor(color);
        PointF pointF=getTurningPoint(rotateAngel);
        if(rotateAngel>90f&&rotateAngel<270f){
            float textWidth=textPaint.measureText(text);
            canvas.drawText(text,pointF.x-20-textWidth,pointF.y+textHeight/2-textBottom,textPaint);
        }else {
            canvas.drawText(text,pointF.x+20,pointF.y+textHeight/2-textBottom,textPaint);
        }
    }

    /**
     * 获取标记线的转折点坐标
     * @return
     */
    private PointF getTurningPoint(float rotateAngel){
        float x= (float) (width/2+(markerLineLength+radius)*Math.cos(Math.toRadians(rotateAngel)));
        float y= (float) (height/2+(markerLineLength+radius)*Math.sin(Math.toRadians(rotateAngel)));
        PointF point=new PointF(x,y);
        return point;
    }

    /**
     * 设置标记文本字体大小
     * @param size
     */
    public void setTextSize(float size){
        textSize=size;
        getHeightAndBottom();
    }

    /**
     * 设置视图宽高
     * @param width
     * @param height
     */
    public void setWidthAndHeight(int width,int height){
        this.width=width;
        this.height=height;
        initRectF();
    }

    public void setRadius(float radius){
        this.radius=radius;
        initRectF();
    }

    public void setMarkerLineLength(float markerLineLength) {
        this.markerLineLength = markerLineLength;
    }
}
