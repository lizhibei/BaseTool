package com.dengjinwen.basetool.activity.selfview;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.dengjinwen.basetool.R;
import com.dengjinwen.basetool.activity.BaseActivity;
import com.dengjinwen.basetool.databinding.ExpressionActivityBinding;
import com.dengjinwen.basetool.library.function.expression.ExpressionGridFragment;
import com.dengjinwen.basetool.library.function.expression.ExpressionShowFragment;
import com.dengjinwen.basetool.library.function.screenAdaptation.ScreenAdapterTools;
import com.dengjinwen.basetool.library.tool.SoftInputTool;
import com.dengjinwen.basetool.library.tool.SoftKeyBoardListener;

/**
 * 表情键盘
 */
public class ExpressionActivity extends BaseActivity implements ExpressionGridFragment.ExpressionClickListener,
        ExpressionGridFragment.ExpressionDeleteClickListener{

      ExpressionActivityBinding binding;
      /**
       * 是否显示了表情软盘
       */
      protected boolean isEmogiShow;
      protected ExpressionShowFragment expressionShowFragment;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.expression_activity);
            ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
            binding= DataBindingUtil.setContentView(this,R.layout.expression_activity);
            binding.setClick(new ClickProxy());

            SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
                  @Override
                  public void keyBoardShow(int height) {
                        if(isEmogiShow){
                              showSoft();
                        }
                  }

                  @Override
                  public void keyBoardHide(int height) {
                        if(isEmogiShow){
                              replaceEmogi();
                        }
                  }
            });
      }

      /**
       * 显示软盘相关操作
       */
      private void showSoft(){
            isEmogiShow=false;
            binding.flEmogi.setVisibility(View.GONE);
      }

      /**
       * 表情显示
       */
      protected void replaceEmogi(){
            isEmogiShow=true;
            binding.flEmogi.setVisibility(View.VISIBLE);
            if (expressionShowFragment == null) {
                  expressionShowFragment = ExpressionShowFragment.newInstance();
                  getSupportFragmentManager().beginTransaction().replace(R.id.fl_emogi, ExpressionShowFragment.newInstance()).commit();
            }
      }

      @Override
      public void expressionClick(String str) {
            ExpressionShowFragment.input(binding.etInput, str);
      }

      @Override
      public void expressionDeleteClick(View v) {
            ExpressionShowFragment.delete(binding.etInput);
      }

      public class ClickProxy {
            /**
             * 显示表情
             */
            public void showExpression(){
                  if(isEmogiShow){  //显示默认软盘
                        showSoft();
                        SoftInputTool.showSoftInput(ExpressionActivity.this);
                  }else { //隐藏默认软盘  显示表情软盘
                        isEmogiShow=true;
                        SoftInputTool.hideSoftInput(ExpressionActivity.this);
                  }
            }
      }

}
