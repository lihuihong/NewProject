package com.example.mrz.newproject.model.dao;

import android.util.Log;

import com.example.mrz.newproject.model.bean.ScoreBean;
import com.example.mrz.newproject.model.bean.UrlBean;
import com.example.mrz.newproject.model.bean.User;
import com.example.mrz.newproject.uitls.OkHttpUitl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Mr.z on 2017/9/19.
 */

public class QueryScoreDao {

    private static Map<String,String> postDatas;

    private static String url;

    //获取请求数据
    public static void getScorePostData(String url) throws IOException {

        //拼接首页地址
        String main_url = UrlBean.IP + "/" + UrlBean.sessionId + "/" + UrlBean.mainUrl + "?xh=" + User.xh;

        QueryScoreDao.url = url;

        Log.d("body",url);

        Request request = new Request.Builder()
                .url(url)
                .removeHeader("User-Agent")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36")
                .addHeader("Referer",main_url)
                .get()
                .build();
        Response rsp = OkHttpUitl.getInstance().newCall(request).execute();

        //jsoup解析
        Document doc = Jsoup.parse(rsp.body().string());

        postDatas = new HashMap<>();

        postDatas.put("__EVENTTARGET","");
        postDatas.put("__EVENTARGUMENT","");

        String __VIEWSTATE = doc.select("input[name=__VIEWSTATE]").val();
        postDatas.put("__VIEWSTATE",URLEncoder.encode(__VIEWSTATE,"GBK"));

    }

    public static List<ScoreBean> getScorePostAll() throws IOException {


        List<ScoreBean> scores = new ArrayList<>();

        if(postDatas==null){
            getScorePostData(url);
        }

        postDatas.put("ddlXN","");
        postDatas.put("ddlXQ","");
        postDatas.put("ddl_kcxz","");
        postDatas.put("btn_zcj", URLEncoder.encode("历年成绩","GBK"));

        //转为get方式链接
        String parma = UrlBean.utf2Gbk(postDatas);


        //以gbk方式提交表单数据
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=gb2312"), parma);

        Request requset = new Request.Builder()
                .url(url)
                .removeHeader("User-Agent")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36")
                .addHeader("Referer",url)
                .addHeader("Origin","http://222.180.192.2:8918")
                .addHeader("Host","222.180.192.2:8918")
                .post(requestBody)
                .build();

        Response rsp = OkHttpUitl.getInstance().newCall(requset).execute();


        Document doc = Jsoup.parse(rsp.body().string());

        Elements trs = doc.getElementById("Datagrid1").select("tr");



        for(int i = 1;i < trs.size();i++) {
            Elements tds = trs.get(i).select("td");

            ScoreBean sb = new ScoreBean();

            sb.setScoreYear(tds.get(0).text());
            sb.setScoreTerm(tds.get(1).text());
            sb.setScoreId(tds.get(2).text());
            sb.setScoreName(tds.get(3).text());
            sb.setScoreNature(tds.get(4).text());
            sb.setScoreCredit(tds.get(6).text());
            sb.setScoreGarde(tds.get(7).text());
            sb.setScoreResult(tds.get(8).text());
            sb.setScoreSchool(tds.get(12).text());

            scores.add(sb);
        }
        return scores;

    }



}
