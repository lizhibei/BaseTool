package com.dengjinwen.basetool.library.tool;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.io.File;
import java.util.List;

/**
 * 照相机工具类
 */
public class CameraTool {

    public static final String DIR=Environment.getExternalStorageDirectory().toString();
    /**
     * 存储照片的路径
     */
    public static final String PATH_IMAGE = DIR+ "/tool/Camera/image";
    /**
     * 存储视频的路径
     */
    public static final String PATH_VIDEO=DIR+"/tool/Camera/video/";

    public static final String packageName="com.dengjinwen.basetool.library";

    public static File mImageFile;
    public static String path;

    public static String takeVideo(Activity activity,int flag){
        path=take(activity,flag,MediaStore.ACTION_VIDEO_CAPTURE);
        return path;
    }

    public static String takePicture(final Activity activity, final int flag){
        path=take(activity,flag,MediaStore.ACTION_IMAGE_CAPTURE);
        return path;
    }

    public static String take(final Activity activity,final int flag,final String type){
        final long time=System.currentTimeMillis();
        String parentPath;
        if(type.equals(MediaStore.ACTION_IMAGE_CAPTURE)){
            path=PATH_IMAGE+"/"+time+".jpg";
            parentPath=PATH_IMAGE;
        }else {
            path=PATH_VIDEO+"/"+time+".mp4";
            parentPath=PATH_VIDEO;
        }
        final File parentFile=new File(parentPath);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            if (activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED&&activity.
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED) {
                AndPermission.with(activity)
                        .requestCode(100)
                        .permission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .callback(new PermissionListener() {
                            @Override
                            public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                                if(!parentFile.exists()){
                                    boolean cu=parentFile.mkdirs();
                                }
                                mImageFile=new File(parentFile,time+".jpg");
                                final Uri mUri=FileProvider.getUriForFile(activity,packageName+".fileprovider",mImageFile);
                                toCamera(mUri,activity,flag,type);
                            }

                            @Override
                            public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                                if(requestCode==100){
                                    AndPermission.defaultSettingDialog(activity,400).show();
                                }
                            }
                        })
                        .rationale(new RationaleListener() {
                            @Override
                            public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                                AndPermission.rationaleDialog(activity,rationale).show();
                            }
                        })
                        .start();
            }else {
                if(!parentFile.exists()){
                    boolean cu=parentFile.mkdirs();
                }
                if(type.equals(MediaStore.ACTION_IMAGE_CAPTURE)){
                    mImageFile=new File(parentFile,time+".jpg");
                }else {
                    mImageFile=new File(parentFile,time+".mp4");
                }
                final Uri mUri=FileProvider.getUriForFile(activity,packageName+".fileprovider",mImageFile);
                toCamera(mUri,activity,flag,type);
            }

        }else {
            if(!parentFile.exists()){
                boolean cu=parentFile.mkdirs();
            }
            if(type.equals(MediaStore.ACTION_IMAGE_CAPTURE)){
                mImageFile=new File(parentFile,time+".jpg");
            }else {
                mImageFile=new File(parentFile,time+".mp4");
            }
            Intent intent = new Intent(type);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageFile);
            activity.startActivityForResult(intent, flag);
        }
        return path;
    }

    private static void toCamera(Uri mUri, Activity activity,int flag,String type){
        Intent intent = new Intent(type);
//        Intent intent=new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(mUri, "image/*");
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//		intent.putExtra("crop", "true");//进行修剪
//		intent.putExtra("aspectX", 1);
//		intent.putExtra("aspectY", 1);
//		intent.putExtra("outputX", 150);
//		intent.putExtra("outputY", 150);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
//		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//		intent.putExtra("return-data", false);

        //重要的一步，使用grantUriPermission来给对应的包提升读写指定uri的临时权限。否则即使调用成功，也会保存裁剪照片失败。
        List<ResolveInfo> resInfoList = activity.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String p = resolveInfo.activityInfo.packageName;
            activity.grantUriPermission(packageName, mUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        activity.startActivityForResult(intent, flag);
    }

}
