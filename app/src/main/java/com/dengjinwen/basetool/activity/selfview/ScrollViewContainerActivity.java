package com.dengjinwen.basetool.activity.selfview;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dengjinwen.basetool.R;
import com.dengjinwen.basetool.activity.BaseActivity;

public class ScrollViewContainerActivity extends BaseActivity implements View.OnClickListener {
    private TextView head_text_title;

    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scrollview_container_activity);
        mContext=this;
        initview();
    }

    private void initview() {
        findViewById(R.id.head_img_left).setOnClickListener(this);
        head_text_title= (TextView) findViewById(R.id.head_text_title);
        head_text_title.setText("ScrollViewContainer");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.head_img_left:
                finish();
                break;
        }
    }
}
