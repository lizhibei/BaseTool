package com.dengjinwen.basetool.library.function.zxing;

import com.dengjinwen.basetool.library.function.zxing.view.ViewfinderView;

public interface ICaptureView extends IBaseCaptureView{

     ViewfinderView getViewfinderView();

     /**
      * 切换闪光灯图片
      * @param flashState
      */
     void switchFlashImg(int flashState);

}
