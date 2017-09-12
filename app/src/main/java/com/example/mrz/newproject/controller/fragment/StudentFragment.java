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
    //学费收费情况查询
    @BindView(R.id.tv_tuition)
    TextView mTv_tuition;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student, null);
        ButterKnife.bind(this,view);
        return view;

    }
}
