package com.dengjinwen.basetool.library.function.dialog.product;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.dengjinwen.basetool.library.R;
import com.dengjinwen.basetool.library.function.screenAdaptation.ScreenAdapterTools;

public class LoadDialog extends Dialog {

    public View view;

    public LoadDialog(@NonNull Context context) {
        super(context);
        view=LayoutInflater.from(context).inflate(R.layout.load_dialog,null);
        ScreenAdapterTools.getInstance().loadView(view);
        setCancelable(false);
        Window window = getWindow();
        window.setContentView(view);
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(android.R.color.transparent);
    }
}
