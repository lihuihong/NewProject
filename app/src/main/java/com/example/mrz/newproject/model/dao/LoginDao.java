package com.example.mrz.newproject.model.dao;

import android.app.DownloadManager;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.example.mrz.newproject.controller.activity.LoginActivity;
import com.example.mrz.newproject.model.bean.UrlBean;
import com.example.mrz.newproject.model.bean.User;
import com.example.mrz.newproject.uitls.OkHttpUitl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 登录逻辑处理类
 *
 * Created by Mr.z on 2017/9/11.
 */

public class LoginDao {

    private static Map<String,String> postDatas;

    /**
     * 登录逻辑处理过程
     *
     * @param userName  用户名
     * @param passwd  密码
     * @return
     */
    public static int login(String userName, String passwd) throws IOException {

        //获取sessionId
        getSession();

        //根据sessionId拼接链接
        String login_url = UrlBean.IP + "/" + UrlBean.sessionId + "/" +UrlBean.loginUrl;

        //获取表单数据
        getPostData(login_url);

        int code = postLogin(login_url,userName,passwd);

        return code;
    }

    private static int postLogin(String login_url, String userName, String passwd){

        postDatas.put("TextBox1",userName);
        postDatas.put("TextBox2",passwd);

        //将map键值对形式的表单数据转换为链接形式
        String parma = UrlBean.utf2Gbk(postDatas);

        //以gbk方式提交表单数据
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=gb2312"), parma);
        //请求时添加头文件伪造浏览器
        Request req = new Request.Builder().url(login_url).removeHeader("User-Agent")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36")
                .post(requestBody).build();

        try {
            //发起登录请求
            Response rsp = OkHttpUitl.getInstance().newCall(req).execute();

            //拼接首页地址
            String main_url = UrlBean.IP + "/" + UrlBean.sessionId + "/" + UrlBean.mainUrl + "?xh=" +  userName;

            //访问登录成功后的首页，成功者返回1,抛出异常返回-1
            getMain(main_url);

            return 1;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }

    public static void getMain(String url) throws Exception {

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Response rsp = OkHttpUitl.getInstance().newCall(request).execute();

        Document doc = Jsoup.parse(rsp.body().string());

        //获取学生姓名
        String xhxm = doc.getElementById("xhxm").text();
        xhxm = xhxm.substring(0,xhxm.length()-2);
        Log.d("LoginDao",xhxm);
        User.setXm(xhxm);
    }

    //获取表单数据
    private static void getPostData(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Response rsp = OkHttpUitl.getInstance().newCall(request).execute();
        Document doc = Jsoup.parse(rsp.body().string());
        Element input = doc.getElementsByTag("input").first();
        String value = input.attr("value");

        postDatas = new HashMap<String,String>();

//        builder.add("__VIEWSTATE", URLEncoder.encode(value,"GBK"));
//        builder.add("Button1",URLEncoder.encode(" 登 录 ","GBK"));
//        builder.add("RadioButtonList1", URLEncoder.encode("学生","GBK"));


        postDatas.put("__VIEWSTATE", URLEncoder.encode(value,"GBK"));
        postDatas.put("Button1",URLEncoder.encode(" 登 录 ","GBK"));
        postDatas.put("RadioButtonList1", URLEncoder.encode("学生","GBK"));
    }


    //获取重定向后地址中的session
    private static void getSession() throws IOException {
        //第一次访问，获取重定向后的内容
        Request request = new Request.Builder()
                .url(UrlBean.IP)
                .get()
                .build();

        Response rsp = OkHttpUitl.getInstance().newCall(request).execute();
        String body = rsp.body().string();
        //使用jsoup解析
        Document doc = Jsoup.parse(body);

        //获取文件中所有a标记中的第一个
        Element a = doc.getElementsByTag("a").get(0);

        //获取a标签的href值
        String href = a.attr("href");
        //获取链接中的sessionId
        String session = href.split("/")[1];

        UrlBean.setSessionId(session);
    }


    /**
     * 判断字符串是否全为数字
     *
     * @param str 判断的字符串
     * @return
     */
    private static boolean isNumeric(String str) {

        try{
            Integer.parseInt(str);
        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }

    /**
     * 检查用户名和密码是否合格
     *
     * @param userName 用户名
     * @param passwd 密码
     * @return  -1:用户名为空   -2:密码为空   -3:用户名不合法   1:检查通过
     */
    public static int check(String userName, String passwd) {
        if(TextUtils.isEmpty(userName))
            return -1;
        else if(TextUtils.isEmpty(passwd))
            return -2;
        else if(!isNumeric(userName))
            return -3;
        else
            return 1;
    }
}
