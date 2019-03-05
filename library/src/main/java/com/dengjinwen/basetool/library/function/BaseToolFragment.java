package com.dengjinwen.basetool.library.function;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dengjinwen.basetool.library.R;
import com.dengjinwen.basetool.library.function.dialog.product.LoadDialog;
import com.dengjinwen.basetool.library.function.mvp.IBaseView;
import com.dengjinwen.basetool.library.tool.ScreenUitl;

public abstract class BaseToolFragment extends Fragment implements IBaseView {

    public Context mContext;
    public View mRootView;
    private LoadDialog loadDialog;

    public abstract int getContentViewId();

    public abstract void initAllMembersView(Bundle savedInstanceState);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView=inflater.inflate(getContentViewId(),null);
        mContext=getContext();
        loadDialog=new LoadDialog(mContext);
        initAllMembersView(savedInstanceState);
        return mRootView;
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
        return mContext;
    }

    public void setTextStatusBar(){
        ScreenUitl.setHead(getActivity(),mRootView.findViewById(R.id.head_empty),
                android.R.color.white,ScreenUitl.StatusBarStyle.COLOR);
        ScreenUitl.setStatusBarLightMode(getActivity());
    }

    public void setImageStatusBar(){
        ScreenUitl.setHead(getActivity(), mRootView.findViewById(R.id.head_empty),
                android.R.color.transparent, ScreenUitl.StatusBarStyle.IMAGE);
        ScreenUitl.setStatusBarDarkMode(getActivity());
    }
}
