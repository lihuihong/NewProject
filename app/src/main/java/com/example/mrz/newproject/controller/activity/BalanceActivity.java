package com.example.mrz.newproject.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.mrz.newproject.R;
import com.example.mrz.newproject.model.bean.UrlBean;
import com.example.mrz.newproject.uitls.OkHttpUitl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Request;
import okhttp3.Response;

//余额查询页面
public class BalanceActivity extends AppCompatActivity{
    @BindView(R.id.tv_balance)
    TextView tv_balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        ButterKnife.bind(this);
        //初始化数据
        intiData();
    }

    private void intiData() {
        new Thread(){
            @Override
            public void run() {
                Request res = new Request.Builder().url(UrlBean.CARDHOLDER_URL).get().build();
                try {
                    Response rsp = OkHttpUitl.getInstance().newCall(res).execute();
                    if(rsp.isSuccessful()){
                        String data = rsp.body().string();
                        Log.d("record","record执行成功");
                        Document doc = Jsoup.parse(data, "GBK");

                        String accountNumber = doc.getElementById("lblOne0").text();
                        tv_balance.setText(accountNumber);
                        Log.i("余额", "run: "+accountNumber);
                    }else {
                        Log.i("请求失败", "run: "+"失败");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }


}
