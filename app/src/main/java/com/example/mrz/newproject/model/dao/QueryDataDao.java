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

    public static List<Consume> intiDa(final String ur) {
        Calendar a = Calendar.getInstance();
        final String mYear = String.valueOf(a.get(Calendar.YEAR));
        final String mMonth = String.valueOf(a.get(Calendar.MONTH) + 1);
        final List<Consume> mConsumes = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request res = new Request.Builder().url(ur).get().build();
                try {
                    Response rsp = OkHttpUitl.getInstance().newCall(res).execute();
                    if (rsp.isSuccessful()) {
                        Document doc = Jsoup.parse(rsp.body().string(), "GBK");
                        String viewstate = doc.select("input[name=\"__VIEWSTATE\"]").first().attr("value");
                        Log.d("__VIEWSTATE", "__VIEWSTATE" + viewstate);
                        RequestBody requestBody = new FormBody.Builder()
                                .add("__VIEWSTATE", viewstate)
                                .add("ddlYear", mYear)
                                .add("ddlMonth", mMonth)
                                .add("txtMonth", mMonth)
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
                                    return;
                                }
                                for (int i = trs.size() - 1; i > 0; i--) {
                                    Elements tds = trs.get(i).select("td");
                                    Consume  mCs = new Consume();
                                    mCs.setAddress(tds.get(4).text());
                                    String priceNum = tds.get(7).text();
                                    try {
                                        priceNum = Long.parseLong(priceNum) > 0 ? "+" + priceNum : priceNum;

                                    } catch (NumberFormatException e) {
                                        e.printStackTrace();
                                    }
                                    mCs.setPrice(priceNum);
                                    mCs.setDate(tds.get(8).text());
                                    mConsumes.add(mCs);
                                }
                            }

                        } else {
                        }

                    } else {
                    }
                } catch (SocketTimeoutException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return mConsumes;

    }

}
