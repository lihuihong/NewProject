package com.example.mrz.newproject.controller.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mrz.newproject.R;
import com.example.mrz.newproject.model.bean.Consume;
import com.example.mrz.newproject.model.bean.UrlBean;
import com.example.mrz.newproject.uitls.OkHttpUitl;
import com.example.mrz.newproject.view.animation.MyProgressDialog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
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
    private List<Consume> mConsumes;
    private Handler mHandler;
    private String mMonth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        ButterKnife.bind(this);
        intiDa();
        //初始化数据
        intiData();

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0x121:
                        ll_balance1.setVisibility(View.VISIBLE);
                        //Log.i("数据", "run getQueryhistory: " + mConsumes);
                        getQueryhistory();
                        dismiss();
                        break;
                    case 0x122:
                        ll_balance.setVisibility(View.VISIBLE);
                        String money = (String) msg.obj;
                        tv_balance.setText(money);
                        break;

                    case 0x123:
                        ll_balance.setVisibility(View.VISIBLE);
                        tv_error.setText(mMonth + "月暂无消费信息");
                        ll_balance1.setVisibility(View.GONE);
                        tv_error.setVisibility(View.VISIBLE);
                        dismiss();
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
                        //Log.d("record", "record执行成功");
                        Document doc = Jsoup.parse(data, "GBK");
                        mAccountNumber = doc.getElementById("lblOne0").text();
                        m.obj = mAccountNumber;
                        m.what = 0x122;
                        mHandler.sendMessage(m);
                    } else {
                        //Log.i("请求失败", "code: " + rsp.code());
                        //Log.i("请求失败", "run: " + "失败");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
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

    public void intiDa() {
        Calendar a = Calendar.getInstance();
        final String year = String.valueOf(a.get(Calendar.YEAR));
        mMonth = String.valueOf(a.get(Calendar.MONTH) + 1);
        mConsumes = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request res = new Request.Builder().url(UrlBean.ECARD_QUERY_URL).get().build();
                try {
                    Response rsp = OkHttpUitl.getInstance().newCall(res).execute();
                    if (rsp.isSuccessful()) {
                        Document doc = Jsoup.parse(rsp.body().string(), "GBK");
                        String viewstate = doc.select("input[name=\"__VIEWSTATE\"]").first().attr("value");
                        Log.d("__VIEWSTATE", "__VIEWSTATE" + viewstate);
                        RequestBody requestBody = new FormBody.Builder()
                                .add("__VIEWSTATE", viewstate)
                                .add("ddlYear", year)
                                .add("ddlMonth", mMonth)
                                .add("txtMonth", mMonth)
                                .add("ImageButton1.x", "22")
                                .add("ImageButton1.y", "16").build();
                        res = new Request.Builder().url(UrlBean.ECARD_QUERY_URL).post(requestBody).build();
                        rsp = OkHttpUitl.getInstance().newCall(res).execute();
                        if (rsp.code() == 302) {
                            String data = rsp.body().string();
                            if (data.contains("QueryhistoryDetail")) {
                                res = new Request.Builder().url(UrlBean.POST_QUERY_URL).get().build();
                                rsp = OkHttpUitl.getInstance().newCall(res).execute();
                                data = rsp.body().string();
                                doc = Jsoup.parse(data, "GBK");
                                Log.d("doc", data);
                                //解析数据
                                Elements trs;
                                try {
                                    trs = doc.getElementById("dgShow").select("tr");
                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                    Message msg = new Message();
                                    msg.what = 0x123;
                                    mHandler.sendEmptyMessage(msg.what);
                                    return;
                                }
                                for (int i = trs.size() - 1; i > 0; i--) {
                                    Elements tds = trs.get(i).select("td");
                                    Consume cs = new Consume();
                                    //cs.setType(tds.get(3).text());
                                    cs.setAddress(tds.get(4).text());
                                    //Log.i("地点", "run: "+ tds.get(4).text());
                                    String priceNum = tds.get(7).text();
                                    try {
                                        priceNum = Long.parseLong(priceNum) > 0 ? "+" + priceNum : priceNum;

                                    } catch (NumberFormatException e) {
                                        e.printStackTrace();
                                    }
                                    cs.setPrice(priceNum);
                                    //Log.i("价钱", "run: "+ priceNum);
                                    cs.setDate(tds.get(8).text());
                                    //Log.i("时间", "run: "+ tds.get(8).text());
                                    //cs.setBalance(tds.get(10).text());
                                    mConsumes.add(cs);
                                    //Log.i("queryhistory", "run: " + mConsumes.get(0).getAddress());
                                }
                                Message message = new Message();
                                message.what = 0x121;
                                mHandler.sendEmptyMessage(message.what);
                            }

                        } else {
                            //Log.i("请求失败 queryhistory", "run: " + "失败");
                        }

                    } else {
                        //dismiss();
                        //showDialog();
                        //Log.i("请求失败 queryhistory1", "run: " + rsp.code());
                        //Log.i("请求失败 queryhistory1", "run: " + "失败");
                    }
                } catch (SocketTimeoutException e) {
                    //dismiss();
                    //showDialog();
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

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
                        intiDa();
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
