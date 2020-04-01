package com.dengjinwen.basetool.library.view.checkView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class CheckableImageView extends AppCompatImageView implements Checkable {

    private boolean mChecked;
    private int[] CHECKED_STATE_SET={android.R.attr.state_checked};

    public CheckableImageView(Context context) {
        super(context);
    }

    public CheckableImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setChecked(boolean checked) {
        if(checked!=mChecked){
            mChecked=checked;
            refreshDrawableState();
        }
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        mChecked=!mChecked;
        refreshDrawableState();
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState=super.onCreateDrawableState(extraSpace+CHECKED_STATE_SET.length);
        if(isChecked()){
            mergeDrawableStates(drawableState,CHECKED_STATE_SET);
        }
        return drawableState;
    }
}
