package com.example.mrz.newproject.model.dao;

import android.util.Log;

import com.example.mrz.newproject.model.bean.CourseBean;
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


public class CourseDao {

    public static List<CourseBean>[] getCourseData(String url) throws IOException {

        List<CourseBean> courseModels[] = new ArrayList[7];

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

        String body = rsp.body().string();
        Document doc = Jsoup.parse(body);


        //获取所有行的内容
        Elements trs = doc.getElementById("Table1").select("tr");

        for (int i = 0; i < courseModels.length; i++) {
            courseModels[i] = new ArrayList<>();
        }

        //第一列的数据
        String[] arr = new String[6];
        for (int i = 0;i < courseModels.length;i++){
            arr[0] = trs.get(2).select("td").get(2+i).text();
            arr[1] = trs.get(4).select("td").get(1+i).text();
            arr[2] = trs.get(6).select("td").get(2+i).text();
            arr[3] = trs.get(8).select("td").get(1+i).text();
            arr[4] = trs.get(10).select("td").get(2+i).text();
            arr[5] = trs.get(12).select("td").get(1+i).text();
            courseModels[i].addAll(getItem(arr));
        }
        /*arr[0] = (trs.get(2).select("td").get(2).text());
        arr[1] = trs.get(4).select("td").get(1).text();
        arr[2] = trs.get(6).select("td").get(2).text();
        arr[3] = trs.get(8).select("td").get(1).text();
        arr[4] = trs.get(10).select("td").get(2).text();
        arr[5] = trs.get(11).select("td").get(1).text();




        arr = new String[6];
        arr[0] = (trs.get(2).select("td").get(3).text());
        arr[1] = trs.get(4).select("td").get(2).text();
        arr[2] = trs.get(6).select("td").get(3).text();
        arr[3] = trs.get(8).select("td").get(4).text();
        arr[4] = trs.get(10).select("td").get(5).text();
        arr[5] = trs.get(11).select("td").get(6).text();



        List<CourseBean> models_2 = new ArrayList<>();
        models_2.add(new CourseBean(3, "Swift", 3, 2, 2, "A222", (int) (Math.random() * 10)));
        models_2.add(new CourseBean(3, "JavaScript", 5, 2, 2, "A777", (int) (Math.random() * 10)));
        courseModels[1].addAll(models_2);

        List<CourseBean> models_3 = new ArrayList<>();
        models_3.add(new CourseBean(3, "Python", 1, 2, 3, "A342", (int) (Math.random() * 10)));
        models_3.add(new CourseBean(3, "Visual Basic .NET", 3, 2, 3, "A737", (int) (Math.random() * 10)));
        courseModels[2].addAll(models_3);

        List<CourseBean> models_4 = new ArrayList<>();
        models_4.add(new CourseBean(4, "C#", 1, 2, 4, "A666", (int) (Math.random() * 10)));
        models_4.add(new CourseBean(5, "R语言", 7, 2,4, "A888", (int) (Math.random() * 10)));
        models_4.add(new CourseBean(5, "Java", 9, 2, 4, "A828", (int) (Math.random() * 10)));
        courseModels[3].addAll(models_4);

        List<CourseBean> models_5 = new ArrayList<>();
        models_5.add(new CourseBean(6, "Android", 1, 2, 5, "A466", (int) (Math.random() * 10)));
        models_5.add(new CourseBean(7, "Groovy", 3, 2, 5, "A434", (int) (Math.random() * 10)));
        models_5.add(new CourseBean(8, "Objective-C", 5, 2, 5, "A411", (int) (Math.random() * 10)));
        courseModels[4].addAll(models_5);

        List<CourseBean> models_6 = new ArrayList<>();
        models_6.add(new CourseBean(9, "C++", 1, 2, 6, "A422", (int) (Math.random() * 10)));
        models_6.add(new CourseBean(10, "SQL", 7, 2, 6, "A402", (int) (Math.random() * 10)));
        courseModels[5].addAll(models_6);*/

        return courseModels;

    }

    public static List<CourseBean> getItem(String[] arr){
        List<CourseBean> models_1 = new ArrayList<>();
        for(int i = 0;i < arr.length;i++){
            if(!arr[i].equals(" ")){
                String[] splits = arr[i].split(" ");
                if(splits.length > 4 && splits.length < 6)
                    models_1.add(new CourseBean(0, splits[0]+splits[1], i*2+1, 2, 1, splits[4], (int) (Math.random() * 10)));
                else
                    models_1.add(new CourseBean(0, splits[0], i*2+1, 2, 1, splits[3], (int) (Math.random() * 10)));
            }
        }
        return models_1;
    }
}
