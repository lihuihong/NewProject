package com.example.mrz.newproject.controller.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mrz.newproject.R;
import com.example.mrz.newproject.model.bean.Consume;
import com.example.mrz.newproject.model.bean.UrlBean;
import com.example.mrz.newproject.model.dao.QueryDataDao;
import com.example.mrz.newproject.view.animation.CircleProgress;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//余额查询页面
public class BalanceActivity extends AppCompatActivity {
    @BindView(R.id.tv_balance)
    TextView tv_balance;

    private String mAccountNumber;

    @BindView(R.id.rl_progress)
    RelativeLayout rl_progress;

    @BindView(R.id.progress)
    CircleProgress progress;

    @BindView(R.id.ll_balance)
    LinearLayout ll_balance;

    @BindView(R.id.tv_morning)
    TextView mTv_morning;

    @BindView(R.id.tv_placeone)
    TextView mTv_placeone;

    @BindView(R.id.tv_moneyone)
    TextView mTv_moneyone;

    @BindView(R.id.tv_noon)
    TextView mTv_noon;

    @BindView(R.id.tv_placetwo)
    TextView mTv_placetwo;

    @BindView(R.id.tv_moneytwo)
    TextView mTv_moneytwo;

    @BindView(R.id.tv_night)
    TextView mTv_night;

    @BindView(R.id.tv_placethree)
    TextView mTv_placethree;

    @BindView(R.id.tv_moneythree)
    TextView mTv_moneythree;

    @BindView(R.id.tv_night1)
    TextView mTv_night1;

    @BindView(R.id.tv_placethree1)
    TextView mTv_placethree1;

    @BindView(R.id.tv_moneythree1)
    TextView mTv_moneythree1;

    @BindView(R.id.ll_balance1)
    LinearLayout ll_balance1;

    @BindView(R.id.tv_error)
    TextView tv_error;

    //标题
    @BindView(R.id.toolbar_iv)
    ImageView toolbar_iv;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.toolbar_more)
    ImageView toolbar_more;

    private Handler mHandler;
    private String mMonth;
    private List<Consume> mConsumes;
    private final int GET_SUCCESED = 0X121;
    private final int GET_SUCCESED1 = 0x122;
    private final int GET_FAILED = 0X111;

    Calendar a = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        ButterKnife.bind(this);
        //初始化toobar
        initToolBar();
        progress.startAnim();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case GET_SUCCESED:
                        //隐藏动画
                        rl_progress.setVisibility(View.GONE);
                        //显示余额及消费记录界面
                        ll_balance.setVisibility(View.VISIBLE);
                        ll_balance1.setVisibility(View.VISIBLE);
                        tv_balance.setText(mAccountNumber);
                        tv_error.setVisibility(View.GONE);
                        //调用消费记录条目并设置数据
                        getQueryhistory();
                        //停止加载动画
                        progress.stopAnim();
                        break;
                    case GET_SUCCESED1:
                        rl_progress.setVisibility(View.GONE);
                        ll_balance.setVisibility(View.VISIBLE);
                        ll_balance1.setVisibility(View.GONE);
                        mMonth = String.valueOf(a.get(Calendar.MONTH) + 1);
                        tv_error.setText(mMonth + "月暂无消费信息");
                        tv_error.setVisibility(View.VISIBLE);
                        tv_balance.setText(mAccountNumber);
                        //停止加载动画
                        progress.stopAnim();
                        break;
                    default:
                        //数据不存在时 显示对话框重新请求数据
                        showDialog();
                        break;
                }
            }
        };
        //初始化数据
        intiData();
    }

    //初始化数据
    private void intiData() {
        final String mMonth = String.valueOf(a.get(Calendar.MONTH) + 1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                //请求数据
                mConsumes = QueryDataDao.intiDa(UrlBean.ECARD_QUERY_URL, UrlBean.CARDHOLDER_URL, mMonth);
                //设置余额数据
                mAccountNumber = mConsumes.get(mConsumes.size() - 1).getAccountNumber();
                //消费记录存在时调用
                if (mConsumes.size() > 1 && mAccountNumber != null) {
                    mHandler.sendEmptyMessage(GET_SUCCESED);
                    return;
                } else if (mAccountNumber != null) {
                    mHandler.sendEmptyMessage(GET_SUCCESED1);
                    return;
                } else {
                    mHandler.sendEmptyMessage(GET_FAILED);
                }

            }
        }).start();

    }

    //设置消费记录数据
    private void getQueryhistory() {
        for (int i = 0; i < mConsumes.size(); i++) {
            if (i == 0) {
                Consume cos = mConsumes.get(i);
                mTv_morning.setText(cos.getDate());
                mTv_moneyone.setText(cos.getPrice());
                mTv_placeone.setText(cos.getAddress());
            } else if (i == 1) {
                Consume cos = mConsumes.get(i);
                mTv_noon.setText(cos.getDate());
                mTv_moneytwo.setText(cos.getPrice());
                mTv_placetwo.setText(cos.getAddress());
            } else if (i == 2) {
                Consume cos = mConsumes.get(i);
                mTv_night.setText(cos.getDate());
                mTv_moneythree.setText(cos.getPrice());
                mTv_placethree.setText(cos.getAddress());
            } else if (i == 3) {
                Consume cos = mConsumes.get(i);
                mTv_night1.setText(cos.getDate());
                mTv_moneythree1.setText(cos.getPrice());
                mTv_placethree1.setText(cos.getAddress());
            }

        }
    }

    //网络超时显示对话框
    private void showDialog() {
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(BalanceActivity.this);
        normalDialog.setIcon(R.drawable.emoji);
        normalDialog.setTitle("呜呜呜呜");
        normalDialog.setMessage("网络开小差了。。。");
        normalDialog.setCancelable(false);
        normalDialog.setPositiveButton("重试",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //重新请求数据
                        intiData();
                        normalDialog.show().dismiss();
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        // 显示对话框
        normalDialog.show();

    }

    //初始化标题栏
    private void initToolBar() {
        toolbar_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar_title.setText("余额查询");
        toolbar_more.setVisibility(View.GONE);

    }


}
