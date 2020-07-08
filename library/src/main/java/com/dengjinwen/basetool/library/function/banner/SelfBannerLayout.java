package com.dengjinwen.basetool.library.function.banner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.dengjinwen.basetool.library.R;
import com.dengjinwen.basetool.library.function.banner.interfac.IBannerEntity;
import com.dengjinwen.basetool.library.function.banner.interfac.OnBannerImgShowListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SelfBannerLayout<T extends PagerAdapter> extends FrameLayout implements ViewPager.OnPageChangeListener{

      private BannerViewPager viewPager;
      private BannerPointLayout bannerPointLayout;
      private int currentPosition;
      private Timer mTimer = new Timer();
      private T adapter;
      private List<IBannerEntity> mEntities = new ArrayList<>();

      public SelfBannerLayout(@NonNull Context context) {
            super(context);
            initView();
      }

      public SelfBannerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
            initView();
      }

      public SelfBannerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            initView();
      }

      public SelfBannerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
            initView();
      }

      private void initView() {
            LayoutInflater.from(getContext()).inflate(R.layout.line_banner_view, this);
            viewPager =  findViewById(R.id.vt_banner_id);
            bannerPointLayout =  findViewById(R.id.vt_banner_point);


      }

      public void setAdapter(T adapter){
            this.adapter=adapter;
      }
      /**
       * 绑定数据
       */
      public void setEntities(List<IBannerEntity> entities,T adapter, OnBannerImgShowListener callBack) {

            if (callBack==null){
                  throw new IllegalStateException("请实现图片加载方法");
            }

            if (entities != null && entities.size() > 0) {
                  mEntities.clear();
                  mEntities.add(entities.get(entities.size() - 1));
                  mEntities.addAll(entities);
                  mEntities.add(entities.get(0));

                  bannerPointLayout.setPointCount(mEntities.size() - 2);
                  viewPager.setAdapter(adapter);
                  viewPager.addOnPageChangeListener(this);
                  viewPager.setCurrentItem(1);
                  if (entities.size() > 1) {
                        viewPager.setCanScroll(true);
                  } else {
                        viewPager.setCanScroll(false);
                  }
            }
      }

      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (slidingPageListener != null) {
                  slidingPageListener.onPageIndex(position);
            }
            if (positionOffsetPixels == 0.0) {
                  if (position == mEntities.size() - 1) {
                        viewPager.setCurrentItem(1, false);
                  } else if (position == 0) {
                        viewPager.setCurrentItem(mEntities.size() - 2, false);
                  } else {
                        viewPager.setCurrentItem(position);
                  }
            }
      }

      @Override
      public void onPageSelected(int position) {
            currentPosition = position;

            if (position == mEntities.size() - 1) {
                  bannerPointLayout.setPosition(0);
            } else if (position == 0) {
                  bannerPointLayout.setPosition(mEntities.size() - 2 - 1);
            } else {
                  bannerPointLayout.setPosition(position - 1);
            }
      }

      @Override
      public void onPageScrollStateChanged(int state) {

      }



      /**默认启动动画的方法*/
      public void schedule() {
            schedule(2000, 4000);
      }

      private boolean isFirst = false;

      /**可以设置动画间隔时间的方法*/
      public void schedule(long delay, long period) {
            if (mEntities == null || mEntities.size() <= 3) {
                  return;
            }
            if (!isFirst) {
                  mTimer.schedule(mTimerTask, delay, period);
                  isFirst = true;
            }
      }

      private TimerTask mTimerTask = new TimerTask() {
            @Override
            public void run() {

                  handler.sendEmptyMessage(0);
            }
      };

      @SuppressLint("HandlerLeak")
      private Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                  super.handleMessage(msg);
                  if (currentPosition != mEntities.size() - 1) {
                        currentPosition = currentPosition + 1;
                        viewPager.setCurrentItem(currentPosition);
                  } else {
                        currentPosition = 0;
                        viewPager.setCurrentItem(0);
                  }
            }
      };

      /**
       * 设置点的位置
       */
      public void setPointPotision(int gravity) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) bannerPointLayout.getLayoutParams();
            layoutParams.gravity = gravity;
            bannerPointLayout.setLayoutParams(layoutParams);
      }

      /**
       * 设置点的颜色
       */
      public void setPointColor(int lightColor, int dimColor) {
            bannerPointLayout.setPointColor(lightColor, dimColor);
      }

      /**
       * 设置点的margin
       * @param left
       * @param top
       * @param right
       * @param bottom
       */
      public void setPointMargin(int left,int top,int right,int bottom){
            LinearLayout.LayoutParams layoutParams= (LinearLayout.LayoutParams) bannerPointLayout.getLayoutParams();
            layoutParams.setMargins(left,top,right,bottom);
            bannerPointLayout.setLayoutParams(layoutParams);
      }


      public interface SlidingPageListener {
            void onPageIndex(int index);
      }

      private BannerLayout.SlidingPageListener slidingPageListener;

      public void setSlidingPageListener(BannerLayout.SlidingPageListener slidingPageListener) {
            this.slidingPageListener = slidingPageListener;
      }
}
