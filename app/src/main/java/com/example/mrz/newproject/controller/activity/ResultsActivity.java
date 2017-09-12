package com.example.mrz.newproject.controller.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mrz.newproject.R;

//学生成绩查询
public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
