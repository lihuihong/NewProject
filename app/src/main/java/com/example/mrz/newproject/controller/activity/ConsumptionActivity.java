package com.example.mrz.newproject.controller.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mrz.newproject.R;

//消费情况查询页面
public class ConsumptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
