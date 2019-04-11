package com.dengjinwen.basetool.library.view.chart.render;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import com.dengjinwen.basetool.library.view.chart.interfaces.iData.IPieAxisData;
import com.dengjinwen.basetool.library.view.chart.interfaces.iData.IPieData;
import com.dengjinwen.basetool.library.view.chart.interfaces.listener.TouchListener;

import java.text.NumberFormat;

public class PieChartRender extends ChartRender implements TouchListener {

    private IPieAxisData pieAxisData;
    private IPieData pieData;

    private Paint mPaint=new Paint();
    private Paint textPaint=new Paint();

    private Path outPath=new Path(),midPath=new Path(),inPath=new Path();
    private Path outMidPath=new Path(),midInPath=new Path();

    /**
     * 绘制扇形的度数
     */
    private float drawAngle;

    /**
     * 是否触摸放大
     */
    private boolean touchFlag=false;

    private NumberFormat numberFormat;

    /**
     * 标记线长度
     */
    private float markerLineLength=30f;


    /**
     * 标记文本大小
     */
    private float textSize=13;

    /**
     * 标记文本的高度
     */
    private float textHeight=100;
    private float textBottom;


    public PieChartRender(IPieAxisData iPieAxisData,IPieData iPieData ) {
        this.pieAxisData=iPieAxisData;
        this.pieData=iPieData;

        mPaint.setAntiAlias(true);
        mPaint.setDither(true);

        textPaint.setAntiAlias(true);
        textPaint.setDither(true);
        textPaint.setColor(pieData.getColor());
        textPaint.setTextSize(pieData.getTextSize());
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setStrokeWidth(pieAxisData.getPaintWidth());

        numberFormat=NumberFormat.getPercentInstance();
        numberFormat.setMinimumFractionDigits(pieAxisData.getDecimalPlaces());
    }


    @Override
    public void drawGraph(Canvas canvas, float animatedValue) {

        /**
         * 绘制视图
         */
        if(Math.min(pieData.getAngle()-1,animatedValue-pieData.getCurrentAngle())>=0){
            drawAngle=Math.min(pieData.getAngle()-1,animatedValue-pieData.getCurrentAngle());
        }else {
            drawAngle=0;
        }

        if(touchFlag){
            drawArc(canvas,pieData.getCurrentAngle(),drawAngle,pieData,pieAxisData.getOffsetRectFs()[0],
                    pieAxisData.getOffsetRectFs()[1],pieAxisData.getOffsetRectFs()[2],mPaint);
        }else {
            drawArc(canvas,pieData.getCurrentAngle(),drawAngle,pieData,pieAxisData.getRectFs()[0],
                    pieAxisData.getRectFs()[1],pieAxisData.getRectFs()[2],mPaint);
        }
    }

    /**
     * 在饼状图里面绘制文本
     * @param canvas
     * @param animatedValue
     */
    public void drawGraphText(Canvas canvas,float animatedValue){
        if(pieAxisData.getIsTextSize()&&animatedValue>pieData.getCurrentAngle()+pieData.getAngle()/2){
            if(touchFlag){

            } else {
                if(pieData.getAngle()>pieAxisData.getMinAngle()){

                }
            }
        }
    }


    /**
     * 绘制扇形
     * @param canvas
     * @param currentStartAnge  开始角度
     * @param drawAngle  扇形的度数
     * @param pie  扇形数据
     * @param outRectf  最外层矩形
     * @param midRectf  中间层矩形
     * @param inRectf  里面层矩形
     * @param paint
     */
    private void drawArc(Canvas canvas,float currentStartAnge,float drawAngle,IPieData pie,RectF outRectf,
                         RectF midRectf,RectF inRectf,Paint paint){
        outPath.moveTo(0,0);
        outPath.arcTo(outRectf,currentStartAnge,drawAngle);
        midPath.moveTo(0,0);
        midPath.arcTo(midRectf,currentStartAnge,drawAngle);
        inPath.moveTo(0,0);
        inPath.arcTo(inRectf,currentStartAnge,drawAngle);
        outMidPath.op(outPath,midPath,Path.Op.DIFFERENCE);
        midInPath.op(midPath,inPath,Path.Op.DIFFERENCE);
        paint.setColor(pie.getColor());
        canvas.drawPath(outMidPath,paint);
        paint.setAlpha(0x80); //设置透明度
        canvas.drawPath(midInPath,paint);
        outPath.reset();
        midPath.reset();
        inPath.reset();
        outMidPath.reset();
        midInPath.reset();
    }

