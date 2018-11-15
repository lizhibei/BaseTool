package com.dengjinwen.basetool.library.tool;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import com.dengjinwen.basetool.library.R;
import com.dengjinwen.basetool.library.function.selectImage.Floder;
import com.dengjinwen.basetool.library.function.selectImage.ImageFolderComparator;
import com.dengjinwen.basetool.library.function.selectImage.ItemEntity;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * 获取本地数据工具类  如获取本地图片，本地视频
 */
public class LocalDataUitlTool {

    private List<Floder> mImageFloders=new ArrayList<>();
    private HashSet<String> mFileCache=new HashSet<>();
    private Context mContext;

    public LocalDataUitlTool(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 获取本地的视频列表
     * @return
     */
    public  List<ItemEntity> getVideotList(){

        List<ItemEntity> sysVideoList=new ArrayList<>();
        // MediaStore.Video.Thumbnails.DATA:视频缩略图的文件路径
        String[] thumbColumns={MediaStore.Video.Thumbnails.DATA,MediaStore.Video.Thumbnails.VIDEO_ID};
        // 视频其他信息的查询条件
        String[] mediaColumns={MediaStore.Video.Media._ID,MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DURATION};

        Cursor cursor=mContext.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                mediaColumns,null,null,null);
        if(cursor==null){
            return sysVideoList;
        }

        if(cursor.moveToFirst()){
            do{
                ItemEntity info=new ItemEntity();
                int id=cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media._ID));
                Cursor thumbCursor=mContext.getContentResolver().query(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI,
                        thumbColumns,MediaStore.Video.Thumbnails.VIDEO_ID+"="+id,null,null);
                if(thumbCursor.moveToFirst()){
                    info.setThumb(thumbCursor.getString(thumbCursor.getColumnIndex(MediaStore.Video.Thumbnails.DATA)));
                }
                info.setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA)));
                info.setDuration(cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media.DURATION)));
                sysVideoList.add(info);

                File parentFile=new File(info.getPath()).getParentFile();
                if(parentFile==null){
                    continue;
                }
                String parentPath=parentFile.getAbsolutePath();
                if(mFileCache.contains(parentPath)){
                    for(int i=0;i<mImageFloders.size();i++){
                        Floder floder=mImageFloders.get(i);
                        if(floder.getDir().equals(parentPath)){
                            floder.setCount(floder.getCount()+1);
                        }
                    }
                    continue;
                }else {
                    mFileCache.add(parentPath);
                    addVideoFloder(info.getThumb(),parentFile);
                }
            }while (cursor.moveToNext());
        }
        mFileCache.clear();
        Collections.sort(mImageFloders,new ImageFolderComparator());
        addAllImageFolder(mContext.getResources().getString(R.string.all_video));
        mImageFloders.get(0).setCount(sysVideoList.size());
        return sysVideoList;
    }

    /**
     * 获取本地所有图片链接 并且会获取图片存储的文件夹列表
     * @return
     */
    public  List<ItemEntity> getImageList(){
        List<ItemEntity> data=new ArrayList<>();
        Cursor cursor=getImageCursor(mContext);
        while (cursor.moveToNext()){
            String path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            ItemEntity ie=new ItemEntity();
            ie.setPath(path);
            data.add(ie);

            File parentFile=new File(path).getParentFile();
            if(parentFile==null){
                continue;
            }
            String parentPath=parentFile.getAbsolutePath();
            if(mFileCache.contains(parentPath)){
                continue;
            }else {
                mFileCache.add(parentPath);
                addImageFloder(path,parentFile);
            }
        }

        mFileCache.clear();
        Collections.sort(mImageFloders,new ImageFolderComparator());
        addAllImageFolder(mContext.getResources().getString(R.string.all_image));

        return data;
    }

    public List<Floder> getImageFloders(){
        return mImageFloders;
    }

    /**
     * 检查有没外包存储
     */
    public  void checkStorage(){
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            Toast.makeText(mContext,mContext.getResources().getString(R.string.not_storage),Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 添加所有图片或者视频文件夹
     */
    private void addAllImageFolder(String name){
        Floder imageFloder=new Floder();
        imageFloder.setAll(true);
        imageFloder.setName(name);
        imageFloder.setFirstImagePath(mImageFloders.get(0).getFirstImagePath());
        mImageFloders.add(0,imageFloder);
    }

    /**
     * 添加图片文件夹
     */
    private void addImageFloder(String path,File parentFile){
        Floder imageFloder=new Floder();
        imageFloder.setFirstImagePath(path);
        imageFloder.setDir(parentFile.getAbsolutePath());
        imageFloder.setName(parentFile.getName());
        String[] childs=getImagesArray(parentFile.getAbsolutePath());
        if(childs!=null&&childs.length>0){
            imageFloder.setCount(childs.length);
        }
        mImageFloders.add(imageFloder);
    }

    private void addVideoFloder(String path,File parentFile){
        Floder imageFloder=new Floder();
        imageFloder.setFirstImagePath(path);
        imageFloder.setDir(parentFile.getAbsolutePath());
        imageFloder.setName(parentFile.getName());
        imageFloder.setCount(1);
        mImageFloders.add(imageFloder);
    }

    /**
     * 获得指定文件夹下的照片
     * @param floder
     * @return
     */
    public List<ItemEntity> getFolderImages(Floder floder){
        List<ItemEntity> list=new ArrayList<>();
        File parentFile=new File(floder.getDir());
        String[] images=getImagesArray(parentFile.getAbsolutePath());
        for(int i=0;i<images.length;i++){
            String thumb=parentFile.getAbsolutePath()+"/"+images[i];
            ItemEntity itemEntity=new ItemEntity();
            itemEntity.setPath(thumb);
            list.add(itemEntity);
        }
        return list;
    }


    /**
     * 获得指定文件夹下的所有图片
     * @param parentPath
     * @return
     */
    public String[] getImagesArray(String parentPath){
        File parentFile=new File(parentPath);
        String[] childs=parentFile.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if(name.endsWith(".png")||name.endsWith(".jpg")||name.endsWith(".jpeg")){
                    return true;
                }
                return false;
            }
        });
        if(childs==null){
            childs=new String[]{};
        }
        return childs;
    }

    /**
     * 获取本地图片的Cursor
     * @param context
     * @return
     */
    public static Cursor getImageCursor(Context context){
        //查找手机中的jpeg png 格式的图片
        Uri imageUri= MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver contentResolver=context.getContentResolver();
        Cursor cursor=contentResolver.query(imageUri,null,MediaStore.Images.Media.MIME_TYPE+"=? or " +
                MediaStore.Images.Media.MIME_TYPE+"=?",new String[]{"image/jpeg","image/png"}, MediaStore.Images.Media.DATE_MODIFIED);
        return cursor;
    }
}
