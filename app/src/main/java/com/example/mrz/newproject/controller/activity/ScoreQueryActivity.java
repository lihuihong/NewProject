package com.example.mrz.newproject.controller.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mrz.newproject.R;
import com.example.mrz.newproject.model.bean.UrlBean;
import com.example.mrz.newproject.model.bean.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScoreQueryActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_score_query);

        ButterKnife.bind(this);

    }





    //监听事件
    @OnClick({R.id.score_btn_year, R.id.score_btn_terms })
    public void myButton(TextView btn) {
        switch (btn.getId()) {

            //按年查询
            case R.id.score_btn_year:

                break;
            //按学期查询
            case R.id.score_btn_terms:

                break;
        }
    }

    private void getData(){
        new Thread(){
            @Override
            public void run() {

                //拼接成绩地址
                String url = UrlBean.IP + "/" + UrlBean.sessionId + "/" + UrlBean.scoreUrl + "?xh=" + User.xh + "&xm=" + User.xm + "&gnmkdm=" + UrlBean.scoreCode;


            }
        }.start();
    }


}
