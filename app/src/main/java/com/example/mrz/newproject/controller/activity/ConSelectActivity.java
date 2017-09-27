package com.example.mrz.newproject.controller.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mrz.newproject.R;
import com.example.mrz.newproject.model.bean.Consume;
import com.example.mrz.newproject.model.bean.UrlBean;
import com.example.mrz.newproject.model.dao.QueryDataDao;
import com.example.mrz.newproject.view.animation.CircleProgress;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConSelectActivity extends AppCompatActivity {

    @BindView(R.id.rl_content)
     RelativeLayout mRl_content;
    //月份选择
    @BindView(R.id.con_term_sp)
     Spinner mCon_term_sp;
    //今日交易
    @BindView(R.id.con_btn_today)
     Button mCon_btn_today;
    //月交易
    @BindView(R.id.con_btn_month)
     Button mCon_btn_month;
    //历史交易
    @BindView(R.id.con_btn_all)
     Button mCon_btn_all;
    //加载动画
    @BindView(R.id.ll_progress)
     LinearLayout mLl_progress;

    @BindView(R.id.textView)
    TextView mTextView;

    @BindView(R.id.progress)
    CircleProgress mProgress;
    @BindView(R.id.loadingTv)
     TextView mLoadingTv;

    //标题
    @BindView(R.id.toolbar_iv)
    ImageView toolbar_iv;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.toolbar_more)
    ImageView toolbar_more;
    private String select_month;

    private List<Consume> mConsumes;
    private String mYear;
    private String mAccountNumber;
    private Handler mHandler;

    private final int GET_SUCCESED = 0X121;
    private final int GET_SUCCESED1 = 0x122;
    private final int GET_FAILED = 0X111;
    private String year_month;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_con_query);
        ButterKnife.bind(this);
        initToolBar();
        //mProgress.startAnim();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case GET_SUCCESED:
                        mLl_progress.setVisibility(View.GONE);
                        mRl_content.setVisibility(View.VISIBLE);
                        mIntent = new Intent(ConSelectActivity.this,ConsumptionActivity.class);
                        mIntent.putExtra("consumes",(Serializable)mConsumes);
                        startActivity(mIntent);
                        mProgress.stopAnim();
                        break;
                    case GET_SUCCESED1:
                        mLl_progress.setVisibility(View.GONE);
                        mRl_content.setVisibility(View.VISIBLE);
                        mProgress.stopAnim();
                        mIntent = new Intent(ConSelectActivity.this, ConsumptionActivity.class);
                        mIntent.putExtra("consumes",(Serializable)mConsumes);
                        startActivity(mIntent);
                        break;
                    default:
                        //数据不存在时 显示对话框重新请求数据
                        showDialog();
                        mLl_progress.setVisibility(View.GONE);
                        mRl_content.setVisibility(View.VISIBLE);
                        break;
                }
            }
        };
        intiData();


    }

    //初始化数据
    private void intiData() {
        Calendar c = Calendar.getInstance();
        List<String> months = new ArrayList<>();
        year_month = String.valueOf(c.get(Calendar.MONTH) + 1);
        int month = c.get(Calendar.MONTH) + 1;
        for (int i = 1; i <= month; i++) {
            months.add(i + "   月");
        }
        //获得年份
        mYear = String.valueOf(c.get(Calendar.YEAR));
        mTextView.setText(mYear);
        //获得月份
        mCon_term_sp.setAdapter(new ArrayAdapter<String>(ConSelectActivity.this,android.R.layout.simple_spinner_dropdown_item, months));

    }

    //监听事件
    @OnClick({R.id.con_btn_today, R.id.con_btn_month,R.id.con_btn_all})
    public void myButton(Button btn) {
        switch (btn.getId()){
            //历史账单查询 默认为本月
            case R.id.con_btn_all:
                mLl_progress.setVisibility(View.VISIBLE);
                mRl_content.setVisibility(View.GONE);
                mProgress.startAnim();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //请求数据
                        mConsumes = QueryDataDao.intiDa(UrlBean.ECARD_QUERY_URL,null,year_month);
                        //消费记录存在时调用
                        if (mConsumes!=null){
                            mHandler.sendEmptyMessage(GET_SUCCESED);
                            return;
                        }else {
                            mHandler.sendEmptyMessage(GET_FAILED);
                        }
                    }
                }).start();
                break;
            //月账单查询
            case R.id.con_btn_month:
                mLl_progress.setVisibility(View.VISIBLE);
                mRl_content.setVisibility(View.GONE);
                mProgress.startAnim();
                select_month = mCon_term_sp.getSelectedItem().toString().trim().substring(0,1);
                Log.i("月份2", "intiData: "+select_month);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //请求数据
                        mConsumes = QueryDataDao.intiDa(UrlBean.ECARD_QUERY_URL, null,select_month);
                        //消费记录存在时调用
                        Log.i("月份2", "intiData: "+select_month);
                        if (mConsumes!=null){
                            mHandler.sendEmptyMessage(GET_SUCCESED1);
                            return;
                        }else {
                            mHandler.sendEmptyMessage(GET_FAILED);
                        }
                    }
                }).start();
                break;
            case R.id.con_btn_today:
                mLl_progress.setVisibility(View.VISIBLE);
                mRl_content.setVisibility(View.GONE);
                mProgress.startAnim();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mConsumes = QueryDataDao.intiToady(UrlBean.POST_QUERY_URL_TODAY);
                        if (mConsumes!=null){
                            mHandler.sendEmptyMessage(GET_SUCCESED1);
                            return;
                        }else {
                            mHandler.sendEmptyMessage(GET_FAILED);
                        }
                    }
                }).start();
        }

    }

    //初始化标题栏
    private void initToolBar() {
        toolbar_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar_title.setText("分类查询");
        toolbar_more.setVisibility(View.GONE);

    }
    private void showDialog() {
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(ConSelectActivity.this);
        normalDialog.setIcon(R.drawable.emoji);
        normalDialog.setTitle("呜呜呜呜.");
        normalDialog.setMessage("本月没有消费。。。");
        normalDialog.setCancelable(false);
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        normalDialog.show().dismiss();
                    }
                });
        // 显示对话框
        normalDialog.show();

    }



}
