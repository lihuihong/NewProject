package com.example.mrz.newproject.model.dao;

import android.util.Log;

import com.example.mrz.newproject.model.bean.UrlBean;
import com.example.mrz.newproject.uitls.OkHttpUitl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by 那个谁 on 2017/9/14.
 * 奥特曼打小怪兽
 * 作用：学费收费情况查询登陆逻辑
 */

public class TuitionLoginDao {

    private static HashMap<String, String> postDatas;

    public static int Tlogin(String userName, String passwd) throws IOException {

        //获取登陆前地址
        String Tlogin_url1 = UrlBean.TIP ;
        //获取登陆后地址
        String Tlogin_url2 = UrlBean.TIP + UrlBean.TloginUrlafter;


        //获取表单数据
        Request request = new Request.Builder()
                .url(Tlogin_url1)
                .get()
                .build();
        Response rsp = OkHttpUitl.getInstance().newCall(request).execute();
        Document doc = Jsoup.parse(rsp.body().string());
        Element __VIEWSTATE = doc.getElementById("__VIEWSTATE");
        String value = __VIEWSTATE.attr("value");
        Element __EVENTTARGET = doc.getElementById("__EVENTTARGET");
        String value1 = __EVENTTARGET.attr("value");
        /*Element ImageButton1 = doc.getElementById("ImageButton1");
        String value2 = ImageButton1.attr("value");*/


//        builder.add("__VIEWSTATE", URLEncoder.encode(value,"GBK"));
//        builder.add("Button1",URLEncoder.encode(" 登 录 ","GBK"));
//        builder.add("RadioButtonList1", URLEncoder.encode("学生","GBK"));

        postDatas = new HashMap<String, String>();
        postDatas.put("__VIEWSTATE", URLEncoder.encode(value,"GBK"));
        postDatas.put("__EVENTTARGET",URLEncoder.encode(value1,"GBK"));
        postDatas.put("txt_yhm",URLEncoder.encode(userName,"GBK"));
        //postDatas.put("ImageButton1", URLEncoder.encode("学生","GBK"));
        postDatas.put("txt_pwd", URLEncoder.encode(userName,"GBK"));
        postDatas.put("ScriptManager1","UpdatePanel1|ImageButton1");


        int code = postLogin(Tlogin_url2,userName,userName);

        return code;
    }

    private static int postLogin(String login_url, String userName, String passwd){

        postDatas.put("txt_yhm",userName);
        postDatas.put("txt_pwd",passwd);

        //将map键值对形式的表单数据转换为链接形式
        String parma = UrlBean.utf2Gbk(postDatas);

        //以gbk方式提交表单数据
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"), parma);
        //请求时添加头文件伪造浏览器
        Request req = new Request.Builder().url(login_url).removeHeader("User-Agent")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36")
                .post(requestBody).build();


        try {
            //发起登录请求
            Response rsp = OkHttpUitl.getInstance().newCall(req).execute();

            //拼接首页地址
            String main_url = UrlBean.TIP + UrlBean.TloginUrl;

            //访问登录成功后的首页，成功者返回1,抛出异常返回-1
            getTMain(main_url);

            //User.setXh(userName);

            return 1;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }
    public static void getTMain(String url) throws Exception {

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Response rsp = OkHttpUitl.getInstance().newCall(request).execute();
        Document doc = Jsoup.parse(rsp.body().string());

        //获取学生姓名
        String txt_yhm = doc.body().toString();
        //User.setXm(txt_yhm);
        Log.i(TAG, "getTMain: body" + txt_yhm);
    }

}
