package com.dengjinwen.basetool.library.tool;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.TypedValue;

public class FetchContextTool {

    public static int fetchContextColor(Context context, int androidAttribute) {
        TypedValue typedValue = new TypedValue();

        TypedArray a = context.obtainStyledAttributes(typedValue.data, new int[]{androidAttribute});
        int color = a.getColor(0, 0);

        a.recycle();

        return color;
    }
}
