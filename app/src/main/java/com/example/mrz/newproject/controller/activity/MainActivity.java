package com.example.mrz.newproject.controller.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.mrz.newproject.R;
import com.example.mrz.newproject.controller.adapter.FragmentPagerAdapter;
import com.example.mrz.newproject.controller.fragment.IndexFragment;
import com.example.mrz.newproject.controller.fragment.MyFragment;
import com.example.mrz.newproject.controller.fragment.StudentFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ashokvarma.bottomnavigation.BottomNavigationBar.BACKGROUND_STYLE_RIPPLE;
import static com.ashokvarma.bottomnavigation.BottomNavigationBar.BACKGROUND_STYLE_STATIC;
import static com.ashokvarma.bottomnavigation.BottomNavigationBar.MODE_FIXED;


/**
 *
 * 主界面
 */

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener, ViewPager.OnPageChangeListener {

    @BindView(R.id.vp_home)
    public ViewPager mViewPager;
    @BindView(R.id.bb_home)
    public BottomNavigationBar mNavigationBar;

    //Fragment集合
    private List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        intiView();

    }

    private void intiView() {
        initViewPager();
        initBottomNavigationBar();
    }

    //viewpager相关操作
    private void initViewPager() {

        mViewPager.setOffscreenPageLimit(1);

        mFragments = new ArrayList<>();
        //向Fragment中添加视图
        mFragments.add(new StudentFragment());
        mFragments.add(new IndexFragment());
        mFragments.add(new MyFragment());
        //设置adapter
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), mFragments));
        //设置viewpager监听
        mViewPager.addOnPageChangeListener(this);
        //默认选中第一个视图
        mViewPager.setCurrentItem(1);
    }

    //底部导航栏
    private void initBottomNavigationBar() {

        mNavigationBar.setTabSelectedListener(this);
        mNavigationBar.clearAll();
        //点击的时候有水波纹效果
        mNavigationBar.setBackgroundStyle(BACKGROUND_STYLE_RIPPLE);
        //mNavigationBar.setMode(mNavigationBar.MODE_DEFAULT);
        //换挡模式，未选中的Item不会显示文字，选中的会显示文字。在切换的时候会有一个像换挡的动画
        mNavigationBar.setMode(MODE_FIXED);
        mNavigationBar.setFitsSystemWindows(true);
        //点击的时候没有水波纹效果
        mNavigationBar.setBackgroundStyle(BACKGROUND_STYLE_STATIC);
        mNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.student_press, "功能")
                        .setInactiveIcon(ContextCompat.getDrawable(this, R.drawable.student)))
                .addItem(new BottomNavigationItem(R.drawable.index_press, "课表")
                        .setInactiveIcon(ContextCompat.getDrawable(this, R.drawable.index)))
                .addItem(new BottomNavigationItem(R.drawable.my_press, "个人")
                        .setInactiveIcon(ContextCompat.getDrawable(this, R.drawable.my)))
                .setFirstSelectedPosition(1)
                .initialise();

    }

    //底部导航栏关联viewpager
    @Override
    public void onTabSelected(int position) {
        mViewPager.setCurrentItem(position);

    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    //viewpager关联底部导航栏
    @Override
    public void onPageSelected(int position) {
        mNavigationBar.selectTab(position);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
