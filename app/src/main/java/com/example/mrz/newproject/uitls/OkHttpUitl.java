package com.example.mrz.newproject.uitls;

import okhttp3.OkHttpClient;

/**
 * okhttp单例模式帮助类
 *
 * Created by Mr.z on 2017/9/3.
 */


public class OkHttpUitl{

    private static OkHttpClient okHttpClient;

    private OkHttpUitl(){}

    public static OkHttpClient getInstance(){
        if(okHttpClient == null){
            synchronized (OkHttpUitl.class){
                if(okHttpClient == null) {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder.followRedirects(false);
                    builder.cookieJar(new MyCookieJar());
                    okHttpClient = builder.build();
                }
            }
        }
        return okHttpClient;
    }


    public static void resetCookies(){
        MyCookieJar.resetCookies();
    }

}
