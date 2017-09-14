package com.example.mrz.newproject.controller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mrz.newproject.R;
import com.example.mrz.newproject.controller.activity.DocumentActivity;
import com.example.mrz.newproject.controller.activity.UserInfoActivity;
import com.example.mrz.newproject.controller.activity.OpinionActivity;
import com.example.mrz.newproject.controller.activity.QqActivity;
import com.example.mrz.newproject.controller.activity.SettingActivity;
import com.example.mrz.newproject.model.bean.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 那个谁 on 2017/9/10.
 * 奥特曼打小怪兽
 * 作用：我
 */

public class MyFragment extends Fragment {
    //Toobar
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    //姓名头部
    @BindView(R.id.ll_avatar)
    LinearLayout mLl_avatar;
    //姓名
    @BindView(R.id.my_name)
    TextView mMy_name;
    //学号
    @BindView(R.id.my_xh)
    TextView mMy_xh;
    //意见提交
    @BindView(R.id.ll_opinion)
    LinearLayout mLl_opinion;
    //加入QQ群
    @BindView(R.id.ll_qq)
    LinearLayout mLl_qq;
    //免费声明
    @BindView(R.id.ll_document)
    LinearLayout mLl_document;
    //软件设置
    @BindView(R.id.ll_setting)
    LinearLayout mLl_setting;

    View view;

    private Intent mIntent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null){
            view = inflater.inflate(R.layout.fragment_my,null);
            ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
            setHasOptionsMenu(true);
            ButterKnife.bind(this,view);
        }
        mMy_name.setText(User.xm);
        mMy_xh.setText(User.xh);
        return view;
    }

    @OnClick({R.id.ll_avatar,R.id.ll_opinion,R.id.ll_qq,R.id.ll_document,R.id.ll_setting})
    public void myOnClick(LinearLayout ll){
        switch (ll.getId()){
            //头部详情
            case R.id.ll_avatar:
                mIntent = new Intent(getActivity(), UserInfoActivity.class);
                startActivity(mIntent);
                break;
            //意见提交
            case R.id.ll_opinion:
                mIntent = new Intent(getActivity(), OpinionActivity.class);
                startActivity(mIntent);
                break;
            //加入QQ群
            case R.id.ll_qq:
                mIntent = new Intent(getActivity(), QqActivity.class);
                startActivity(mIntent);
                break;
            //免费声明
            case R.id.ll_document:
                mIntent = new Intent(getActivity(), DocumentActivity.class);
                startActivity(mIntent);
                break;
            //软件设置
            case R.id.ll_setting:
                mIntent = new Intent(getActivity(), SettingActivity.class);
                startActivity(mIntent);
                break;
        }
    }
}
