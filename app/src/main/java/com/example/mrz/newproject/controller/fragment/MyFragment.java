package com.example.mrz.newproject.controller.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrz.newproject.R;
import com.example.mrz.newproject.controller.activity.DocumentActivity;
import com.example.mrz.newproject.controller.activity.OpinionActivity;
import com.example.mrz.newproject.controller.activity.QqActivity;
import com.example.mrz.newproject.controller.activity.SettingActivity;
import com.example.mrz.newproject.controller.activity.UserInfoActivity;
import com.example.mrz.newproject.model.bean.UrlBean;
import com.example.mrz.newproject.model.bean.User;
import com.example.mrz.newproject.model.bean.UserInfoKVP;
import com.example.mrz.newproject.model.dao.GSUserInfoDao;
import com.example.mrz.newproject.model.db.MySqlHelper;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 那个谁 on 2017/9/10.
 * 奥特曼打小怪兽
 * 作用：我
 */

public class MyFragment extends Fragment {

    //详细信息
    @BindView(R.id.ll_avatar)
    LinearLayout mLl_avatar;
    //姓名
    @BindView(R.id.my_name)
    TextView mMy_name;
    //学号
    @BindView(R.id.my_xh)
    TextView mMy_xh;
    //意见提交
    @BindView(R.id.ll_opinion)
    LinearLayout mLl_opinion;
    //加入QQ群
    @BindView(R.id.ll_qq)
    LinearLayout mLl_qq;
    //免费声明
    @BindView(R.id.ll_document)
    LinearLayout mLl_document;
    //软件设置
    @BindView(R.id.ll_setting)
    LinearLayout mLl_setting;

    @BindView(R.id.userinfo_img)
    ImageView userinfo_img;

    View view;

    private Intent mIntent;

    private Handler mHandler;

    private Context context;


    //获取头像成功
    private final int GET_INFO_IMG_SUCCED = 0X111111;

    //获取头像失败
    private final int GET_INFO_IMG_FAILED = 0X111110;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null){
            view = inflater.inflate(R.layout.fragment_my,null);
            ButterKnife.bind(this,view);
            context = getContext();
            mHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what){
                        case GET_INFO_IMG_SUCCED:
                            userinfo_img.setImageBitmap((Bitmap) msg.obj);
                            break;
                        case GET_INFO_IMG_FAILED:
                            break;
                    }
                }
            };
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        //获取本地储存,查看是否有用户登录
        SharedPreferences pref = context.getSharedPreferences("userInfoData", context.MODE_PRIVATE);
        String name = pref.getString("xhxm","");
        String xh = pref.getString("userName","");
        //姓名
        mMy_name.setText(name);
        //学号
        mMy_xh.setText(xh);

        if(GSUserInfoDao.isHaveInfo(getContext())){

            try {
                Bitmap headImg = GSUserInfoDao.getUserInfoImg();
                userinfo_img.setImageBitmap(headImg);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{

            new Thread(){
                @Override
                public void run() {

                    //个人信息url地址
                    String getUserInfoUrl = UrlBean.IP + "/" + UrlBean.sessionId + "/" + UrlBean.userInfoUrl + "?xh=" + User.xh + "&xm=" + User.xm + "&gnmkdm=" + UrlBean.userInfoCode;
                    Message msg = new Message();

                    try {
                        //获取全部个人信息
                        GSUserInfoDao.getAllUserInfo(getUserInfoUrl);

                        msg.what = GET_INFO_IMG_SUCCED;
                        //获取到的图片
                        msg.obj = GSUserInfoDao.getUserInfoImg();

                        mHandler.sendMessage(msg);

                    } catch (IOException e) {
                        //e.printStackTrace();
                        msg.what = GET_INFO_IMG_FAILED;
                        mHandler.sendMessage(msg);
                    }
                }
            }.start();
        }
        super.onActivityCreated(savedInstanceState);
    }

    @OnClick({R.id.ll_avatar,R.id.ll_opinion,R.id.ll_qq,R.id.ll_document,R.id.ll_setting})
    public void myOnClick(LinearLayout ll){
        switch (ll.getId()){
            //详细信息
            case R.id.ll_avatar:

                mIntent = new Intent(getActivity(), UserInfoActivity.class);

                //基本信息
                List<UserInfoKVP> basicInfos = GSUserInfoDao.getInfos("1","5");
                mIntent.putExtra("basicInfos", (Serializable) basicInfos);
                //联系信息
                List<UserInfoKVP> connInfos = GSUserInfoDao.getInfos("6","8");
                mIntent.putExtra("connInfos", (Serializable) connInfos);

                //学校信息
                List<UserInfoKVP> schoolInfos = GSUserInfoDao.getInfos("9","14");
                mIntent.putExtra("schoolInfos", (Serializable) schoolInfos);

                startActivity(mIntent);
                break;
            //意见提交
            case R.id.ll_opinion:
                mIntent = new Intent(getActivity(), OpinionActivity.class);
                startActivity(mIntent);
                break;
            //加入QQ群
            case R.id.ll_qq:
                mIntent = new Intent(getActivity(), QqActivity.class);
                startActivity(mIntent);
                break;
            //免费声明
            case R.id.ll_document:
                mIntent = new Intent(getActivity(), DocumentActivity.class);
                startActivity(mIntent);
                break;
            //软件设置
            case R.id.ll_setting:
                mIntent = new Intent(getActivity(), SettingActivity.class);
                startActivity(mIntent);
                break;
        }
    }


}
