package com.dengjinwen.basetool.library.tool.filter.inputFilter;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 限制 必须输入数字，并且有可以限制最大最小值
 */
public class NumberLimitInputFilter implements InputFilter {
    /**
     * 最小值
     */
    private long min;
    /**
     * 最大值
     */
    private long max;
    private Context context;

    /**
     *
     * @param context
     * @param min  最小值
     * @param max  最大值
     */
    public NumberLimitInputFilter(Context context,long min, long max) {
        this.min = min;
        this.max = max;
        this.context=context;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        Pattern pattern=Pattern.compile("[0-9]*");
        Matcher isNum=pattern.matcher(source.toString());
        if(!isNum.matches()){
            return "";
        }
        String s=dest.toString()+source.toString();
        long temp=Long.parseLong(s);
        CharSequence minC=""+min;
        if(s.length()>=minC.length()){
            if(temp<min){
                Toast.makeText(context,"小于最小值"+min,Toast.LENGTH_SHORT).show();
                return "";
            }
        }
        if(temp>max){
            Toast.makeText(context,"大于最大值"+max,Toast.LENGTH_SHORT).show();
            return "";
        }
        return null;
    }
}
