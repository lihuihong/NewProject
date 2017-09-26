package com.example.mrz.newproject.controller.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mrz.newproject.R;
import com.example.mrz.newproject.model.bean.User;
import com.example.mrz.newproject.model.db.MySqlHelper;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        //获取本地储存,查看是否有用户登录
        SharedPreferences pref = getSharedPreferences("userInfoData", MODE_PRIVATE);
        String name = pref.getString("userName","");
        String xhxm = pref.getString("xhxm","");

        User.setXm(xhxm);

        if(name.isEmpty()){
            //为空跳转到登录页面
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        }else{
            //否则跳到首页
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        finish();
}


}
