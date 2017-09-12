package com.example.mrz.newproject.model.bean;

/**
 * Created by Mr.z on 2017/9/12.
 */

public class UrlBean {

    //ip地址
    public final static String IP = "http://222.180.192.2:8918";

    //链接中的sessionI
    public static String sessionId;

    //发起登录请求的地址后缀
    public static String loginUrl = "default4.aspx";

    //教务系统的首页地址后缀
    public static String mainUrl = "xs_main.aspx";

    public static void setSessionId(String sessionId) {
        UrlBean.sessionId = sessionId;
    }

}
