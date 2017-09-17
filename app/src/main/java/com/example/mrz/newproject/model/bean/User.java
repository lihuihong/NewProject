package com.example.mrz.newproject.model.bean;

/**
 * Created by Mr.z on 2017/9/12.
 */

public class User {

    //学号
    public static String xh;

    //学生姓名
    public static String xm;
    //身份证后六位

    private static String id;

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        User.id = id;
    }

    public static void setXh(String xh) {
        User.xh = xh;
    }

    public static void setXm(String xm) {
        User.xm = xm;
    }

}
