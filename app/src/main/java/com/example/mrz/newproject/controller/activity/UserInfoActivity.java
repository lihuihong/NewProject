package com.example.mrz.newproject.controller.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RelativeLayout;

import com.example.mrz.newproject.R;
import com.example.mrz.newproject.controller.adapter.UserInfoRecyclerAdapter;
import com.example.mrz.newproject.model.bean.UrlBean;
import com.example.mrz.newproject.model.bean.User;
import com.example.mrz.newproject.model.bean.UserInfoKVP;
import com.example.mrz.newproject.model.dao.GSUserInfoDao;
import com.example.mrz.newproject.view.DividerItemDecoration;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.mrz.newproject.R.id.progress_view;

//头部详情页  BalanceActivity
public class UserInfoActivity extends AppCompatActivity {

    private  final int GET_USERINFO_SUCCED = 0x111111;
    private  final int GET_USERINFO_FAILED = 0X111110;

    //用户基本信息
    @BindView(R.id.userinfo_basic)
    RecyclerView userinfo_basic;

    //用户联系信息
    @BindView(R.id.userinfo_conn)
    RecyclerView userinfo_conn;


    //用户学校信息
    @BindView(R.id.userinfo_school)
    RecyclerView userinfo_school;

    public static Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

        ButterKnife.bind(this);

        initData();

    }

    private  void initData() {

        userinfo_basic.addItemDecoration(new DividerItemDecoration(UserInfoActivity.this,DividerItemDecoration.VERTICAL_LIST));

        //将基本信息设置为垂直布局
        userinfo_basic.setLayoutManager(new LinearLayoutManager(UserInfoActivity.this));
        //解析基本信息
        List<UserInfoKVP> basicInfos = (List<UserInfoKVP>) getIntent().getSerializableExtra("basicInfos");
        //为基本信息设置适配器
        userinfo_basic.setAdapter(new UserInfoRecyclerAdapter(UserInfoActivity.this,basicInfos));


        userinfo_conn.addItemDecoration(new DividerItemDecoration(UserInfoActivity.this,DividerItemDecoration.VERTICAL_LIST));
        //将联系信息设置为垂直布局
        userinfo_conn.setLayoutManager(new LinearLayoutManager(UserInfoActivity.this));
        //解析练习信息
        List<UserInfoKVP> connInfos = (List<UserInfoKVP>) getIntent().getSerializableExtra("connInfos");
        //为联系信息设置适配器
        userinfo_conn.setAdapter(new UserInfoRecyclerAdapter(UserInfoActivity.this,connInfos));


        userinfo_school.addItemDecoration(new DividerItemDecoration(UserInfoActivity.this,DividerItemDecoration.VERTICAL_LIST));
        //将学校信息设置为垂直布局
        userinfo_school.setLayoutManager(new LinearLayoutManager(UserInfoActivity.this));
        //解析学校信息
        List<UserInfoKVP> schoolInfos = (List<UserInfoKVP>) getIntent().getSerializableExtra("schoolInfos");
        Log.d("body","school_"+schoolInfos.size());
        //为学校信息设置适配器
        userinfo_school.setAdapter(new UserInfoRecyclerAdapter(UserInfoActivity.this,schoolInfos));


    }


}
