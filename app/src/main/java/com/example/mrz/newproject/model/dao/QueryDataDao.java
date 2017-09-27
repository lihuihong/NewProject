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
    private static Consume mCs;
    private static List<Consume> mConsumes;
    private static Request res1;
    private static String mData1;

    //请求数据
    public static List<Consume> intiDa(final String ur,final String ur1,final String month) {
        Calendar a = Calendar.getInstance();
        final String mYear = String.valueOf(a.get(Calendar.YEAR));
        mConsumes = new ArrayList<>();
        Request res = new Request.Builder().url(ur).get().build();
        if (ur1!=null){
            Request res1 = new Request.Builder().url(ur1).get().build();
            try {
                Response rsp1 = OkHttpUitl.getInstance().newCall(res1).execute();
                if (rsp1.isSuccessful()){
                    mData1 = rsp1.body().string();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
        }
        try {
            Response rsp = OkHttpUitl.getInstance().newCall(res).execute();
            if (rsp.isSuccessful()) {
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
                            if (mData1!=null){
                                mCs = new Consume();
                                Document doc1 = Jsoup.parse(mData1, "GBK");
                                mAccountNumber = doc1.getElementById("lblOne0").text();
                                mCs.setAccountNumber(mAccountNumber);
                                mConsumes.add(mCs);
                                return mConsumes;
                            }
                            return null;
                        }
                        for (int i = trs.size() - 1; i > 0; i--) {
                            mCs = new Consume();
                            Elements tds = trs.get(i).select("td");
                            mCs.setAddress(tds.get(4).text());
                            String priceNum = tds.get(7).text();
                            try {
                                priceNum = Integer.parseInt(priceNum) > 0 ? "+" + priceNum : priceNum;

                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                            mCs.setPrice(priceNum);
                            mCs.setDate(tds.get(8).text());
                            mCs.setBalance(tds.get(10).text());
                            mConsumes.add(mCs);
                        }
                        //Log.i("数据", "intiDa: " + mConsumes.get(5).getAddress());

                    }
                    if (mData1!=null){
                        Document doc1 = Jsoup.parse(mData1, "GBK");
                        mAccountNumber = doc1.getElementById("lblOne0").text();
                        mCs.setAccountNumber(mAccountNumber);
                        mConsumes.add(mCs);
                    }

                } else {
                    //Log.i("code", "run: " + "sb");
                }

            } else {
                //Log.i("code", "run: " + rsp.code() +"bs" +rsp1.code());
            }

        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mConsumes;
    }

    public static List<Consume> intiToady(String url) {
        mConsumes = new ArrayList<>();
        Request res = new Request.Builder().url(url).get().build();
        Response rsp = null;
        try {
            rsp = OkHttpUitl.getInstance().newCall(res).execute();
            if (rsp.isSuccessful()){
                String data = rsp.body().string();
                Document doc  = Jsoup.parse(data, "GBK");
                //解析数据
                Elements trs;
                try {
                    trs = doc.getElementById("dgShow").select("tr");
                } catch (NullPointerException e) {
                    Log.i("啊撇1", "intiToady: ");
                    return null;
                }
                for (int i = trs.size() - 1; i > 0; i--) {
                    mCs = new Consume();
                    Elements tds = trs.get(i).select("td");
                    mCs.setAddress(tds.get(4).text());
                    String priceNum = tds.get(7).text();
                    try {
                        priceNum = Integer.parseInt(priceNum) > 0 ? "+" + priceNum : priceNum;

                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    mCs.setPrice(priceNum);
                    mCs.setDate(tds.get(8).text());
                    mCs.setBalance(tds.get(10).text());
                    mConsumes.add(mCs);
                    Log.i("啊撇2", "intiToady: "+mConsumes.get(0).getAddress());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mConsumes;
    }

}
