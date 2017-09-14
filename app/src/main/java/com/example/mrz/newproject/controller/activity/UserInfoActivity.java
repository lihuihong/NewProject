package com.example.mrz.newproject.controller.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.mrz.newproject.R;
import com.example.mrz.newproject.controller.adapter.UserInfoRecyclerAdapter;
import com.example.mrz.newproject.model.bean.UrlBean;
import com.example.mrz.newproject.model.bean.User;
import com.example.mrz.newproject.model.bean.UserInfoKVP;
import com.example.mrz.newproject.model.dao.GSUserInfoDao;
import com.example.mrz.newproject.view.DividerItemDecoration;

import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//头部详情页
public class UserInfoActivity extends AppCompatActivity {

    private final int GET_USERINFO_SUCCED = 0x111111;
    private final int GET_USERINFO_FAILED = 0X111110;

    //用户基本信息
    @BindView(R.id.userinfo_basic)
    RecyclerView userinfo_basic;

    /*//用户地址信息
    @BindView(R.id.userinfo_address)
    RecyclerView userinfo_address;*/

    //用户学校信息
    @BindView(R.id.userinfo_school)
    RecyclerView userinfo_school;

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

        ButterKnife.bind(this);

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {

                switch (msg.what){
                    case GET_USERINFO_SUCCED:

                        userinfo_basic.addItemDecoration(new DividerItemDecoration(UserInfoActivity.this,DividerItemDecoration.VERTICAL_LIST));

                        //将基本信息设置为垂直布局
                        userinfo_basic.setLayoutManager(new LinearLayoutManager(UserInfoActivity.this));
                        //解析基本信息
                        List<UserInfoKVP> basicInfos = GSUserInfoDao.getbasicInfo((Elements) msg.obj);
                        //为基本信息设置适配器
                        userinfo_basic.setAdapter(new UserInfoRecyclerAdapter(UserInfoActivity.this,basicInfos));


                        userinfo_school.addItemDecoration(new DividerItemDecoration(UserInfoActivity.this,DividerItemDecoration.VERTICAL_LIST));
                        //将学校信息设置为垂直布局
                        userinfo_school.setLayoutManager(new LinearLayoutManager(UserInfoActivity.this));
                        //解析学校信息
                        List<UserInfoKVP> schoolInfo = GSUserInfoDao.getSchoolInfo((Elements) msg.obj);
                        //为学校信息设置适配器
                        userinfo_school.setAdapter(new UserInfoRecyclerAdapter(UserInfoActivity.this,schoolInfo));


                        break;
                    case GET_USERINFO_FAILED:

                        break;
                }
            }
        };

        initData();

    }

    private void initData() {

        new Thread(){
            @Override
            public void run() {
                Message msg = new Message();

                //个人信息url地址
                String getUserInfoUrl = UrlBean.IP + "/" + UrlBean.sessionId + "/" + UrlBean.userInfoUrl + "?xh=" + User.xh + "&xm=" + User.xm + "&gnmkdm=" + UrlBean.userInfoCode;

                try {

                    //获取全部个人信息
                    Elements allUserInfo = GSUserInfoDao.getAllUserInfo(getUserInfoUrl);

                    msg.obj = allUserInfo;
                    msg.what = GET_USERINFO_SUCCED;

                    mHandler.sendMessage(msg);

                } catch (IOException e) {

                    msg.what = GET_USERINFO_FAILED;
                    mHandler.sendMessage(msg);
                }
            }
        }.start();
    }


}
