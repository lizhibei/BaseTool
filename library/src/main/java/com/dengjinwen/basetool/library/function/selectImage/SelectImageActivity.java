package com.dengjinwen.basetool.library.function.selectImage;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dengjinwen.basetool.library.R;
import com.dengjinwen.basetool.library.tool.ScreenUitl;
import com.dengjinwen.basetool.library.tool.log;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * 从本地选择图片
 */
public class SelectImageActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG_IMAGE_NUMBER="image_number";
    public static final String TAG_SELECT_IMAGE="select_image";

    private TextView right_tv,filename_tv;
    private GridView show_image_gv;
    private RelativeLayout bottom_rl;
    private ProgressDialog mProgressDialog;

    private Context mContext;
    /**
     * 要选择的图片数量
     */
    private int IMAGE_NUMBER=0;
    /**
     * 存储有图片文件夹的路径
     */
    private HashSet< String> mFileCache=new HashSet<String>();
    /**
     * 存储有图片的文件夹
     */
    private List<ImageFloder> mImageFloders = new ArrayList<ImageFloder>();
    /**
     * 已选择的图片
     */
    private HashSet<String> selectImages=new HashSet<>();

    private SelectImageAdapter adapter;
    private List<String> data=new ArrayList<>();

    private SelectFileAdapter fileAdapter;

    /**
     * 选择文件夹对话框
     */
    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_image_activity);
        mContext=this;
        initview();
    }

    private void initview() {
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            IMAGE_NUMBER=bundle.getInt(TAG_IMAGE_NUMBER,0);
        }
        findViewById(R.id.left_iv).setOnClickListener(this);

        right_tv=findViewById(R.id.right_tv);
        right_tv.setText(getResources().getString(R.string.finish)+" 0/"+IMAGE_NUMBER);
        right_tv.setOnClickListener(this);

        show_image_gv=findViewById(R.id.show_image_gv);

        filename_tv=findViewById(R.id.filename_tv); //文件名称
        filename_tv.setOnClickListener(this);

        bottom_rl=findViewById(R.id.bottom_rl);

        adapter=new SelectImageAdapter(mContext,data,selectImages);
        show_image_gv.setAdapter(adapter);

        adapter.setOnAdapterProcessListener(new SelectImageAdapter.OnAdapterProcessListener() {
            /**
             * 选择的图片数量发生改变
             */
            @Override
            public void selectNumberChange() {
                right_tv.setText(getResources().getString(R.string.finish)+" "+selectImages.size()+"/"+IMAGE_NUMBER);
            }
        });

        checkPermission();
    }

    private void checkPermission(){
        AndPermission.with(this)
                .requestCode(100)
                .permission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                .callback(new PermissionListener() {
                    @Override
                    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                        if(requestCode==100){
                            getImage();
                        }
                    }

                    @Override
                    public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                        if(requestCode==100){
                            AndPermission.defaultSettingDialog(SelectImageActivity.this,400).show();
                        }
                    }
                })
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                        AndPermission.rationaleDialog(mContext,rationale).show();
                    }
                })
                .start();
    }

    private void getImage(){
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            Toast.makeText(mContext,getResources().getString(R.string.not_storage),Toast.LENGTH_SHORT).show();
        }

        mProgressDialog=ProgressDialog.show(mContext,null,getResources().getString(R.string.loading));

        new Thread(new Runnable() {
            @Override
            public void run() {

                //查找手机中的jpeg png 格式的图片
                Uri imageUri= MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver contentResolver=getContentResolver();
                Cursor cursor=contentResolver.query(imageUri,null,MediaStore.Images.Media.MIME_TYPE+"=? or " +
                        MediaStore.Images.Media.MIME_TYPE+"=?",new String[]{"image/jpeg","image/png"}, MediaStore.Images.Media.DATE_MODIFIED);
                while (cursor.moveToNext()){
                    String path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
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
                addAllImageFolder();
                mHandler.sendEmptyMessage(0);
            }
        }).start();
    }

    private Handler.Callback callback=new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            mProgressDialog.dismiss();
            if(mImageFloders.size()==0){
                Toast.makeText(mContext,getResources().getString(R.string.no_image),Toast.LENGTH_SHORT).show();
            }else {
                selectFilePopupWindow();
                updateCurrentListImage(0);
            }
            return false;
        }
    };
    private Handler mHandler=new Handler(callback);

    /**
     * 添加所有图片文件夹
     */
    private void addAllImageFolder(){
        ImageFloder imageFloder=new ImageFloder();
        imageFloder.setAll(true);
        imageFloder.setName(getResources().getString(R.string.all_image));
        imageFloder.setFirstImagePath(mImageFloders.get(0).getFirstImagePath());
        mImageFloders.add(0,imageFloder);
    }

    /**
     *更新当前列表的图片
     * @param position
     */
    private void updateCurrentListImage(int position){
        ImageFloder imageFloder=mImageFloders.get(position);
        filename_tv.setText(imageFloder.getName());
        imageFloder.setSelect(true);
        data.clear();
        if(imageFloder.isAll()){
            for(int i=1;i<mImageFloders.size();i++){
                ImageFloder ifloder=mImageFloders.get(i);
                File parentFile=new File(ifloder.getDir());
                String[] images=getImagesArray(parentFile.getAbsolutePath());
                for(int j=0;j<images.length;j++){
                    data.add(parentFile.getAbsolutePath()+"/"+images[j]);
                }
            }
        }else {
            File parentFile=new File(imageFloder.getDir());
            String[] images=getImagesArray(parentFile.getAbsolutePath());
            for(int i=0;i<images.length;i++){
                data.add(parentFile.getAbsolutePath()+"/"+images[i]);
            }
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 添加图片文件夹
     */
    private void addImageFloder(String path,File parentFile){
        ImageFloder imageFloder=new ImageFloder();
        imageFloder.setFirstImagePath(path);
        imageFloder.setDir(parentFile.getAbsolutePath());
        imageFloder.setName(parentFile.getName());
        String[] childs=getImagesArray(parentFile.getAbsolutePath());
        if(childs!=null&&childs.length>0){
            imageFloder.setCount(childs.length);
        }
        mImageFloders.add(imageFloder);
    }

    /**
     * 获得指定文件夹下的所有图片
     * @param parentPath
     * @return
     */
    private String[] getImagesArray(String parentPath){
        File parentFile=new File(parentPath);
        String[] childs=parentFile.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if(name.endsWith(".png")||name.endsWith("jpg")||name.endsWith("jpeg")){
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

    private View view;
    private ListView listView;
    /**
     * 选择文件夹
     */
    private void selectFilePopupWindow(){
        mPopupWindow=new PopupWindow(mContext);
        view= LayoutInflater.from(mContext).inflate(R.layout.select_file_pop,null);
        listView=view.findViewById(R.id.list_lv);
        log.e("listView:"+listView);
        fileAdapter=new SelectFileAdapter(mContext,mImageFloders);
        listView.setAdapter(fileAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPopupWindow.dismiss();
                for(int i=0;i<mImageFloders.size();i++){
                    ImageFloder mif=mImageFloders.get(i);
                    if(i==position){
                        mif.setSelect(true);
                    }else {
                        mif.setSelect(false);
                    }
                }
                updateCurrentListImage(position);
            }
        });

        mPopupWindow.setContentView(view);
        mPopupWindow.setAnimationStyle(R.style.anim_popup_dir);
        mPopupWindow.setWidth(ScreenUitl.getScreenWidth(mContext));
        mPopupWindow.setHeight((int) (ScreenUitl.getScreenHeight(mContext)*0.7));
        mPopupWindow.setFocusable(true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.left_iv){  //返回
            finish();
        }else if(v.getId()==R.id.right_tv){  //完成
            if(selectImages.size()>0){
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                ArrayList<String> urls=new ArrayList<>();
                Iterator iterator=selectImages.iterator();
                while (iterator.hasNext()){
                    urls.add((String) iterator.next());
                }
                bundle.putStringArrayList(TAG_SELECT_IMAGE,urls);
                intent.putExtras(bundle);
                setResult(RESULT_OK,intent);
                finish();
            }else {
                Toast.makeText(mContext,getResources().getString(R.string.please_select_image),Toast.LENGTH_SHORT).show();
            }
        }else if(v.getId()==R.id.filename_tv){ //选择文件
            if(mImageFloders.size()>0){
                mPopupWindow.showAsDropDown(bottom_rl,0,0);
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = .3f;
                getWindow().setAttributes(lp);
            }else {
                Toast.makeText(mContext,getResources().getString(R.string.no_image),Toast.LENGTH_SHORT).show();
            }
        }
    }
}
