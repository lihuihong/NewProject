package com.example.mrz.newproject.controller.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mrz.newproject.R;


//学费收费情况查询
public class TuitionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuition);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
