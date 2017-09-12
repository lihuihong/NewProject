package com.example.mrz.newproject.controller.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrz.newproject.R;
import com.example.mrz.newproject.model.dao.LoginDao;
import com.example.mrz.newproject.uitls.DensityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    private float mWidth, mHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        //添加点击事件
        initEvent();
    }

    private void initEvent() {
        login_post.setOnClickListener(this);
        login_help.setOnClickListener(this);
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()){
            case R.id.login_post:

                String userName = login_userName.getText().toString();
                String password = login_passWord.getText().toString();


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

                    // 隐藏输入框
                    //login_post.setVisibility(View.INVISIBLE);
                    login_post.setText("");
                    //mPsw.setVisibility(View.INVISIBLE);

                    //启动动画
                    mWidth = login_post.getMeasuredWidth();
                    mHeight = login_post.getMeasuredHeight();
                    inputAnimator(login_post, mWidth, mHeight);


                    //获取登录状态码
                    int loginCode = LoginDao.login(userName,password);

                    //-1：登录失败
                    if(loginCode == 1){

                        new AlertDialog.Builder(this)
                                .setTitle("登录失败")
                                .setMessage("学号或者密码错误，请重新输入！")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        View childAt = ll_login_post.getChildAt(0);
                                        ll_login_post.removeView(childAt);

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
                                        ll_login_post.addView(login_post);


                                        Log.d("message", String.valueOf(ll_login_post.getVisibility()));



                                        progress.setVisibility(View.GONE);

                                        login_userName.setText(null);
                                        login_passWord.setText(null);
                                    }
                                })
                                .show();
                    }else{
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }


                    break;
                }
                break;
            case R.id.login_help:

                break;

        }
    }


    /**
     * 输入框的动画效果
     *
     * @param view
     *      控件
     * @param w
     *      宽
     * @param h
     *      高
     */
    private void inputAnimator(final View view, float w, float h) {

        AnimatorSet set = new AnimatorSet();

        ValueAnimator animator = ValueAnimator.ofFloat(0, w);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view
                        .getLayoutParams();

                params.leftMargin = (int) value;
                params.rightMargin = (int) value;
                view.setLayoutParams(params);
            }
        });

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(login_post,
                "scaleX", 1f, 0.5f);
        set.setDuration(1000);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(animator, animator2);
        set.start();
        set.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                /**
                 * 动画结束后，先显示加载的动画，然后再隐藏输入框
                 */
                progress.setVisibility(View.VISIBLE);
                progressAnimator(progress);
                login_post.setVisibility(View.GONE);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }
        });

    }

    /**
     * 出现进度动画
     *
     * @param view
     */
    private void progressAnimator(final View view) {
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX",
                0.5f, 1f);
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY",
                0.5f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view,
                animator, animator2);
        animator3.setDuration(1000);
        animator3.setInterpolator(new JellyInterpolator());
        animator3.start();

    }


    /**
     * 自定义插值器
     */
    class JellyInterpolator extends LinearInterpolator {
        private float factor;

        public JellyInterpolator() {
            this.factor = 0.15f;
        }

        @Override
        public float getInterpolation(float input) {
            return (float) (Math.pow(2, -10 * input)
                    * Math.sin((input - factor / 4) * (2 * Math.PI) / factor) + 1);
        }
    }

}
