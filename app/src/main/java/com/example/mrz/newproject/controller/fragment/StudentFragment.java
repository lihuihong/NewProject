package com.example.mrz.newproject.controller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrz.newproject.R;
import com.example.mrz.newproject.controller.activity.BalanceActivity;
import com.example.mrz.newproject.controller.activity.ConsumptionActivity;
import com.example.mrz.newproject.controller.activity.ElectiveActivity;
import com.example.mrz.newproject.controller.activity.EvaluationActivity;
import com.example.mrz.newproject.controller.activity.LossActivity;
import com.example.mrz.newproject.controller.activity.ResultsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 那个谁 on 2017/9/10.
 * 奥特曼打小怪兽
 * 作用：选课
 */

public class StudentFragment extends Fragment {

    //余额查询
    @BindView(R.id.tv_balance)
    TextView mTv_balance;
    //消费情况查询
    @BindView(R.id.tv_consumption)
    TextView mTv_consumption;
    //一键挂失
    @BindView(R.id.tv_loss)
    TextView mTv_loss;
    //网上选课
    @BindView(R.id.tv_elective)
    TextView mTv_elective;
    //学生成绩查询
    @BindView(R.id.tv_results)
    TextView mTv_results;
    //教学质量一键评价
    @BindView(R.id.tv_evaluation)
    TextView mTv_evaluation;
    //刷新图标
    @BindView(R.id.iv_refresh)
    ImageView mIv_refresh;
    //toolbar
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    View view;
    private Intent mIntent;
    //刷新动画
    private Animation mAnimation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_student, null);
            ButterKnife.bind(this, view);
            ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
            setHasOptionsMenu(true);
        }

        return view;
    }

    //刷新事件
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1000:
                    if (mAnimation != null) {
                        mIv_refresh.clearAnimation();
                        Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    //监听事件
    @OnClick({R.id.tv_balance, R.id.tv_consumption, R.id.tv_loss, R.id.tv_elective,
            R.id.tv_results, R.id.tv_evaluation})
    public void myButton(TextView btn) {
        switch (btn.getId()) {
            //余额查询
            case R.id.tv_balance:
                mIntent = new Intent(getActivity(), BalanceActivity.class);
                startActivity(mIntent);
                break;
            //消费情况查询
            case R.id.tv_consumption:
                mIntent = new Intent(getActivity(), ConsumptionActivity.class);
                startActivity(mIntent);
                break;
            //一键挂失
            case R.id.tv_loss:
                mIntent = new Intent(getActivity(), LossActivity.class);
                startActivity(mIntent);
                break;
            //学生成绩查询
            case R.id.tv_results:
                mIntent = new Intent(getActivity(), ResultsActivity.class);
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

    //刷新按钮点击事件
    @OnClick(R.id.iv_refresh)
    public void myRefresh() {
        startAnimation();
        mHandler.sendEmptyMessageDelayed(1000, 3000);
    }

    //动画事件
    private void startAnimation() {
        mAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.iv_rotating);
        //插值器
        LinearInterpolator ll = new LinearInterpolator();
        mAnimation.setInterpolator(ll);
        if (mAnimation != null) {
            //开始动画
            mIv_refresh.startAnimation(mAnimation);
        }
    }

}
