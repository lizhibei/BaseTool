package com.dengjinwen.basetool.library.tool.screen;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * 状态栏和导航栏的显示隐藏设置
 * View.SYSTEM_UI_FLAG_LOW_PROFILE  设置状态栏和导航栏中的图标变小，变模糊或者弱化其效果
 * View.SYSTEM_UI_FLAG_HIDE_NAVIGATION 隐藏导航栏，点击屏幕任意区域，导航栏将重新出现，并且不会自动消失。
 * View.SYSTEM_UI_FLAG_FULLSCREEN  隐藏状态栏，点击屏幕区域不会出现，需要从状态栏位置下拉才会出现。
 * View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION  将布局内容拓展到导航栏的后面。
 * View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  将布局内容拓展到状态的后面。
 *
 * View.SYSTEM_UI_FLAG_LAYOUT_STABLE
 * 稳定布局，主要是在全屏和非全屏切换时，布局不要有大的变化。一般和View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN、
 * View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION搭配使用。同时，android:fitsSystemWindows要设置为true。
 *
 *View.SYSTEM_UI_FLAG_IMMERSIVE
 * 使状态栏和导航栏真正的进入沉浸模式,即全屏模式，如果没有设置这个标志，设置全屏时，我们点击屏幕的任意位置，
 * 就会恢复为正常模式。所以，View.SYSTEM_UI_FLAG_IMMERSIVE都是配合View.SYSTEM_UI_FLAG_FULLSCREEN和
 * View.SYSTEM_UI_FLAG_HIDE_NAVIGATION一起使用的。
 *
 * View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
 * 它的效果跟View.SYSTEM_UI_FLAG_IMMERSIVE一样。但是，它在全屏模式下，用户上下拉状态栏或者导航栏时，
 * 这些系统栏只是以半透明的状态显示出来，并且在一定时间后会自动消失。
 * */


public class StatusBarUtil {

      /**
       * 布局扩展到状态栏
       */
      private static int EXTENSION_STATUS = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
              | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

      /**
       * 设置状态栏颜色
       * @param activity
       * @param color
       */
      public static void setStatusBar(Activity activity,int color){
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().setStatusBarColor(color);

            if(isColorDark(color)){
                  activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }else {
                  activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
      }

      /**
       * 设置View填充状态栏
       * @param activity
       */
      private static void viewFillStatusBar(Activity activity){
            View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(EXTENSION_STATUS);
            setStatusBar(activity,Color.TRANSPARENT);
      }

      /**
       * 图片填充状态栏
       * @param activity
       * @param view
       */
      public static void imageFillStatusBar(Activity activity,View view){
            viewFillStatusBar(activity);
            setMargins(view, 0, getStatusBarHeight(activity), 0, 0);
      }

      /**
       * 取消填充状态栏
       * @param activity
       * @param view
       * @param color
       */
      public static void cancleImageFillStatusBar(Activity activity,View view,int color){
            View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(0);
            setStatusBar(activity,color);
            setMargins(view,0,0,0,0);
      }

      /**
       * 设置头部margin值
       */
      private static void setMargins(View v, int l, int t, int r, int b) {
            if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                  ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
                  p.setMargins(l, t, r, b);
                  v.requestLayout();
            }
      }

      /**
       * 判断文字是暗色还是亮色
       * @param color
       * @return
       */
      private static boolean isColorDark(int color){
            double darkness = 1-(0.299* Color.red(color) + 0.587*Color.green(color) + 0.114*Color.blue(color))/255;
            if(darkness<0.5){
                  return false; // It's a light color
            }else{
                  return true; // It's a dark color
            }
      }

      /**
       * 获得状态栏高度
       * @param context
       * @return
       */
      public static int getStatusBarHeight(Context context){
            int statusBarHeight = -1;
            try {
                  Class<?> clazz = Class.forName("com.android.internal.R$dimen");
                  Object object = clazz.newInstance();
                  int height = Integer.parseInt(clazz.getField("status_bar_height")
                          .get(object).toString());
                  statusBarHeight = context.getResources().getDimensionPixelSize(height);
            } catch (Exception e) {
                  e.printStackTrace();
            }
            return statusBarHeight;
      }
}
