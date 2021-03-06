package com.example.mrz.newproject.model.dao;

import android.util.Log;

import com.example.mrz.newproject.model.bean.UrlBean;
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

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 那个谁 on 2017/9/19.
 * 奥特曼打小怪兽
 * 作用：
 */

public class EcardDao {

    //post提交数据
    private static Map<String, String> postDatas = new HashMap<>();


    public static void login(String name,String pwd) throws IOException {

        getcode();
        String replace = postData(name,pwd);
        //发起请求
        RequestBody requestBody = null;
        requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=gb2312"), new String(replace.getBytes(), "GBK"));
        Request request = new Request.Builder().url(UrlBean.ECARDURL).removeHeader("User-Agent")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36")
                .post(requestBody)
                .build();
        Response rsp = OkHttpUitl.getInstance().newCall(request).execute();

        Log.d("body",rsp.body().string());

    }

    /**
     * 获取登录提交数据
     *
     * @return 登录链接
     */
    public static String postData(String name,String pwd){

        postDatas.put("UserLogin:txtUser", name);
        Log.i("用户", "login: " + name);
        postDatas.put("UserLogin:txtPwd", pwd);
        Log.i("密码", "login: " + pwd);


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

        //手动将utf转为gbk编码
        String parma = "";
        for (Iterator<Map.Entry<String, String>> it = postDatas.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry entry = it.next();
            parma += entry.getKey() + "=" + entry.getValue();
            if (it.hasNext())
                parma += "&";
        }
        final String replace = parma.replace(":", "%3a").replace("+", "%2b");

        return replace;
    }

    /**
     *  获取登录验证码
     *
     * @throws IOException
     */
    public static void getcode() throws IOException {
        Request request = new Request.Builder().url(UrlBean.ECARDURL).get().build();
        Response rsp = OkHttpUitl.getInstance().newCall(request).execute();
        if (rsp.isSuccessful()) {
            String data = rsp.body().string();
            Document document = Jsoup.parse(data);
            Element viewstate = document.select("input[name=\"__VIEWSTATE\"]").first();
            Element eventvalidation = document.select("input[name=\"__EVENTVALIDATION\"]").first();

            //截取验证码
            String UserLogin_ImgFirst = (String) document.getElementById("UserLogin_ImgFirst").attr("src").subSequence(7,8);
            String UserLogin_imgSecond = (String) document.getElementById("UserLogin_imgSecond").attr("src").subSequence(7,8);
            String UserLogin_imgThird = (String) document.getElementById("UserLogin_imgThird").attr("src").subSequence(7,8);
            String UserLogin_imgFour = (String) document.getElementById("UserLogin_imgFour").attr("src").subSequence(7,8);
            String num = UserLogin_ImgFirst + UserLogin_imgSecond + UserLogin_imgThird + UserLogin_imgFour;


            postDatas.put("__VIEWSTATE", viewstate.attr("value"));
            postDatas.put("__EVENTVALIDATION", eventvalidation.attr("value"));
            postDatas.put("UserLogin:txtSure",num);

        }

    }

}
