package com.example.mrz.newproject.model.dao;

import android.util.Log;

import com.example.mrz.newproject.model.bean.Class;
import com.example.mrz.newproject.model.bean.UrlBean;
import com.example.mrz.newproject.model.bean.User;
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
 * Created by Mr.z on 2017/9/12.
 */

public class SelectClassDao {


    /**
     *
     * 解析网页得到课程
     *
     * @param url 传入要抓取课的url地址
     * @return
     * @throws IOException 网页访问异常
     */

    public static List<Class> getAllClass(String url) throws IOException {

        List<Class> classes = new ArrayList<>();

        //拼接首页地址
        String main_url = UrlBean.IP + "/" + UrlBean.sessionId + "/" + UrlBean.mainUrl + "?xh=" + User.xh;

        Request req = new Request.Builder()
                .url(url)
                .removeHeader("User-Agent")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36")
                .addHeader("Referer",main_url)
                .get()
                .build();

        //请求返回体
        Response rsp = OkHttpUitl.getInstance().newCall(req).execute();

        //获取网页源代码
        String body = rsp.body().string();


        //使用jsoup解析
        Document doc = Jsoup.parse(body);

        //获取table标签下的所有tr标签
        Elements trs = doc.select("table").first().select("tr");


        if(trs.size() < 2)
        {
            return null;

        }else {

            //获取表格的列数
            int tdSize = trs.first().select("td").size();

            //等于13 为学院选课，等于20 为全校性选课
            if(tdSize == 13){
                //循环遍历得到值
                for (int i = 1; i < trs.size(); i++) {
                    Elements tds = trs.get(i).select("td");
                    Class c = new Class();
                    c.setClassName(tds.get(2).text());
                    c.setClassTeacter(tds.get(3).text());
                    c.setClassTime(tds.get(4).text());
                    c.setClassPlace(tds.get(5).text());
                    c.setClassScore(tds.get(6).text());
                    c.setClassTotal(tds.get(8).text());
                    c.setClassSelected(tds.get(9).text());
                    c.setClassMargin(tds.get(10).text());

                    classes.add(c);
                }
            }else if(tdSize == 20){
                for (int i = 1; i < trs.size(); i++) {
                    Elements tds = trs.get(i).select("td");
                    Class c = new Class();
                    c.setClassName(tds.get(2).text());
                    c.setClassTeacter(tds.get(4).text());
                    c.setClassTime(tds.get(5).text());
                    c.setClassPlace(tds.get(6).text());
                    c.setClassScore(tds.get(7).text());
                    c.setClassTotal(tds.get(10).text());
                    c.setClassSelected(tds.get(11).text());
                    c.setClassMargin(tds.get(12).text());

                    classes.add(c);
                }
            }
            return classes;
        }

    }



}
