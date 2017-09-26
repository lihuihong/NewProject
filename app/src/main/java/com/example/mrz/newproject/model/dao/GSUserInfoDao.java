package com.example.mrz.newproject.model.dao;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.example.mrz.newproject.model.bean.UrlBean;
import com.example.mrz.newproject.model.bean.User;
import com.example.mrz.newproject.model.bean.UserInfoKVP;
import com.example.mrz.newproject.model.db.MySqlHelper;
import com.example.mrz.newproject.uitls.DBUtils;
import com.example.mrz.newproject.uitls.ImageDownload;
import com.example.mrz.newproject.uitls.OkHttpUitl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
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

    private static MySqlHelper dbHelper;

    public static Context context;

    private static Document doc;

    public static boolean isHaveInfo(Context context){

        GSUserInfoDao.context = context;

        dbHelper = DBUtils.getInstance(context);
        //获取读的数据库
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //获取记录总数
        Cursor cursor = db.rawQuery("select * from InfoKvp", null);
        cursor.moveToFirst();
        long count = cursor.getCount();

        cursor.close();
        db.close();

        if(count > 0){
            return true;
        }else{
            return false;
        }
    }

    //插入数据
    public static void insertUserInfo(){

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //获取table标签下的所有tr标签
        Elements trs = doc.getElementsByClass("formlist").first().select("tr");

        ContentValues values = new ContentValues();

        //姓名
        Elements tds = trs.get(1).select("td");
        values.put("infoKey", tds.get(0).text().split("：")[0]);
        values.put("infoValue", tds.get(1).text());
        db.insert("InfoKvp", null, values);

        //性别
        values = new ContentValues();
        tds = trs.get(3).select("td");
        values.put("infoKey", tds.get(0).text().split("：")[0]);
        values.put("infoValue", tds.get(1).text());
        db.insert("InfoKvp", null, values);

        //出身日期
        values = new ContentValues();
        tds = trs.get(4).select("td");
        values.put("infoKey", tds.get(0).text().split("：")[0]);
        values.put("infoValue", tds.get(1).text());
        db.insert("InfoKvp", null, values);

        //民族
        values = new ContentValues();
        tds = trs.get(5).select("td");
        values.put("infoKey", tds.get(0).text().split("：")[0]);
        values.put("infoValue", tds.get(1).text());
        db.insert("InfoKvp", null, values);

        //身份证号
        values = new ContentValues();
        tds = trs.get(10).select("td");
        values.put("infoKey", tds.get(2).text().split("：")[0]);

        String id = tds.get(3).text();
        values.put("infoValue",id);
        db.insert("InfoKvp", null, values);

       //将一卡通系统密码保存到本地
        SharedPreferences.Editor editor = context.getSharedPreferences("userInfoData", context.MODE_PRIVATE).edit();
        editor.putString("eardpwd", id.substring(id.length()-6));
        editor.apply();

        //手机号码
        values = new ContentValues();
        tds = trs.get(1).select("td");
        String value = tds.get(5).text();
        values.put("infoKey", tds.get(4).text().split("：")[0]);
        values.put("infoValue", value.isEmpty()? "-" : value);
        db.insert("InfoKvp", null, values);

        //来源省
        values = new ContentValues();
        tds = trs.get(9).select("td");
        values.put("infoKey", tds.get(0).text().split("：")[0]);
        values.put("infoValue", tds.get(1).text());
        db.insert("InfoKvp", null, values);

        //家庭地址
        values = new ContentValues();
        tds = trs.get(12).select("td");
        value = tds.get(5).text();
        values.put("infoKey", tds.get(4).text().split("：")[0]);
        values.put("infoValue", value.isEmpty()? "-" : value);
        db.insert("InfoKvp", null, values);

        //学号
        values = new ContentValues();
        tds = trs.get(0).select("td");
        values.put("infoKey", tds.get(0).text().split("：")[0]);
        values.put("infoValue", tds.get(1).text());
        db.insert("InfoKvp", null, values);

        //学历层次
        values = new ContentValues();
        tds = trs.get(11).select("td");
        values.put("infoKey", tds.get(2).text().split("：")[0]);
        values.put("infoValue", tds.get(3).text());
        db.insert("InfoKvp", null, values);

        //学院
        values = new ContentValues();
        tds = trs.get(12).select("td");
        values.put("infoKey", tds.get(0).text().split("：")[0]);
        values.put("infoValue", tds.get(1).text());
        db.insert("InfoKvp", null, values);

        //专业
        values = new ContentValues();
        tds = trs.get(14).select("td");
        values.put("infoKey", tds.get(0).text().split("：")[0]);
        values.put("infoValue", tds.get(1).text());
        db.insert("InfoKvp", null, values);

        //班级名称
        values = new ContentValues();
        tds = trs.get(16).select("td");
        values.put("infoKey", tds.get(0).text().split("：")[0]);
        values.put("infoValue", tds.get(1).text());
        db.insert("InfoKvp", null, values);

        //考生号
        values = new ContentValues();
        tds = trs.get(21).select("td");
        values.put("infoKey", tds.get(0).text().split("：")[0]);
        values.put("infoValue", tds.get(1).text());
        db.insert("InfoKvp", null, values);

        db.close();

    }


    //获取用户界面的所有信息
    public static void getAllUserInfo(String url) throws IOException {

        String main_url = UrlBean.IP + "/" + UrlBean.sessionId + "/" + UrlBean.mainUrl + "?xh=" + User.xh;

        Request request = new Request.Builder()
                .url(url)
                .removeHeader("User-Agent")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36")
                .addHeader("Referer",main_url)
                //拼接首页地址
                .get()
                .build();

        Response rsp = OkHttpUitl.getInstance().newCall(request).execute();

        //获取网页源码
        String body = rsp.body().string();

        //使用jsoup解析
        doc = Jsoup.parse(body);

        //插入到数据库
        insertUserInfo();
    }


    /**
     * 从数据库查询数据
     *
     * @param start 开始位置
     * @param end   结束位置
     * @return 数据集
     */
    public static List<UserInfoKVP> getInfos(String start,String end){
        List<UserInfoKVP> infos = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from InfoKvp where id >= ? and id <= ?",new String[]{start,end});


        while(cursor.moveToNext()){
            infos.add(new UserInfoKVP(cursor.getString(1),cursor.getString(2)));
        }
        cursor.close();
        db.close();
        return infos;
    }

    public static Bitmap getUserInfoImg() throws IOException {

        Bitmap bitmap = loadBitmap();
        if(bitmap != null){
            return bitmap;
        }

        //获取图片地址
        String imgSrc = doc.getElementById("xszp").attr("src");
        String imgUrl = UrlBean.IP + "/" + UrlBean.sessionId + "/" + imgSrc;

        //返回图片
        bitmap = ImageDownload.download(imgUrl);
        saveBitmap(bitmap);
        return bitmap;
    }

    //保存图片到本地
    private static void saveBitmap(Bitmap bitmap)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("headPic", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        String imageBase64 = new String(Base64.encodeToString(baos.toByteArray(),Base64.DEFAULT));
        editor.putString("headPic",imageBase64 );
        editor.commit();
    }

    //从本地读取图片
    private static Bitmap loadBitmap()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("headPic",Activity.MODE_PRIVATE);
        String headPic = sharedPreferences.getString("headPic","");
        Bitmap bitmap = null;
        if (headPic != "") {
            byte[] bytes = Base64.decode(headPic.getBytes(), 1);
            //  byte[] bytes =headPic.getBytes();
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        return bitmap;
    }

}
