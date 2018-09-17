package com.dengjinwen.basetool.library.function.selectImage;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

public class AndSelectImage {
    private Activity activity;
    private Fragment fragment;
    private android.support.v4.app.Fragment supporFragment;

    private Class<? extends SelectImageActivity> mSelectImageClass=SelectImageActivity.class;
    /**
     * 跳转activity的requestcode
     */
    private Integer mRequestCode;

    /**
     * 选择图片的数量
     */
    private int selectNumber;

    public AndSelectImage(){}

    public  AndSelectImage withActivity(Activity activity){
        if(fragment!=null&&supporFragment!=null){
            throw new RuntimeException("You must pass either Activity, Fragment or SupportFragment");
        }
        this.activity=activity;
        return this;
    }

    public AndSelectImage withFragment(Fragment fragment){
        if(activity!=null&&supporFragment!=null){
            throw new RuntimeException("You must pass either Activity, Fragment or SupportFragment");
        }
        this.fragment=fragment;
        return this;
    }

    public AndSelectImage withSupporFragment(android.support.v4.app.Fragment fragment){
        if(activity!=null||fragment!=null){
            throw new RuntimeException("You must pass either Activity, Fragment or SupportFragment");
        }
        supporFragment=fragment;
        return this;
    }

    public AndSelectImage withRequestCode(int requestCode){
        mRequestCode=requestCode;
        return this;
    }

    public AndSelectImage withNumber(int number){
        selectNumber=number;
        return this;
    }

    public void start(){
        if (activity == null && fragment == null && supporFragment == null) {
            throw new RuntimeException("You must pass Activity/Fragment by calling withActivity/withFragment/withSupportFragment method");
        }

        if (mRequestCode == null) {
            throw new RuntimeException("You must pass request code by calling withRequestCode method");
        }

        Activity activity1=null;
        if (activity != null) {
            activity1 = activity;
        } else if (fragment != null) {
            activity1 = fragment.getActivity();
        } else if (supporFragment != null) {
            activity1 = supporFragment.getActivity();
        }

        Intent intent=new Intent(activity1,mSelectImageClass);
        Bundle bundle=new Bundle();
        bundle.putInt(SelectImageActivity.TAG_IMAGE_NUMBER,selectNumber);
        intent.putExtras(bundle);
        if(activity!=null){
            activity.startActivityForResult(intent,mRequestCode);
        }else if(fragment!=null){
            fragment.startActivityForResult(intent,mRequestCode);
        }else if(supporFragment!=null){
            supporFragment.startActivityForResult(intent,mRequestCode);
        }
    }
}
