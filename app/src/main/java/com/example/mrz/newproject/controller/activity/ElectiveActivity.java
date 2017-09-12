package com.example.mrz.newproject.controller.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mrz.newproject.R;

//网上选课页面
public class ElectiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elective);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
