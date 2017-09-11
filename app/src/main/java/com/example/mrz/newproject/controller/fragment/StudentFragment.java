package com.example.mrz.newproject.controller.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mrz.newproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 那个谁 on 2017/9/10.
 * 奥特曼打小怪兽
 * 作用：选课
 */

public class StudentFragment extends Fragment {

    //余额查询
    @BindView(R.id.tv_balance)
    private TextView mTv_balance;
    //消费情况查询
    @BindView(R.id.tv_consumption)
    private TextView mTv_consumption;
    //一键挂失
    @BindView(R.id.tv_loss)
    private TextView mTv_loss;
    //网上选课
    @BindView(R.id.tv_elective)
    private TextView mTv_elective;
    //学生成绩查询
    @BindView(R.id.tv_results)
    private TextView mTv_results;
    //教学质量一键评价
    @BindView(R.id.tv_evaluation)
    private TextView mTv_evaluation;
    //学费收费情况查询
    @BindView(R.id.tv_tuition)
    private TextView mTv_tuition;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_student, null);
        ButterKnife.bind(this,view);
        return view;

    }
}
