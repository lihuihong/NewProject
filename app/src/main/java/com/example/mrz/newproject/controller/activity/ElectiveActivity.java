package com.example.mrz.newproject.controller.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.mrz.newproject.R;
import com.example.mrz.newproject.controller.adapter.FragmentPagerAdapter;
import com.example.mrz.newproject.controller.fragment.ElectiveFragment;

import com.example.mrz.newproject.model.bean.UrlBean;
import com.example.mrz.newproject.model.bean.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//网上选课页面
public class ElectiveActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar Elective_toolbar;

    @BindView(R.id.elective_tab_class)
    TabLayout tabLayout;

    @BindView(R.id.elective_vp_class)
    ViewPager vp_class;

    private String[] titles = {"院系选修课","全校性选修课"};

    //Fragment集合
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elective);

        ButterKnife.bind(this);

        initData();
    }

    private void initData() {

        //设置toolbar
        Elective_toolbar.setTitle("网上选课");

        //向Fragment中添加视图
        fragments = new ArrayList<>();

        //院校性选课地址
        String academyUrl = UrlBean.IP + "/" + UrlBean.sessionId + "/" + UrlBean.selectElectiveUrl + "?xh=" + User.xh + "&xm=" + User.xm + "&gnmkdm=" + UrlBean.academyCode;
        fragments.add(new ElectiveFragment(ElectiveActivity.this,academyUrl));

        //全校性选课地址
        String schoolUrl = UrlBean.IP + "/" + UrlBean.sessionId + "/"  + UrlBean.selectAllSchoolUrl + "?xh=" + User.xh + "&xm=" + User.xm + "&gnmkdm=" + UrlBean.schoolCode;
        fragments.add(new ElectiveFragment(ElectiveActivity.this,schoolUrl));


        //设置数据适配器
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager(),fragments,titles);
        vp_class.setAdapter(adapter);

        //将viewpager绑定tablayout
        tabLayout.setupWithViewPager(vp_class);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
