package com.example.mrz.newproject.controller.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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

    //筛选按钮
    @BindView(R.id.elective_screen)
    LinearLayout elective_screen;

    private String[] titles = {"院系选修课","全校选修课"};

    @BindView(R.id.toolbar_iv)
    ImageView toolbar_iv;

    //Fragment集合
    private List<Fragment> fragments;

    //主界面
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elective);

        ButterKnife.bind(this);

        initData();

        initEnevt();
    }

    private void initEnevt() {

        elective_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.END);
            }
        });

        toolbar_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initData() {

        //设置toolbar
        Elective_toolbar.setTitle("网上选课");

        //关闭手势滑动
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        //向Fragment中添加视图
        fragments = new ArrayList<>();

        //院校性选课地址
        String academyUrl = UrlBean.IP + "/" + UrlBean.sessionId + "/" + UrlBean.selectElectiveUrl + "?xh=" + User.xh + "&xm=" + User.xm + "&gnmkdm=" + UrlBean.academyCode;
        fragments.add(ElectiveFragment.newInstance(academyUrl));

        //全校性选课地址
        String schoolUrl = UrlBean.IP + "/" + UrlBean.sessionId + "/"  + UrlBean.selectAllSchoolUrl + "?xh=" + User.xh + "&xm=" + User.xm + "&gnmkdm=" + UrlBean.schoolCode;
        fragments.add(ElectiveFragment.newInstance(schoolUrl));


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
