package com.example.mrz.newproject.model.dao;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.mrz.newproject.controller.activity.LoginActivity;

/**
 * Created by Mr.z on 2017/9/11.
 */

public class LoginDao {




    /**
     * 登录逻辑处理过程
     *
     * @param userName  用户名
     * @param passwd  密码
     * @return
     */
    public static int login(String userName, String passwd) {
        return -1;
    }


    /**
     * 判断字符串是否全为数字
     *
     * @param str 判断的字符串
     * @return
     */
    public static boolean isNumeric(String str) {

        try{
            Log.d("message",str);
            Integer.parseInt(str);
        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }

    /**
     * 检查用户名和密码是否合格
     *
     * @param userName 用户名
     * @param passwd 密码
     * @return  -1:用户名为空   -2:密码为空   -3:用户名不合法   1:检查通过
     */
    public static int check(String userName, String passwd) {
        if(TextUtils.isEmpty(userName))
            return -1;
        else if(TextUtils.isEmpty(passwd))
            return -2;
        else if(!isNumeric(userName))
            return -3;
        else
            return 1;
    }
}
