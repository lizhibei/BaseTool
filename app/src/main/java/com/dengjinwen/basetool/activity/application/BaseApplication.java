package com.dengjinwen.basetool.activity.application;

import android.app.Application;
import android.content.res.Configuration;

import com.dengjinwen.basetool.library.function.screenAdaptation.ScreenAdapterTools;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ScreenAdapterTools.init(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ScreenAdapterTools.getInstance().reset(this);
    }
}
