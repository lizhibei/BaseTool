package com.dengjinwen.basetool.library.function.zxing;

import android.app.Activity;
import android.os.Handler;

import com.dengjinwen.basetool.library.function.zxing.bean.ZxingConfig;
import com.dengjinwen.basetool.library.function.zxing.camera.CameraManager;
import com.dengjinwen.basetool.library.function.zxing.view.ViewfinderView;
import com.google.zxing.Result;

public interface ICaptureView {

     ViewfinderView getViewfinderView();

     void handleDecode(Result rawResult);

     Activity getActivity();

     void switchFlashImg(int flashState);

     ZxingConfig getConfig();

     CameraManager getCameraManager();

     Handler getHandler();
}
