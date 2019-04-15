package com.dengjinwen.basetool.library.function.stepDown;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class PreferencesHelper {

    public static final String APP_SHARE="step_share_prefs";

    /**
     * 计步器上次返回的步数
     */
    public static final String LAST_SENSOR_TIME="last_sensor_time";

    private static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences(APP_SHARE,0);
    }

    /**
     * 保存计步器最后返回的步数
     * @param context
     * @param stepData
     */
    public static void saveLastSensorStep(Context context,StepData stepData){
        Gson gson=new Gson();
        Type type=new TypeToken<StepData>(){}.getType();
        String json=gson.toJson(stepData,type);
        getSharedPreferences(context).edit().putString(LAST_SENSOR_TIME,json).apply();
    }

    /**
     * 获得计步器最后返回的步数
     * @param context
     * @return
     */
    public static StepData getLastSensorStep(Context context){
        Gson gson=new Gson();
        Type type=new TypeToken<StepData>(){}.getType();
        String json=getSharedPreferences(context).getString(LAST_SENSOR_TIME,null);
        if(json!=null){
            StepData stepData=gson.fromJson(json,type);
            return stepData;
        }
        return null;
    }
}
