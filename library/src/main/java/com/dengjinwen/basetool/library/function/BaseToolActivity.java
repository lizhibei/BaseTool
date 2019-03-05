package com.dengjinwen.basetool.library.function;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.dengjinwen.basetool.library.function.dialog.product.LoadDialog;
import com.dengjinwen.basetool.library.function.mvp.IBaseView;

public class BaseToolActivity extends AppCompatActivity implements IBaseView {

    private LoadDialog loadDialog;

//    public void setTextStatusBar(){
//        ScreenUitl.setHead(this,findViewById(R.id.head_empty),
//                android.R.color.white,ScreenUitl.StatusBarStyle.COLOR);
//        ScreenUitl.setStatusBarLightMode(this);
//    }
//
//    public void setImageStatusBar(){
//        ScreenUitl.setHead(this, findViewById(R.id.head_empty),
//                android.R.color.transparent, ScreenUitl.StatusBarStyle.IMAGE);
//        ScreenUitl.setStatusBarDarkMode(this);
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadDialog=new LoadDialog(this);
    }

    @Override
    public void showLoading() {
        if(!loadDialog.isShowing()){
            loadDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if(loadDialog.isShowing()){
            loadDialog.dismiss();
        }
    }

    @Override
    public void showToast(String msg) {
        try {
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Looper.prepare();
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }

    @Override
    public void showErr(String msg) {
        if(msg!=null&&!msg.isEmpty()){
            Toast.makeText(getContext(),msg, Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getContext(),"发生错误", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        if(loadDialog.isShowing()){
            loadDialog.dismiss();
        }
        super.onDestroy();
    }
}
