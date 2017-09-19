package com.example.mrz.newproject.controller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mrz.newproject.R;
import com.example.mrz.newproject.controller.activity.BalanceActivity;
import com.example.mrz.newproject.controller.activity.ConsumptionActivity;
import com.example.mrz.newproject.controller.activity.ElectiveActivity;
import com.example.mrz.newproject.controller.activity.EvaluationActivity;
import com.example.mrz.newproject.controller.activity.LossActivity;
import com.example.mrz.newproject.controller.activity.ResultsActivity;
import com.example.mrz.newproject.model.bean.UrlBean;
import com.example.mrz.newproject.model.bean.User;
import com.example.mrz.newproject.model.dao.GSUserInfoDao;
import com.example.mrz.newproject.uitls.OkHttpUitl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.mrz.newproject.R.id.tv_balance;

/**
 * Created by 那个谁 on 2017/9/10.
 * 奥特曼打小怪兽
 * 作用：选课
 */

public class StudentFragment extends Fragment {

    //余额查询
    @BindView(tv_balance)
    TextView mTv_balance;
    //消费情况查询
    @BindView(R.id.tv_consumption)
    TextView mTv_consumption;
    //一键挂失
    @BindView(R.id.tv_loss)
    TextView mTv_loss;
    //网上选课
    @BindView(R.id.tv_elective)
    TextView mTv_elective;
    //学生成绩查询
    @BindView(R.id.tv_results)
    TextView mTv_results;
    //教学质量一键评价
    @BindView(R.id.tv_evaluation)
    TextView mTv_evaluation;
    //toolbar
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    View view;
    private Intent mIntent;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    Map<String, String> postDatas = new HashMap<>(); //post提交数据

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_student, null);
            ButterKnife.bind(this, view);
//            ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
//            setHasOptionsMenu(true);
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (User.getId() == null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //个人信息url地址
                    String getUserInfoUrl = UrlBean.IP + "/" + UrlBean.sessionId + "/" + UrlBean.userInfoUrl + "?xh=" + User.xh + "&xm=" + User.xm + "&gnmkdm=" + UrlBean.userInfoCode;
                    try {
                        GSUserInfoDao.getbasicInfo(GSUserInfoDao.getAllUserInfo(getUserInfoUrl));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //登录一卡通
                    login();
                    //获取验证码
                    getcode();
                }
            }).start();
        }


        toolbar_title.setText("功能");
        super.onActivityCreated(savedInstanceState);
    }

    private void login() {
        postDatas.put("UserLogin:txtUser", User.getXh());
        Log.i("用户", "login: " + User.getXh());
        postDatas.put("UserLogin:txtPwd", User.getId());
        Log.i("密码", "login: " + User.getId());

        postDatas.put("__EVENTTARGET", "");
        postDatas.put("__LASTFOCUS", "");
        postDatas.put("__EVENTARGUMENT", "");
        postDatas.put("UserLogin:ImageButton1.x", "27");
        postDatas.put("UserLogin:ImageButton1.y", "5");
        try {
            postDatas.put("UserLogin:ddlPerson", URLEncoder.encode("卡户", "GBK"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String parma = "";

        for (Iterator<Map.Entry<String, String>> it = postDatas.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry entry = it.next();
            parma += entry.getKey() + "=" + entry.getValue();
            if (it.hasNext())
                parma += "&";
        }
        final String replace = parma.replace(":", "%3a").replace("+", "%2b");

        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=gb2312"), new String(replace.getBytes(), "GBK"));
            Request request = new Request.Builder().url(UrlBean.ECARDURL).removeHeader("User-Agent")
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36")
                    .post(requestBody).build();

            Response rsp = OkHttpUitl.getInstance().newCall(request).execute();
            String data = rsp.body().string();

            if (rsp.code() == 302 && data != null) {

                Log.i("返回码", "login: "+rsp.code() + data);
                intiData();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void intiData() {
        new Thread(){
            @Override
            public void run() {
                Request res = new Request.Builder().url("http://ecard.cqcet.edu.cn/default.aspx").get().build();
                try {
                    Response rsp = OkHttpUitl.getInstance().newCall(res).execute();
                    if(rsp.isSuccessful()){
                        String data = rsp.body().string();
                        Log.d("record","record执行成功" +data);

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    private void getcode() {
        Request request = new Request.Builder().url(UrlBean.ECARDURL).get().build();
        try {
            Response rsp = OkHttpUitl.getInstance().newCall(request).execute();
            if (rsp.isSuccessful()) {
                String data = rsp.body().string();
                Document document = Jsoup.parse(data);
                Element viewstate = document.select("input[name=\"__VIEWSTATE\"]").first();
                Element eventvalidation = document.select("input[name=\"__EVENTVALIDATION\"]").first();
                String UserLogin_ImgFirst = (String) document.getElementById("UserLogin_ImgFirst").attr("src").subSequence(7,8);
                String UserLogin_imgSecond = (String) document.getElementById("UserLogin_imgSecond").attr("src").subSequence(7,8);
                String UserLogin_imgThird = (String) document.getElementById("UserLogin_imgThird").attr("src").subSequence(7,8);
                String UserLogin_imgFour = (String) document.getElementById("UserLogin_imgFour").attr("src").subSequence(7,8);
                String num = UserLogin_ImgFirst + UserLogin_imgSecond + UserLogin_imgThird + UserLogin_imgFour;
                postDatas.put("__VIEWSTATE", viewstate.attr("value"));
                postDatas.put("__EVENTVALIDATION", eventvalidation.attr("value"));
                postDatas.put("UserLogin:txtSure",num);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //监听事件
    @OnClick({tv_balance, R.id.tv_consumption, R.id.tv_loss, R.id.tv_elective,
            R.id.tv_results, R.id.tv_evaluation})
    public void myButton(TextView btn) {
        switch (btn.getId()) {
            //余额查询
            case tv_balance:
                mIntent = new Intent(getActivity(), BalanceActivity.class);
                startActivity(mIntent);
                break;
            //消费情况查询
            case R.id.tv_consumption:
                mIntent = new Intent(getActivity(), ConsumptionActivity.class);
                startActivity(mIntent);
                break;
            //一键挂失
            case R.id.tv_loss:
                mIntent = new Intent(getActivity(), LossActivity.class);
                startActivity(mIntent);
                break;
            //学生成绩查询
            case R.id.tv_results:
                mIntent = new Intent(getActivity(), ResultsActivity.class);
                startActivity(mIntent);
                break;
            //教学质量一键评价
            case R.id.tv_evaluation:
                mIntent = new Intent(getActivity(), EvaluationActivity.class);
                startActivity(mIntent);
                break;
            //网上选课
            case R.id.tv_elective:
                mIntent = new Intent(getActivity(), ElectiveActivity.class);
                startActivity(mIntent);
                break;
        }

    }


}
