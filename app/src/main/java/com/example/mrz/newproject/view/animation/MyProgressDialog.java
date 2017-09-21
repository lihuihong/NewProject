package com.example.mrz.newproject.view.animation;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.mrz.newproject.R;

/**
 * Created by 那个谁 on 2017/9/19.
 * 奥特曼打小怪兽
 * 作用：
 */

public class MyProgressDialog extends ProgressDialog {
    protected CircleProgress mProgress;
    protected TextView mLoadingTv;

    public MyProgressDialog(Context context) {
        super(context);
        //点击提示框外面是否取消提示框
        setCanceledOnTouchOutside(false);
        //点击返回键是否取消提示框
        setCancelable(true);
        setIndeterminate(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        initView();
        //弹出dialog
        mProgress.post(new Runnable() {
            @Override
            public void run() {
                mProgress.startAnim();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getOwnerActivity().getMenuInflater().inflate(R.menu.main_menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void initView() {
        mProgress = (CircleProgress)findViewById(R.id.progress);
        mLoadingTv = (TextView)findViewById(R.id.loadingTv);
    }


}
