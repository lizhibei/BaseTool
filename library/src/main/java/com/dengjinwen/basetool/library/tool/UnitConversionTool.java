package com.dengjinwen.basetool.library.tool;

import android.content.Context;

/**
 * 单位换算
 */
public class UnitConversionTool {
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
}
