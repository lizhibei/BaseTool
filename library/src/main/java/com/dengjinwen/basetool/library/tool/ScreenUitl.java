package com.dengjinwen.basetool.library.tool;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.lang.reflect.Method;

public class ScreenUitl {

    /**
     * 获取屏幕宽度
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager windowmanager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = windowmanager.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 得到屏幕高度
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;

        return height;
    }

    /**
     * 获得状态栏高度
     * @param context
     * @return
     */
    public int getStatusBarHeight(Context context){
        int result=0;
        int resourceId=context.getResources().getIdentifier("status_bar_height",
                "dimen","android");
        if(resourceId>0){
            result =context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    /**
     * 获取虚拟导航键的高度
     * @param context
     * @return
     */
    public static int getVirtualBarHeight(Context context) {
        int vh = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            @SuppressWarnings("rawtypes")
            Class c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            vh = dm.heightPixels - display.getHeight();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }

    public enum StatusBarStyle{
        COLOR,
        IMAGE
    }

    /**
     * 设置头部状态栏
     * @param context
     * @param view
     * @param color
     * @param style tag为1时头部是正常的白色标题栏头部；tag为2时头部是图片
     */
    public void setHead(Activity context, View view, int color, StatusBarStyle style) {
        //5.0及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = context.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            context.getWindow().setStatusBarColor(context.getResources().getColor(color));
            if (style == StatusBarStyle.COLOR) {
                LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) view.getLayoutParams();
                linearParams.height = getVirtualBarHeight(context) + 5;
                view.setBackgroundColor(context.getResources().getColor(color));
            } else if (style == StatusBarStyle.IMAGE) {
                //设置距离顶部margin值
                setMargins(view, 0, 50, 0, 0);
            }
        }
    }

    /**
     * 设置头部margin值
     */
    private void setMargins(View v, int l, int t, int r, int b) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
                p.setMargins(l, t, r, b);
                v.requestLayout();
            }
        }
    }
}
