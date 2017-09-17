package com.example.mrz.newproject.model.dao;

import com.example.mrz.newproject.model.bean.UrlBean;
import com.example.mrz.newproject.model.bean.User;
import com.example.mrz.newproject.model.bean.UserInfoKVP;
import com.example.mrz.newproject.uitls.OkHttpUitl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

/**
 *
 * 获取用户信息和修改用户信息
 * Created by Mr.z on 2017/9/14.
 */

public class GSUserInfoDao {



    //获取用户界面的所有信息
    public static Elements getAllUserInfo(String url) throws IOException {

        //拼接首页地址
        String main_url = UrlBean.IP + "/" + UrlBean.sessionId + "/" + UrlBean.mainUrl + "?xh=" + User.xh;


        Request request = new Request.Builder()
                .url(url)
                .removeHeader("User-Agent")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36")
                .addHeader("Referer",main_url)
                .get()
                .build();

        Response rsp = OkHttpUitl.getInstance().newCall(request).execute();

        //获取网页源码
        String body = rsp.body().string();


        //使用jsoup解析
        Document doc = Jsoup.parse(body);

        //获取table标签下的所有tr标签
        Elements trs = doc.getElementsByClass("formlist").first().select("tr");

        return trs;
    }

    /**
     * //解析基本信息
     *
     * @param trs 传入表格
     */
    public static List<UserInfoKVP> getbasicInfo(Elements trs){

        List<UserInfoKVP> infos = new ArrayList<>();

        //姓名
        Elements tds = trs.get(1).select("td");
        infos.add(new UserInfoKVP(tds.get(0).text().split("：")[0],tds.get(1).text()));

        //性别
        tds = trs.get(3).select("td");
        infos.add(new UserInfoKVP(tds.get(0).text(),tds.get(1).text()));

        //出身日期
        tds = trs.get(4).select("td");
        infos.add(new UserInfoKVP(tds.get(0).text(),tds.get(1).text()));

        //民族
        tds = trs.get(5).select("td");
        infos.add(new UserInfoKVP(tds.get(0).text(),tds.get(1).text()));

        //身份证号
        tds = trs.get(10).select("td");
        String id = tds.get(3).text();
        User.setId(id.substring(tds.get(3).text().length()-6,id.length()));
        infos.add(new UserInfoKVP(tds.get(2).text(),tds.get(3).text()));

        return infos;
    }

    public static List<UserInfoKVP> getSchoolInfo(Elements trs){

        List<UserInfoKVP> infos = new ArrayList<>();

        //学号
        Elements tds = trs.get(0).select("td");
        infos.add(new UserInfoKVP(tds.get(0).text().split("：")[0],tds.get(1).text()));

        //学历层次
        tds = trs.get(11).select("td");
        infos.add(new UserInfoKVP(tds.get(2).text().split("：")[0],tds.get(3).text()));

        //学院
        tds = trs.get(12).select("td");
        infos.add(new UserInfoKVP(tds.get(0).text().split("：")[0],tds.get(1).text()));

        //专业
        tds = trs.get(14).select("td");
        infos.add(new UserInfoKVP(tds.get(0).text().split("：")[0],tds.get(1).text()));

        //班级名称
        tds = trs.get(16).select("td");
        infos.add(new UserInfoKVP(tds.get(0).text().split("：")[0],tds.get(1).text()));

        //考生号
        tds = trs.get(21).select("td");
        infos.add(new UserInfoKVP(tds.get(0).text().split("：")[0],tds.get(1).text()));

        return infos;
    }

}
