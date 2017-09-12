package com.example.mrz.newproject.controller.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrz.newproject.R;
import com.example.mrz.newproject.model.dao.LoginDao;
import com.example.mrz.newproject.uitls.DBUtils;
import com.example.mrz.newproject.uitls.DensityUtils;
import com.example.mrz.newproject.view.LoginAnimator;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cqcet_三猿 on 2017/9/10.
 * Mr.z
 * 作用：登录界面
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.login_username)
    EditText login_userName;
    @BindView(R.id.login_password)
    EditText login_passWord;

    @BindView(R.id.login_post)
    Button login_post;
    @BindView(R.id.login_help)
    TextView login_help;

    @BindView(R.id.login_pb)
    View progress;

    @BindView(R.id.ll_login_post)
    LinearLayout ll_login_post;

    private Handler mHandler;

    public final int LOGIN_SUCCED = 0x11111;
    public final int LOGIN_FAILED = 0X11112;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        //添加点击事件
        initEvent();


        //初始化handler
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case LOGIN_SUCCED:
                        loginSuccedHandler();
                        finish();
                        break;
                    case LOGIN_FAILED:
                        loginFailedHandler();
                        break;
                }
            }
        };
    }

    //初始化事件
    private void initEvent() {
        login_post.setOnClickListener(this);
        login_help.setOnClickListener(this);

        //获取本地储存，如果学号和密码存在就读取
        SharedPreferences pref =getSharedPreferences("userInfoData", MODE_PRIVATE);
        login_userName.setText(pref.getString("userName",""));
        login_passWord.setText(pref.getString("passWord",""));
    }


    //登录成功处理
    private void loginSuccedHandler(){

        //如果登录成功，就将学号和密码保存在本地
        SharedPreferences.Editor editor = getSharedPreferences("userInfoData", MODE_PRIVATE).edit();
        editor.putString("userName", login_userName.getText().toString());
        editor.putString("passWord", login_passWord.getText().toString());
        editor.apply();

        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//        Log.d("message",Build.VERSION.SDK_INT+"");
//        Bundle sharedView = ActivityOptionsCompat.makeSceneTransitionAnimation(this, ll_login_post, "sharedView").toBundle();
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            //启动动画
//          startActivity(intent, sharedView);
//            //startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, ll_login_post, "sharedView").toBundle());
//        }else{
//            startActivity(intent);
//        }
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }



    //登录失败处理
    private void loginFailedHandler(){
        new AlertDialog.Builder(this)
                .setTitle("登录失败")
                .setMessage("学号或者密码错误，请检查！")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //获取登录按钮的外层linearlayout
                        View childAt = ll_login_post.getChildAt(0);
                        //删除登录按钮
                        ll_login_post.removeView(childAt);

                        //新建登录按钮，再设置样式
                        login_post = new Button(LoginActivity.this);
                        login_post.setText("登录");
                        login_post.setText("立即登录");
                        login_post.setTextSize(22);
                        LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParam.setMargins(0, DensityUtils.dip2px(LoginActivity.this,20), 0, 0);
                        login_post.setLayoutParams(layoutParam);
                        login_post.setBackgroundResource(R.drawable.login_botton_bg);
                        login_post.setTextColor(getResources().getColor(R.color.white));
                        login_post.setId(R.id.login_post);
                        login_post.setOnClickListener(LoginActivity.this);

                        //添加登录按钮
                        ll_login_post.addView(login_post);

                        //隐藏加载条
                        progress.setVisibility(View.GONE);

                        //并将密码输入框清空
                        //login_userName.setText(null);
                        login_passWord.setText(null);
                    }
                })
                .show();
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()){

            //点击的是登录按钮
            case R.id.login_post:

                final String userName = login_userName.getText().toString();
                final String password = login_passWord.getText().toString();


                //传入用户名和密码,检查是否合格
                int checkCode = LoginDao.check(userName,password);
                switch (checkCode){
                case -1:
                    Toast.makeText(this,"学号不能为空，请输入",Toast.LENGTH_SHORT).show();
                    break;
                case -2:
                    Toast.makeText(this,"密码不能为空，请输入",Toast.LENGTH_SHORT).show();
                    break;
                case -3:
                    //显示对话框
                    new AlertDialog.Builder(this)
                            .setTitle("登录失败")
                            .setMessage("请输入正确的账号!")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    login_userName.setText(null);
                                }
                            })
                            .show();
                    break;
                default:

                    login_userName.setFocusable(false);
                    login_passWord.setFocusable(false);
                    // 隐藏输入框
                    //login_post.setVisibility(View.INVISIBLE);
                    login_post.setText("");
                    //mPsw.setVisibility(View.INVISIBLE);

                    //启动动画
                    float mWidth = login_post.getMeasuredWidth();
                    float mHeight = login_post.getMeasuredHeight();
                    LoginAnimator animator = new LoginAnimator(progress);
                    animator.inputAnimator(login_post, mWidth, mHeight);


                    //获取登录状态码,应需要访问网络，所以需要子线程
                    new Thread(){
                        @Override
                        public void run() {
                            int loginCode = 0;
                            try {
                                loginCode = LoginDao.login(userName,password);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Message msg = new Message();
                            // 非1：登录失败 1：登录成功
                            if(loginCode == 1){
                                msg.what = LOGIN_SUCCED;
                                mHandler.sendEmptyMessage(msg.what);
                            }else{
                                msg.what = LOGIN_FAILED;
                                mHandler.sendEmptyMessage(msg.what);
                            }
                        }
                    }.start();
                    break;
                }
                break;
            //点击了登录帮助
            case R.id.login_help:

                break;

        }
    }




}
