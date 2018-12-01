package com.dengjinwen.basetool.library.function.selectImage;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
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
import com.dengjinwen.basetool.library.tool.CameraTool;
import com.dengjinwen.basetool.library.tool.LocalDataUitlTool;
import com.dengjinwen.basetool.library.tool.ScreenUitl;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * 从本地选择图片
 */
public class SelectImageActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG_IMAGE_NUMBER="image_number";
    public static final String TYPE="select_type";
    /**
     * 选择图片
     */
    public static final int TAG_SELECT_IMAGE=0;
    /**
     * 选择视频
     */
    public static final int TAG_SELECT_VIDEO=1;

    private int SELECT_TYPE=TAG_SELECT_IMAGE;

    private TextView right_tv,filename_tv,title_tv;
    private GridView show_image_gv;
    private RelativeLayout bottom_rl;
    private ProgressDialog mProgressDialog;

    private Context mContext;
    /**
     * 要选择的图片数量
     */
    private int IMAGE_NUMBER=0;
    /**
     * 存储有图片的文件夹
     */
    private List<Floder> mImageFloders = new ArrayList<Floder>();
    /**
     * 已选择的
     */
    private HashSet<ItemEntity> selectItem=new HashSet<>();

    private SelectImageAdapter adapter;
    private List<ItemEntity> data=new ArrayList<>();
    List<ItemEntity> list;

    private SelectFileAdapter fileAdapter;

    /**
     * 选择文件夹对话框
     */
    private PopupWindow mPopupWindow;
    private LocalDataUitlTool localDataUitlTool;

    private String path;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_image_activity);
        mContext=this;
        localDataUitlTool=new LocalDataUitlTool(mContext);
        initview();
    }

    private void initview() {
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            IMAGE_NUMBER=bundle.getInt(TAG_IMAGE_NUMBER,0);
            SELECT_TYPE=bundle.getInt(TYPE,TAG_SELECT_IMAGE);
        }
        findViewById(R.id.left_iv).setOnClickListener(this);

        right_tv=findViewById(R.id.right_tv);
        right_tv.setText(getResources().getString(R.string.finish)+" 0/"+IMAGE_NUMBER);
        right_tv.setOnClickListener(this);

        title_tv=findViewById(R.id.title_tv);
        if(SELECT_TYPE==TAG_SELECT_VIDEO){
            title_tv.setText(R.string.select_video);
        }else {
            title_tv.setText(R.string.select_image);
        }

        show_image_gv=findViewById(R.id.show_image_gv);

        filename_tv=findViewById(R.id.filename_tv); //文件名称
        filename_tv.setOnClickListener(this);

        bottom_rl=findViewById(R.id.bottom_rl);

        adapter=new SelectImageAdapter(mContext,data,selectItem,SELECT_TYPE,IMAGE_NUMBER);
        show_image_gv.setAdapter(adapter);

        adapter.setOnAdapterProcessListener(new SelectImageAdapter.OnAdapterProcessListener() {
            @Override
            public void taking() {
                if(SELECT_TYPE==TAG_SELECT_VIDEO){  //拍摄视频
                    path=CameraTool.takeVideo(SelectImageActivity.this,TAG_SELECT_VIDEO);
                }else {  //拍摄照片
                    path=CameraTool.takePicture(SelectImageActivity.this,TAG_SELECT_IMAGE);
                }
            }

            /**
             * 选择的图片数量发生改变
             */
            @Override
            public void selectNumberChange() {
                right_tv.setText(getResources().getString(R.string.finish)+" "+selectItem.size()+"/"+IMAGE_NUMBER);
            }
        });

        checkPermission();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK){
            Intent intent=new Intent();
            Bundle bundle=new Bundle();
            ArrayList<ItemEntity> items=new ArrayList<>();
            ItemEntity itemEntity=new ItemEntity();
            itemEntity.setPath(path);
            items.add(itemEntity);
            //通知相册更新
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(path))));
            //拍摄照片返回
            if (requestCode==TAG_SELECT_IMAGE){
                if(path!=null){
                    bundle.putParcelableArrayList(AndSelectImage.SELECT_IMAGE,items);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }else if(requestCode==TAG_SELECT_VIDEO){  //拍摄视频返回
                if(path!=null){
                    bundle.putParcelableArrayList(AndSelectImage.SELECT_VIDEO,items);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        }
    }

    private void checkPermission(){
        AndPermission.with(this)
                .requestCode(100)
                .permission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
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
                if(SELECT_TYPE==TAG_SELECT_VIDEO){
                    list=localDataUitlTool.getVideotList();
                }else {
                    list=localDataUitlTool.getImageList();
                }
                mImageFloders.clear();
                mImageFloders.addAll(localDataUitlTool.getImageFloders());
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
     *更新当前列表的图片
     * @param position
     */
    private void updateCurrentListImage(int position){
        Floder imageFloder=mImageFloders.get(position);
        filename_tv.setText(imageFloder.getName());
        imageFloder.setSelect(true);
        data.clear();
        if(SELECT_TYPE==TAG_SELECT_VIDEO){
            if(imageFloder.isAll()){
                data.addAll(list);
            }else {
                data.addAll(getFloderVideo(imageFloder));
            }
        }else {
            if(imageFloder.isAll()){
                for(int i=1;i<mImageFloders.size();i++){
                    Floder ifloder=mImageFloders.get(i);
                    data.addAll(localDataUitlTool.getFolderImages(ifloder));
                }
            }else {
                data.addAll(localDataUitlTool.getFolderImages(imageFloder));
            }
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 获得 指定文件夹下的视频列表
     * @param floder
     * @return
     */
    private List<ItemEntity> getFloderVideo(Floder floder){
        List<ItemEntity> l=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            ItemEntity itemEntity=list.get(i);
            if(floder.getDir().equals(new File(itemEntity.getPath()).getParent())){
                l.add(itemEntity);
            }
        }
        return l;
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
        fileAdapter=new SelectFileAdapter(mContext,mImageFloders,SELECT_TYPE);
        listView.setAdapter(fileAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPopupWindow.dismiss();
                for(int i=0;i<mImageFloders.size();i++){
                    Floder mif=mImageFloders.get(i);
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
            if(selectItem.size()>0){
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                ArrayList<ItemEntity> items=new ArrayList<>();
                Iterator iterator=selectItem.iterator();
                while (iterator.hasNext()){
                    items.add((ItemEntity) iterator.next());
                }
                if(SELECT_TYPE==TAG_SELECT_VIDEO){
                    bundle.putParcelableArrayList(AndSelectImage.SELECT_VIDEO,items);
                }else {
                    bundle.putParcelableArrayList(AndSelectImage.SELECT_IMAGE,items);
                }
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
