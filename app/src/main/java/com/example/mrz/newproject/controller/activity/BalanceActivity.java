package com.example.mrz.newproject.controller.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrz.newproject.R;
import com.example.mrz.newproject.model.bean.Consume;
import com.example.mrz.newproject.model.bean.UrlBean;
import com.example.mrz.newproject.model.dao.QueryDataDao;
import com.example.mrz.newproject.uitls.OkHttpUitl;
import com.example.mrz.newproject.view.animation.MyProgressDialog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Request;
import okhttp3.Response;

//余额查询页面
public class BalanceActivity extends AppCompatActivity {
    @BindView(R.id.tv_balance)
    TextView tv_balance;

    private String mAccountNumber;

    @BindView(R.id.rl_progress)
    RelativeLayout mRl_progress;

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

    private MyProgressDialog mDialog;
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
        mConsumes = new QueryDataDao().intiDa(UrlBean.ECARD_QUERY_URL);
        //初始化数据
        intiData();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case GET_SUCCESED:
                        ll_balance1.setVisibility(View.VISIBLE);
                        Log.i("数据", "run getQueryhistory: " + mConsumes);
                        getQueryhistory();
                        dismiss();
                        break;
                    case GET_SUCCESED1:
                        ll_balance.setVisibility(View.VISIBLE);
                        String money = (String) msg.obj;
                        tv_balance.setText(money);
                        break;
                    case 0x123:
                        ll_balance.setVisibility(View.VISIBLE);
                        ll_balance1.setVisibility(View.GONE);
                        mMonth = String.valueOf(a.get(Calendar.MONTH) + 1);
                        tv_error.setText(mMonth + "月暂无消费信息");
                        tv_error.setVisibility(View.VISIBLE);
                        dismiss();
                        break;
                    default:
                        Toast.makeText(BalanceActivity.this, "网络请求超时，请重试", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
    }

    private void intiData() {
        mDialog = new MyProgressDialog(BalanceActivity.this);
        showMyDialog();
        new Thread() {
            @Override
            public void run() {
                Message m = new Message();
                Request res = new Request.Builder().url(UrlBean.CARDHOLDER_URL).get().build();
                try {
                    Response rsp = OkHttpUitl.getInstance().newCall(res).execute();
                    if (rsp.isSuccessful()) {
                        String data = rsp.body().string();
                        Document doc = Jsoup.parse(data, "GBK");
                        mAccountNumber = doc.getElementById("lblOne0").text();
                        m.obj = mAccountNumber;
                        m.what = 0x122;
                        mHandler.sendMessage(m);
                    } else {
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                SystemClock.sleep(12000);
                if (mConsumes.size()!=0){
                    mHandler.sendEmptyMessage(GET_SUCCESED);
                }else {
                    mHandler.sendEmptyMessage(GET_FAILED);
                    mHandler.sendEmptyMessage(0x123);
                }
            }
        }.start();
    }

    private void showMyDialog() {
        mDialog.getWindow().setBackgroundDrawableResource(android.R.color.white);
        mDialog.show();
        Window win = mDialog.getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        win.setAttributes(lp);
        //用户选择退出（按返回键）时ProgressDialog和对应的下面的Activity都退出。
        mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                mRl_progress.setVisibility(View.INVISIBLE);
                finish();
            }
        });
    }

    private void dismiss() {
        ll_balance.setVisibility(View.VISIBLE);
        mRl_progress.setVisibility(View.INVISIBLE);
        mDialog.dismiss();
    }

    private void getQueryhistory() {
        for (int i = 0; i < mConsumes.size(); i++) {
            //Log.i("数据", "run getQueryhistory: " + mConsumes.get(i).getAddress());
            if (i == 0) {
                Consume cos = mConsumes.get(i);
                mTv_morning.setText(cos.getDate());
                mTv_moneyone.setText(cos.getPrice());
                mTv_placeone.setText(cos.getAddress());
                //Log.i("时间", "run getQueryhistory: " + cos.getDate());
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

    private void showDialog() {
        AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(BalanceActivity.this);
        normalDialog.setIcon(R.drawable.emoji);
        normalDialog.setTitle("呜呜呜呜.");
        normalDialog.setMessage("网络开小差了。。。");
        normalDialog.setPositiveButton("重试",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //intiDa();
                        intiData();
                        showMyDialog();
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        // 显示
        normalDialog.show();
    }



}