    private void drawText(Canvas canvas, IPieData pie, float currentStartAngle, NumberFormat numberFormat,boolean flag){

    }
    /**
     * 绘制图形
     */
//    public void drawAllSectors(Canvas canvas,List<IPieData> data){
//        float sum1=0f;
//        for(IPieData pieChartData:data){
//            sum1+=pieChartData.getValue();
//        }
//
//        float sum2=0f;
//        for(IPieData pieChartData:data){
//            float startAngel=sum2/sum1*360;
//            sum2+=pieChartData.getValue();
//            float sweepAngel=pieChartData.getValue()/sum1*360;
//            drawSectors(canvas,pieChartData.getColor(),startAngel,sweepAngel);
//            drawTag(canvas,pieChartData.getColor(),startAngel+sweepAngel/2,pieChartData.getMarker());
//        }
//    }


    /**
     * 获取标记文本的高度bottom
     */
//    private void getHeightAndBottom(){
//        textPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,textSize,
//                mContext.getResources().getDisplayMetrics()));
//        Paint.FontMetrics fontMetrics=textPaint.getFontMetrics();
//        textHeight=fontMetrics.descent-fontMetrics.ascent;
//        textBottom=fontMetrics.bottom;
//    }

    /**
     * 绘制扇形
     * @param color
     * @param startAngel
     * @param sweepAngel
     */
//    private void drawSectors(Canvas canvas,int color,float startAngel,float sweepAngel){
//
//        Paint paint=new Paint();
//        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
//        paint.setStyle(Paint.Style.FILL);
//        paint.setColor(color);
//
//        canvas.drawArc(pieChartCircleRectf,startAngel,sweepAngel,true,paint);
//    }

    /**
     * 绘制标记
     * @param color
     * @param rotateAngel
     * @param text
     */
//    private void drawTag(Canvas canvas,int color,float rotateAngel,String text){
//
//        drawTagLine(canvas,rotateAngel,color);
//        drawTagText(canvas,rotateAngel,color,text);
//    }

    /**
     * 绘制标记线
     * @param rotateAngel
     */
//    private void drawTagLine(Canvas canvas,float rotateAngel,int color){
//        Paint paint=new Paint();
//        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setColor(color);
//
//        Path path=new Path();
//        path.close();
////        path.moveTo(width/2,height/2);
//        PointF pointF=getTurningPoint(rotateAngel);
//        path.lineTo(pointF.x,pointF.y);
//        float landLineX=0f;
//        if(rotateAngel>90f&&rotateAngel<270f){
//            landLineX=pointF.x-20;
//        }else {
//            landLineX=pointF.x+20;
//        }
//        path.lineTo(landLineX,pointF.y);
//        canvas.drawPath(path,paint);
//    }

    /**
     * 绘制标记文本
     * @param rotateAngel
     * @param color
     */
//    private void drawTagText( Canvas canvas,float rotateAngel, int color, String text){
//        textPaint.setColor(color);
//        PointF pointF=getTurningPoint(rotateAngel);
//        if(rotateAngel>90f&&rotateAngel<270f){
//            float textWidth=textPaint.measureText(text);
//            canvas.drawText(text,pointF.x-20-textWidth,pointF.y+textHeight/2-textBottom,textPaint);
//        }else {
//            canvas.drawText(text,pointF.x+20,pointF.y+textHeight/2-textBottom,textPaint);
//        }
//    }

    /**
     * 获取标记线的转折点坐标
     * @return
     */
//    private PointF getTurningPoint(float rotateAngel){
//        float x= (float) (width/2+(markerLineLength+radius)*Math.cos(Math.toRadians(rotateAngel)));
//        float y= (float) (height/2+(markerLineLength+radius)*Math.sin(Math.toRadians(rotateAngel)));
//        PointF point=new PointF(x,y);
//        return point;
//    }


    public void setMarkerLineLength(float markerLineLength) {
        this.markerLineLength = markerLineLength;
    }

    @Override
    public void setTouchFlag(boolean touchFlag) {
        this.touchFlag=touchFlag;
    }
}
