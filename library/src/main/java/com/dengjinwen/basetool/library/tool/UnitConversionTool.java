package com.dengjinwen.basetool.library.tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

/**
 * 单位换算
 */
public class UnitConversionTool {

    private static final int COLORDRAWABLE_DIMENSION = 2;
    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;

    /**
     * dip  转px
     * @param context
     * @param dpValue
     * @return
     */
    public static float dip2px(Context context,float dpValue){
        return dp2px(context,dpValue);
    }

    /**
     * dp 转 px
     * @param context
     * @param dpValue
     * @return
     */
    public static float dp2px(Context context,float dpValue){
        final float scale=context.getResources().getDisplayMetrics().density;
        return dpValue*scale+0.5f;
    }

    /**
     * px  转  dip
     * @param context
     * @param pxValue
     * @return
     */
    public static float px2dip(Context context, float pxValue){
        return px2dp(context,pxValue);
    }

    /**
     * px 转 dp
     * @param context
     * @param pxValue
     * @return
     */
    public static float px2dp(Context context,float pxValue){
        final float scale=context.getResources().getDisplayMetrics().density;
        return pxValue/scale+0.5f;
    }

    /**
     * sp  装px
     * @param context
     * @param spValue
     * @return
     */
    public static float sp2px(Context context,float spValue){
        final float fontScale=context.getResources().getDisplayMetrics().scaledDensity;
        return spValue*fontScale+0.5f;
    }

    /**
     * px 转sp
     * @param context
     * @param pxValue
     * @return
     */
    public static float px2sp(Context context,float pxValue){
        final float fontScale=context.getResources().getDisplayMetrics().scaledDensity;
        return pxValue/fontScale+0.5f;
    }

    /**
     * Drawable转化为Bitmap
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable){
        if(drawable==null){
            return null;
        }

        if(drawable instanceof BitmapDrawable){
            return ((BitmapDrawable)drawable).getBitmap();
        }

        try {
            Bitmap bitmap;
            if(drawable instanceof ColorDrawable){
                bitmap=Bitmap.createBitmap(COLORDRAWABLE_DIMENSION,COLORDRAWABLE_DIMENSION,BITMAP_CONFIG);
            }else {
                bitmap=Bitmap.createBitmap(drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight(),BITMAP_CONFIG);
            }
            Canvas canvas=new Canvas(bitmap);
            drawable.setBounds(0,0,canvas.getWidth(),canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
