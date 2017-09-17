package com.example.mrz.newproject.model.dao;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.example.mrz.newproject.model.bean.Page;
import com.example.mrz.newproject.uitls.MyCode;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 那个谁 on 2017/9/14.
 * 奥特曼打小怪兽
 * 作用：学费收费情况查询登陆逻辑
 */

public class BalanceLoginDao {
    //post提交数据
    Map<String,String> postDatas = new HashMap<>();
    private static OkHttpClient okHttpClient;
    private static String sBalanceloginBaseUrl;

    private String name;
    private String password;

    public BalanceLoginDao(String name, String password) {
        this.name = name;
        this.password = password;

        postDatas.put("UserLogin:txtSure", MyCode.getInstance().getCode());
        postDatas.put("UserLogin:txtUser", name);
        postDatas.put("UserLogin:txtPwd", password);
    }

    private List<Page> items;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x1234:
                   /* loginCode.setImageBitmap((Bitmap) msg.obj);
                    flag = true;*/
                default:
            }
        }
    };

    protected void getImage(){
        new Thread(){
            @Override
            public void run() {
                Request request = new Request.Builder().url(sBalanceloginBaseUrl).get().build();
                try {
                    Response rsp = okHttpClient.newCall(request).execute();
                    if (rsp.isSuccessful()){

                        String data = rsp.body().string();
                        Document document = Jsoup.parse(data);

                        Element viewstate =
                                document.select("input[name=\"__VIEWSTATE\"]").first();
                        Element eventvalidation =
                                document.select("input[name=\"__EVENTVALIDATION\"]").first();

                        String UserLogin_ImgFirst =
                                document.getElementById("UserLogin_ImgFirst").attr("src");
                        String UserLogin_imgSecond =
                                document.getElementById("UserLogin_imgSecond").attr("src");
                        String UserLogin_imgThird =
                                document.getElementById("UserLogin_imgThird").attr("src");
                        String UserLogin_imgFour =
                                document.getElementById("UserLogin_imgFour").attr("src");

                        String num = UserLogin_ImgFirst+UserLogin_imgSecond+UserLogin_imgThird+UserLogin_imgFour;

                        String regEx="[^0-9]";
                        Pattern p = Pattern.compile(regEx);
                        Matcher m = p.matcher(num);
                        Log.d("num",num);
                        Log.d("num",m.replaceAll(""));
                        Bitmap login_code = MyCode.getInstance().createBitmap(m.replaceAll(""));

                        postDatas.put("__VIEWSTATE",viewstate.attr("value"));
                        postDatas.put("__EVENTVALIDATION",eventvalidation.attr("value"));

                        Message msg = new Message();
                        msg.what = 0x1234;
                        msg.obj = login_code;
                        handler.sendMessage(msg);

                    }
                }  catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void login() {

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

        Log.d("postdatas", replace);

        new Thread() {
            @Override
            public void run() {

                RequestBody requestBody = null;
                try {
                    requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=gb2312"), new String(replace.getBytes(), "GBK"));
                    Request request = new Request.Builder().url(sBalanceloginBaseUrl).removeHeader("User-Agent")
                            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36")
                            .post(requestBody).build();
                    Response rsp = okHttpClient.newCall(request).execute();
                    String data = rsp.body().string();
                    //Log.d("code", code.getText().toString());
                    Log.d("code__", MyCode.getInstance().getCode());
                    if (rsp.code() == 302 && data != null) {
                        Log.d("data", data);
                        if (data.contains("Cardholder")) {
                            /*SharedPreferences.Editor editor = getSharedPreferences("userInfoData", MODE_PRIVATE).edit();
                            editor.putString("userName", userName.getText().toString());
                            editor.putString("passWord", passWord.getText().toString());
                            editor.apply();*/
                            getItem();

                        }
                    } else {
                        Looper.prepare();
                        Looper.loop();
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (SocketTimeoutException e1) {
                    Looper.prepare();
                   // Toast.makeText(get, "连接超时，请重试", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
    public void getItem() {
        String url = "http://www.cqcet.edu.cn/";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request res = new Request.Builder().url(url + "index/xwdt.htm").get().build();
        try {
            Response rsp = okHttpClient.newCall(res).execute();
            if (rsp.isSuccessful()) {
                String data = rsp.body().string();
                Document doc = Jsoup.parse(data, "GBK");
                String pageNum = doc.getElementById("fanye50592").text().split("/")[1].replace(" ", "");   //获取页数
                Log.d("pageNum", pageNum);
                Elements htmlItems = doc.getElementsByClass("c50592");
                for (int i = 0; i < htmlItems.size(); i++) {
                    Element element = htmlItems.get(i);
                    String next_url = element.attr("href").replace("../../", "");

                    res = new Request.Builder().url(url + next_url).get().build();
                    rsp = okHttpClient.newCall(res).execute();
                    String body = rsp.body().string();
                    doc = Jsoup.parse(body, "GBK");

                    Page p = new Page();
                    p.setUrl(url + next_url);
                    p.setBody(body);
                    String page_id = next_url.split("/")[2].split("\\.")[0];
                    String num_url = "http://www.cqcet.edu.cn/system/resource/code/news/click/dynclicks.jsp?clickid=" + page_id + "&owner=1135322561&clicktype=wbnews";
                    res = new Request.Builder().url(num_url).get().build();
                    rsp = okHttpClient.newCall(res).execute();

                    p.setTitle(element.attr("title"));
                    Log.d("title", "title" + element.attr("title"));
                    p.setDate(doc.getElementById("article_extinfo").text().replace("]", rsp.body().string() + "]"));

                    Elements imgs = doc.select("div#article_body").select("img");
                    if (imgs.size() > 0) {
                        String imgUrl = imgs.get(0).attr("src").replace("../../", url);
                        p.setImgUrl(imgUrl);
                    }

                    items.add(p);
                    //p.setImg();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
