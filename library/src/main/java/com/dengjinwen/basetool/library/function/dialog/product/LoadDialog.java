package com.dengjinwen.basetool.library.function.dialog.product;

import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dengjinwen.basetool.library.R;
import com.dengjinwen.basetool.library.function.screenAdaptation.ScreenAdapterTools;

import androidx.annotation.NonNull;

public class LoadDialog extends Dialog {

    public View view;
    private ProgressBar progress_bar;
    private TextView hint_tv;
    private RelativeLayout rl_dialog;

    public LoadDialog(@NonNull Context context) {
        super(context);
        view=LayoutInflater.from(context).inflate(R.layout.load_dialog_basetool,null);
        ScreenAdapterTools.getInstance().loadView(view);
        setCanceledOnTouchOutside(false);
        Window window = getWindow();
        window.setContentView(view);
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams layoutParams=window.getAttributes();
        layoutParams.dimAmount=0.0f;
        window.setAttributes(layoutParams);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        initview();
    }

    private void initview() {
        progress_bar=view.findViewById(R.id.progress_bar);
        hint_tv=view.findViewById(R.id.hint_tv);
        rl_dialog=view.findViewById(R.id.rl_dialog);
    }

    /**
     * 设置加载框颜色
     *
     * @param color
     */
    public void setProgressBarColor(int color) {
        progress_bar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }

    public void setBackground(int resid){
        rl_dialog.setBackgroundResource(resid);
    }

    public void setHint(int hint) {
        hint_tv.setText(hint);
    }

    public void setHint(String hint) {
        hint_tv.setText(hint);
    }

    public void setHintColor(int color) {
        hint_tv.setTextColor(color);
    }
}
