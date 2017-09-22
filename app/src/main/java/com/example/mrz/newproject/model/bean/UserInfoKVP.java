package com.example.mrz.newproject.model.bean;

import java.io.Serializable;

/**
 * 保存用户信息的键值对
 *
 * Created by Mr.z on 2017/9/14.
 */

public class UserInfoKVP implements Serializable{

    private String key;
    private String value;

    public UserInfoKVP(String key,String value){
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
