package com.example.mrz.newproject.controller.fragment;

import android.content.Context;
import android.content.pm.ProviderInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mrz.newproject.R;
import com.example.mrz.newproject.controller.activity.ElectiveActivity;
import com.example.mrz.newproject.controller.adapter.ElectiveRecyclerAdapter;
import com.example.mrz.newproject.model.bean.Class;
import com.example.mrz.newproject.model.dao.SelectClassDao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr.z on 2017/9/12.
 */

public class ElectiveFragment extends Fragment {

    private Context context;
    private String url;

    private View view;

    private final int GET_SELECT_SUCCESED = 0X11111;
    private final int GET_SELECT_FAILED = 0X11110;

    private Context context;  
    private String url;  
  
    //Fragment新要求这样些
    public static ElectiveFragment newInstance(String url) {  
        ElectiveFragment newFragment = new ElectiveFragment();  
        Bundle bundle = new Bundle();  
        bundle.putString("url", url);  
        newFragment.setArguments(bundle);  
        return newFragment;  
    }  

    //有可选课时显示的条目
    @BindView(R.id.elective_rv_class)
    RecyclerView rv_classes;

    //无可选课程时显示的内容
    @BindView(R.id.elective_class_text)
    TextView class_text;

    private Handler mHandler;

    //有余量的课
    private List<Class> classes;

    private ElectiveRecyclerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.fragment_elective,container,false);
            ButterKnife.bind(this, view);
            this.context = getActivity();
            Bundle args = getArguments();  
            if (args != null) {  
                this.url = args.getString("url");  
            }  
        }


        //get_classes();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case GET_SELECT_SUCCESED:
                        //获取成功就设置数据适配器
                        adapter = new ElectiveRecyclerAdapter(context,classes);
                        rv_classes.setAdapter(adapter);
                        break;
                    default:
                        //为空就显示没有课text
                        rv_classes.setVisibility(View.GONE);
                        class_text.setVisibility(View.VISIBLE);
                }
            }
        };

        //设置垂直布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(context);
        rv_classes.setLayoutManager(manager);
        //设置数据适配器
        //classes = new ArrayList<>();

        new Thread(){
            @Override
            public void run() {

                try {
                    //获取课表
                    classes = SelectClassDao.getAllClass(url);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(classes != null){
                    //如果不为空说明正常获取到课程
                    mHandler.sendEmptyMessage(GET_SELECT_SUCCESED);

                }else{
                    //为空则说明没有获取课程
                    mHandler.sendEmptyMessage(GET_SELECT_FAILED);
                }


            }
        }.start();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
