package com.example.mrz.newproject.controller.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.example.mrz.newproject.R;
import com.example.mrz.newproject.model.bean.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.tv_splash)
    TextView tv_splash;
    //组合动画
    private AnimationSet set;
    //时间
    private int animTime = 1500;

    private String mName;
    private String mXhxm;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1001:
                    //获取本地储存,查看是否有用户登录
                    SharedPreferences pref = getSharedPreferences("userInfoData", MODE_PRIVATE);
                    mName = pref.getString("userName","");
                    mXhxm = pref.getString("xhxm","");
                    User.setXm(mXhxm);
                    if(mName.isEmpty()){
                        //为空跳转到登录页面
                        Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }else{
                        //否则跳到首页
                        Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                    finish();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        intiView();
}
    private void intiView() {
        //是否同用一个动画补间
        set = new AnimationSet(true);

        set.setDuration(animTime);
        //动画结束后保持状态
        set.setFillAfter(true);
        //缩放
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1);
        scaleAnimation.setDuration(animTime);
        set.addAnimation(scaleAnimation);
        //平移
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, 0);
        translateAnimation.setDuration(animTime);
        set.addAnimation(translateAnimation);
        //执行动画
        tv_splash.startAnimation(set);
        //动画监听
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            //动画结束
            @Override
            public void onAnimationEnd(Animation animation) {

                mHandler.sendEmptyMessageAtTime(1001, 500);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}
