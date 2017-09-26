package com.example.mrz.newproject.controller.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.mrz.newproject.R;
import com.example.mrz.newproject.controller.activity.BalanceActivity;
import com.example.mrz.newproject.controller.activity.ConSelectActivity;
import com.example.mrz.newproject.controller.activity.ElectiveActivity;
import com.example.mrz.newproject.controller.activity.EvaluationActivity;
import com.example.mrz.newproject.controller.activity.LossActivity;
import com.example.mrz.newproject.controller.activity.ScoreSelectActivity;
import com.example.mrz.newproject.model.dao.EcardDao;
import com.example.mrz.newproject.model.dao.LoginDao;
import com.example.mrz.newproject.view.animation.CircleProgress;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.mrz.newproject.R.id.tv_balance;

/**
 * Created by 那个谁 on 2017/9/10.
 * 奥特曼打小怪兽
 * 作用：选课
 */

public class StudentFragment extends ViewPagerFragment {

    private static final int LOGIN_SUCCED = 0X111111;
    //余额查询
    @BindView(tv_balance)
    LinearLayout mTv_balance;
    //消费情况查询
    @BindView(R.id.tv_consumption)
    LinearLayout mTv_consumption;
    //一键挂失
    @BindView(R.id.tv_loss)
    LinearLayout mTv_loss;
    //网上选课
    @BindView(R.id.tv_elective)
    LinearLayout mTv_elective;
    //学生成绩查询
    @BindView(R.id.tv_results)
    LinearLayout mTv_results;
    //教学质量一键评价
    @BindView(R.id.tv_evaluation)
    LinearLayout mTv_evaluation;

    View view;
    private Intent mIntent;

    private Context context;

    /** 标志位，标志已经初始化完成 */
    private boolean isPrepared;
    /** 是否已被加载过一次，第二次就不再去请求数据了 */
    private boolean mHasLoadedOnce;

    private Handler mHandler;

    @BindView(R.id.ll_progress)
    LinearLayout ll_progress;
    @BindView(R.id.progress)
    CircleProgress progress;

    @BindView(R.id.ll_main)
    LinearLayout ll_main;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_student, null);
            context = getContext();
            ButterKnife.bind(this, view);
            isPrepared = true;
            lazyLoad();
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                progress.stopAnim();
                ll_progress.setVisibility(View.GONE);
                ll_main.setVisibility(View.VISIBLE);
            }
        };

        super.onActivityCreated(savedInstanceState);
    }


    //监听事件
    @OnClick({tv_balance, R.id.tv_consumption, R.id.tv_loss, R.id.tv_elective,
            R.id.tv_results, R.id.tv_evaluation})
    public void myButton(LinearLayout btn) {
        switch (btn.getId()) {
            //余额查询
            case tv_balance:
                mIntent = new Intent(getActivity(), BalanceActivity.class);
                startActivity(mIntent);
                break;
            //消费情况查询
            case R.id.tv_consumption:
                mIntent = new Intent(getActivity(), ConSelectActivity.class);
                startActivity(mIntent);
                break;
            //一键挂失
            case R.id.tv_loss:
                mIntent = new Intent(getActivity(), LossActivity.class);
                startActivity(mIntent);
                break;
            //学生成绩查询
            case R.id.tv_results:
                mIntent = new Intent(getActivity(), ScoreSelectActivity.class);
                startActivity(mIntent);
                break;
            //教学质量一键评价
            case R.id.tv_evaluation:
                mIntent = new Intent(getActivity(), EvaluationActivity.class);
                startActivity(mIntent);
                break;
            //网上选课
            case R.id.tv_elective:
                mIntent = new Intent(getActivity(), ElectiveActivity.class);
                startActivity(mIntent);
                break;
        }

    }


    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoadedOnce) {
            return;
        }

        progress.startAnim();

        //如果没登录就登录教务系统
        new Thread(){
            @Override
            public void run() {

                //获取本地储存,查看是否有用户登录
                SharedPreferences pref = context.getSharedPreferences("userInfoData", context.MODE_PRIVATE);
                String name = pref.getString("userName","");
                String eardpwd = pref.getString("eardpwd","");

                if(!LoginDao.isLogin){
                    //教务系统密码
                    final String pwd = pref.getString("passWord", "");
                    try {
                        LoginDao.login(context,name,pwd);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    EcardDao.login(name,eardpwd);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(LOGIN_SUCCED);
            }
        }.start();

        mHasLoadedOnce = true;
    }
}
