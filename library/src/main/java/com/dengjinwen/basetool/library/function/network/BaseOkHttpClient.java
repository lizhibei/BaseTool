package com.dengjinwen.basetool.library.function.network;

import com.dengjinwen.basetool.library.tool.log;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class BaseOkHttpClient {

    private static OkHttpClient okHttpClient;
    private static long TIME_OUT = 15;
    private static HashMap<String,String> params;
    private static HashMap<String,String> headers;

    //声明日志类
    private static HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {

        @Override

        public void log(String message) {
                log.e("RetrofitLog:"+message);
        }

    });

    public static OkHttpClient getOkHttpClient(){
        if(okHttpClient==null){
            synchronized (BaseOkHttpClient.class){
                if(okHttpClient==null){
                    okHttpClient=createOkHttpClient(params,headers);
                }
            }
        }
        return okHttpClient;
    }

    public static OkHttpClient createNewOkHttpClient(HashMap<String, String> params,HashMap<String,String> headers){
        return createOkHttpClient(params,headers);
    }

    private static OkHttpClient createOkHttpClient(HashMap<String, String> params,HashMap<String,String> headers){
        return new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT,TimeUnit.SECONDS)
                .readTimeout(TIME_OUT,TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .addNetworkInterceptor(new AddParameterAndHeaderInterceptor(params,headers))
                .build();
    }

    public static HashMap<String, String> getParams() {
        return params;
    }

    public static void setParams(HashMap<String, String> params) {
        BaseOkHttpClient.params = params;
    }

    public static HashMap<String, String> getHeaders() {
        return headers;
    }

    public static void setHeaders(HashMap<String, String> headers) {
        BaseOkHttpClient.headers = headers;
    }
}
