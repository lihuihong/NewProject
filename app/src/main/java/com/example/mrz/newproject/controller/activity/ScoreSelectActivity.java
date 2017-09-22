package com.example.mrz.newproject.controller.activity;

import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mrz.newproject.R;
import com.example.mrz.newproject.model.bean.ScoreBean;
import com.example.mrz.newproject.model.bean.UrlBean;
import com.example.mrz.newproject.model.bean.User;
import com.example.mrz.newproject.model.dao.QueryScoreDao;
import com.example.mrz.newproject.view.animation.CircleProgress;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
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

    //学年查询
    @BindView(R.id.score_btn_year)
    Button score_btn_year;

    //学期查询
    @BindView(R.id.score_btn_terms)
    Button score_btn_terms;


    @BindView(R.id.ll_progress)
    LinearLayout ll_progress;
    @BindView(R.id.progress)
    CircleProgress progress;

    @BindView(R.id.rl_content)
    RelativeLayout rl_content;

    private Handler mHandler;

    private final int GET_SCORE_SUCCED = 0X111111;


    //标题
    @BindView(R.id.toolbar_iv)
    ImageView toolbar_iv;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.toolbar_more)
    ImageView toolbar_more;

    //所有分数
    private List<ScoreBean> scores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_select);

        ButterKnife.bind(this);

        progress.startAnim();

        initToolBar();

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {

                switch (msg.what){
                    case GET_SCORE_SUCCED:

                        //获取年份
                        ArrayList<String> years = new ArrayList<>(QueryScoreDao.years);

                        //设置年筛选项
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ScoreSelectActivity.this, android.R.layout.simple_spinner_item,years);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        score_year_sp.setAdapter(adapter);

                        progress.stopAnim();
                        ll_progress.setVisibility(View.GONE);
                        rl_content.setVisibility(View.VISIBLE);

                        break;
                }
            }
        };

        getData();
    }

    //初始化标题栏
    private void initToolBar() {

        toolbar_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 finish();
            }
        });

        toolbar_title.setText("学年选择");

        toolbar_more.setVisibility(View.GONE);

    }


    //监听事件
    @OnClick({R.id.score_btn_year, R.id.score_btn_terms,R.id.score_btn_all})
    public void myButton(TextView btn) {

        switch (btn.getId()) {


            //按年查询
            case R.id.score_btn_year:

                List<ScoreBean> yearScores = QueryScoreDao.getScoreYears(scores,score_year_sp.getSelectedItem().toString());

                Intent yearIntent =new Intent(ScoreSelectActivity.this,ScoreQueryActivity.class);
                yearIntent.putExtra("scores",(Serializable)yearScores);
                yearIntent.putExtra("title",score_year_sp.getSelectedItem().toString()+"学年成绩");

                startActivity(yearIntent);

                break;

            //按学期查询
            case R.id.score_btn_terms:

                List<ScoreBean> termScores = null;
                yearScores = QueryScoreDao.getScoreTerms(scores,score_year_sp.getSelectedItem().toString(),score_term_sp.getSelectedItem().toString());

                Intent termIntent =new Intent(ScoreSelectActivity.this,ScoreQueryActivity.class);
                termIntent.putExtra("scores",(Serializable)yearScores);
                termIntent.putExtra("title",score_year_sp.getSelectedItem().toString()+"年第"+score_term_sp.getSelectedItem().toString()+"学期成绩");

                startActivity(termIntent);

                break;
            //历年查询
            case R.id.score_btn_all:

                Intent allIntent =new Intent(ScoreSelectActivity.this,ScoreQueryActivity.class);
                allIntent.putExtra("scores",(Serializable)scores);
                allIntent.putExtra("title","历年成绩");

                startActivity(allIntent);

                break;
        }
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

                Message msg = new Message();
                //获取数据
                try {

                    //第一次访问获取隐藏表单数据
                    QueryScoreDao.getScorePostData(url);

                    //获取历年成绩
                    scores = QueryScoreDao.getScorePostAll();
                    mHandler.sendEmptyMessage(GET_SCORE_SUCCED);

                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }.start();
    }

}
