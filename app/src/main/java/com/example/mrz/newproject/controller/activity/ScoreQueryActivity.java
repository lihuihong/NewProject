package com.example.mrz.newproject.controller.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mrz.newproject.R;
import com.example.mrz.newproject.controller.adapter.ScoreRecyclerAdapter;
import com.example.mrz.newproject.model.bean.ScoreBean;
import com.example.mrz.newproject.model.bean.UrlBean;
import com.example.mrz.newproject.model.bean.User;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScoreQueryActivity extends AppCompatActivity {

    @BindView(R.id.score_rv)
    RecyclerView score_rv;

    //标题栏
    @BindView(R.id.toolbar_more)
    ImageView toolbar_more;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.toolbar_iv)
    ImageView toolbar_iv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_query);

        ButterKnife.bind(this);

        initView();

        initData();
    }

    private void initView() {

        //隐藏更多按钮
        toolbar_more.setVisibility(View.GONE);

        //设置标题
        toolbar_title.setText(getIntent().getStringExtra("title"));

        //添加事件 返回
        toolbar_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initData() {

        //获取传递过来的数据
        List<ScoreBean> scores = (ArrayList<ScoreBean>) getIntent().getSerializableExtra("scores");


        LinearLayoutManager manager = new LinearLayoutManager(this);

        score_rv.setLayoutManager(manager);

        //设置数据适配器
        ScoreRecyclerAdapter adapter = new ScoreRecyclerAdapter(this,scores);

        score_rv.setAdapter(adapter);
    }


}
