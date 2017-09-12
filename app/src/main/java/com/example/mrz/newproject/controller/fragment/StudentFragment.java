package com.example.mrz.newproject.controller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrz.newproject.R;
import com.example.mrz.newproject.controller.activity.BalanceActivity;
import com.example.mrz.newproject.controller.activity.ConsumptionActivity;
import com.example.mrz.newproject.controller.activity.ElectiveActivity;
import com.example.mrz.newproject.controller.activity.EvaluationActivity;
import com.example.mrz.newproject.controller.activity.LossActivity;
import com.example.mrz.newproject.controller.activity.ResultsActivity;
import com.example.mrz.newproject.controller.activity.TuitionActivity;

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
    //学费收费情况查询
    @BindView(R.id.tv_tuition)
    TextView mTv_tuition;
    @BindView(R.id.student_toolbar)
    Toolbar student_toolbar;
    View view;
    private Intent mIntent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_student, null);
            ButterKnife.bind(this, view);
            //Fragment中的onCreateOptionsMenu生效必须先调用setHasOptionsMenu方法，否则Toolbar没有菜单
            setHasOptionsMenu(true);
            //强转, 必须是AppCompatActivity才有这个方法.
            ((AppCompatActivity) getActivity()).setSupportActionBar(student_toolbar);
        }

        return view;
    }

    //toolbar菜单加载
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //toolbar点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(getActivity(), "刷新", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //监听事件
    @OnClick({R.id.tv_balance, R.id.tv_consumption, R.id.tv_loss, R.id.tv_elective,
            R.id.tv_results, R.id.tv_evaluation, R.id.tv_tuition, R.id.student_toolbar})
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
            //学费收费情况查询
            case R.id.tv_tuition:
                mIntent = new Intent(getActivity(), TuitionActivity.class);
                startActivity(mIntent);
                break;
            //网上选课
            case R.id.tv_elective:
                mIntent = new Intent(getActivity(), ElectiveActivity.class);
                startActivity(mIntent);
                break;

        }

    }
}
