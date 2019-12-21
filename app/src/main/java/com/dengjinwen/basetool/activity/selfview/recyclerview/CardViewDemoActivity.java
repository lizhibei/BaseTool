package com.dengjinwen.basetool.activity.selfview.recyclerview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dengjinwen.basetool.R;
import com.dengjinwen.basetool.library.view.recyclerView.adpater.UniversalAdapter;
import com.dengjinwen.basetool.library.view.recyclerView.adpater.ViewHolder;
import com.dengjinwen.basetool.library.view.recyclerView.tangtang.CardConfig;
import com.dengjinwen.basetool.library.view.recyclerView.tangtang.SwipeCardBean;
import com.dengjinwen.basetool.library.view.recyclerView.tangtang.SwipeCardCallback;
import com.dengjinwen.basetool.library.view.recyclerView.tangtang.SwipeCardLayoutManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardViewDemoActivity extends AppCompatActivity {

    @BindView(R.id.rv)RecyclerView rv;

    private Context mContext;
    private List<SwipeCardBean> mDatas;
    private UniversalAdapter<SwipeCardBean> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardview_demo_activity);
        mContext=this;
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        rv.setLayoutManager(new SwipeCardLayoutManager());

        mDatas=SwipeCardBean.initDatas();
        CardConfig.initConfig(mContext);
        adapter=new UniversalAdapter<SwipeCardBean>(mContext,R.layout.item_swipe_card,mDatas) {
            @Override
            public void convert(ViewHolder ViewHolder, SwipeCardBean swipeCardBean) {
                ViewHolder.setText(R.id.tvName, swipeCardBean.getName());
                ViewHolder.setText(R.id.tvPrecent, swipeCardBean.getPosition() +
                        "/" + mDatas.size());
                Glide.with(mContext)
                        .load(swipeCardBean.getUrl())
                        .into((ImageView) ViewHolder.getView(R.id.iv));
            }
        };

        rv.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new SwipeCardCallback(rv, adapter, mDatas);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(rv);
    }
}
