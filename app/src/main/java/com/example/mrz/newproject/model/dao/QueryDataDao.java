package com.example.mrz.newproject.model.dao;

import android.util.Log;

import com.example.mrz.newproject.model.bean.Consume;
import com.example.mrz.newproject.model.bean.UrlBean;
import com.example.mrz.newproject.uitls.OkHttpUitl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 那个谁 on 2017/9/22.
 * 奥特曼打小怪兽
 * 作用：
 */

public class QueryDataDao {

    //余额
    private static String mAccountNumber;
    private static Consume mCs = new Consume();

    //请求数据
    public static List<Consume> intiDa(final String ur, final String ur1, final String month) {
        Calendar a = Calendar.getInstance();
        final String mYear = String.valueOf(a.get(Calendar.YEAR));
        final List<Consume> mConsumes = new ArrayList<>();
        Request res = new Request.Builder().url(ur).get().build();
        Request res1 = new Request.Builder().url(ur1).get().build();
        try {
            Response rsp = OkHttpUitl.getInstance().newCall(res).execute();
            Response rsp1 = OkHttpUitl.getInstance().newCall(res1).execute();
            if (rsp.isSuccessful() || rsp1.isSuccessful()) {
                Document doc = Jsoup.parse(rsp.body().string(), "GBK");
                String viewstate = doc.select("input[name=\"__VIEWSTATE\"]").first().attr("value");
                Log.d("__VIEWSTATE", "__VIEWSTATE" + viewstate);
                RequestBody requestBody = new FormBody.Builder()
                        .add("__VIEWSTATE", viewstate)
                        .add("ddlYear", mYear)
                        .add("ddlMonth", month)
                        .add("txtMonth", month)
                        .add("ImageButton1.x", "22")
                        .add("ImageButton1.y", "16").build();
                res = new Request.Builder().url(UrlBean.ECARD_QUERY_URL).post(requestBody).build();
                rsp = OkHttpUitl.getInstance().newCall(res).execute();
                if (rsp.code() == 302) {
                    String data = rsp.body().string();
                    if (data.contains("QueryhistoryDetail")) {
                        res = new Request.Builder().url(UrlBean.POST_QUERY_URL).get().build();
                        rsp = OkHttpUitl.getInstance().newCall(res).execute();
                        data = rsp.body().string();
                        doc = Jsoup.parse(data, "GBK");
                        //Log.d("doc", data);
                        //解析数据
                        Elements trs;
                        try {
                            trs = doc.getElementById("dgShow").select("tr");
                        } catch (NullPointerException e) {
                            String data1 = rsp1.body().string();
                            Document doc1 = Jsoup.parse(data1, "GBK");
                            mAccountNumber = doc1.getElementById("lblOne0").text();
                            mCs.setAccountNumber(mAccountNumber);
                            mConsumes.add(mCs);
                            return mConsumes;
                        }
                        for (int i = trs.size() - 1; i > 0; i--) {
                            Elements tds = trs.get(i).select("td");
                            mCs.setAddress(tds.get(4).text());
                            String priceNum = tds.get(7).text();
                            try {
                                priceNum = Long.parseLong(priceNum) > 0 ? "+" + priceNum : priceNum;

                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                            mCs.setPrice(priceNum);
                            mCs.setDate(tds.get(8).text());
                            //将取到的数据加在list里面
                            mConsumes.add(mCs);
                        }
                    }
                    //将取到的数据加在list里面
                    String data1 = rsp1.body().string();
                    Document doc1 = Jsoup.parse(data1, "GBK");
                    mAccountNumber = doc1.getElementById("lblOne0").text();
                    mCs.setAccountNumber(mAccountNumber);
                    mConsumes.add(mCs);
                } else {
                    Log.i("code", "run: " + "sb");
                }

            } else {
                Log.i("code", "run: " + rsp.code() + "bs" + rsp1.code());
            }

        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mConsumes;
    }

}
