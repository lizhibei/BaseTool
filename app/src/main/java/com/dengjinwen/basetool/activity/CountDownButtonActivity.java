package com.dengjinwen.basetool.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dengjinwen.basetool.R;
import com.dengjinwen.basetool.library.view.CountDownButton;
import com.dengjinwen.basetool.util.BarterLoginDialog;

/**
 * 计时器
 */
public class CountDownButtonActivity extends BaseActivity implements View.OnClickListener {
    private TextView head_text_title;
    private CountDownButton cd_button;

    private Context mContext;
    private BarterLoginDialog barterLoginDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.count_down_button_activity);
        mContext=this;
        initview();
    }

    private void initview() {
        findViewById(R.id.head_img_left).setOnClickListener(this);
        head_text_title=findViewById(R.id.head_text_title);
        head_text_title.setText("CountDownButton");

        cd_button=findViewById(R.id.cd_button);
        cd_button.setOnClickListener(this);

        barterLoginDialog=new BarterLoginDialog(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.head_img_left:
                finish();
                break;
                //
            case R.id.cd_button:
//                Toast.makeText(mContext,"获取验证码成功",Toast.LENGTH_SHORT).show();
                barterLoginDialog.show();
                break;
        }
    }
}
