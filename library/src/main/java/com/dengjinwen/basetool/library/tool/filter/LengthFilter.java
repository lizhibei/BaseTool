package com.dengjinwen.basetool.library.tool.filter;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * 限制输入的长度
 */
public class LengthFilter implements InputFilter {

    private int mMax;

    public LengthFilter(int mMax) {
        this.mMax = mMax;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
//        int keep=mMax-
        return null;
    }
}
