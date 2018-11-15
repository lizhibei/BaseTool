package com.dengjinwen.basetool.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.dengjinwen.basetool.R;
import com.dengjinwen.basetool.adapter.SelectImageAndAdapter;
import com.dengjinwen.basetool.library.function.selectImage.AndSelectImage;
import com.dengjinwen.basetool.library.function.selectImage.ItemEntity;

import java.util.ArrayList;

/**
 * 选择本地图片
 */
public class SelectImageAndActivity extends BaseActivity implements View.OnClickListener{
    private TextView head_text_title;
    private GridView list_gv;

    private Context mContext;
    private ArrayList<String> data=new ArrayList<>();
    private SelectImageAndAdapter adapter;
    private final int SELECT_IMAGE=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_image_and_activity);
        mContext=this;
        initview();
    }

    private void initview() {
        findViewById(R.id.head_img_left).setOnClickListener(this);
        head_text_title=findViewById(R.id.head_text_title);
        head_text_title.setText("选择图片");

        list_gv=findViewById(R.id.list_gv);

        findViewById(R.id.add_tv).setOnClickListener(this);

        adapter=new SelectImageAndAdapter(mContext,data);
        list_gv.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(resultCode==RESULT_OK){
            Bundle bundle=intent.getExtras();
            switch (requestCode){
                case SELECT_IMAGE:
                    ArrayList<ItemEntity> list=bundle.getParcelableArrayList(AndSelectImage.SELECT_VIDEO);
                    data.clear();
                    if(list!=null&&list.size()>0){
                        for(int i=0;i<list.size();i++){
                            ItemEntity itemEntity=list.get(i);
                            data.add(itemEntity.getPath());
                        }
                    }
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.head_img_left:
                finish();
            break;
            case R.id.add_tv:
                new AndSelectImage().withActivity(this)
                        .withNumber(20)
                        .withRequestCode(SELECT_IMAGE)
                        .withType(1)
                        .start();
//                Intent intent=new Intent();
//                Bundle bundle=new Bundle();
//                bundle.putInt(SelectImageActivity.TAG_IMAGE_NUMBER,20);
//                intent.putExtras(bundle);
//                startActivityForResult(intent,SELECT_IMAGE);
                break;
        }
    }
}
