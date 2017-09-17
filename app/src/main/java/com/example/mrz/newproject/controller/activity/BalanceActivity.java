package com.example.mrz.newproject.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.mrz.newproject.R;

import butterknife.ButterKnife;

//余额查询页面
public class BalanceActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

        ButterKnife.bind(this);
        //初始化数据
    }



}
