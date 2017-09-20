package com.example.mrz.newproject.controller.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mrz.newproject.R;
import com.example.mrz.newproject.model.bean.ScoreBean;
import com.example.mrz.newproject.model.bean.UrlBean;
import com.example.mrz.newproject.model.bean.User;
import com.example.mrz.newproject.model.dao.QueryScoreDao;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScoreSelectActivity extends AppCompatActivity {

    //年份选择
    @BindView(R.id.score_year_sp)
    Spinner score_year_sp;

    //学期选择
    @BindView(R.id.score_term_sp)
    Spinner score_term_sp;

    //学期选择
    @BindView(R.id.score_btn_year)
    Button score_btn_year;

    //学期选择
    @BindView(R.id.score_btn_terms)
    Button score_btn_terms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_select);

        ButterKnife.bind(this);
        getData();
    }

    //监听事件
    @OnClick({R.id.score_btn_year, R.id.score_btn_terms,R.id.score_btn_all})
    public void myButton(TextView btn) {
        switch (btn.getId()) {


            //按年查询
            case R.id.score_btn_year:

                break;
            //按学期查询
            case R.id.score_btn_terms:

                break;
            //历年查询
            case R.id.score_btn_all:

                Message msg = new Message();
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            List<ScoreBean> scores = QueryScoreDao.getScorePostAll();

                            Intent intent =new Intent(ScoreSelectActivity.this,ScoreQueryActivity.class);
                            intent.putExtra("scores",(Serializable)scores);

                            startActivity(intent);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
        }
    }

    private void data(){

    }

    private void getData(){
        new Thread(){
            @Override
            public void run() {

                //拼接成绩地址
                String url = null;
                try {
                    url = UrlBean.IP + "/" + UrlBean.sessionId + "/" + UrlBean.scoreUrl + "?xh=" + User.xh + "&xm=" + URLEncoder.encode(User.xm,"GBK") + "&gnmkdm=" + UrlBean.scoreCode;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                //获取数据
                try {

                    QueryScoreDao.getScorePostData(url);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
