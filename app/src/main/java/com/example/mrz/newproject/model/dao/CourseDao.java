package com.example.mrz.newproject.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.mrz.newproject.model.bean.CourseBean;
import com.example.mrz.newproject.model.bean.UrlBean;
import com.example.mrz.newproject.model.bean.User;
import com.example.mrz.newproject.model.db.MySqlHelper;
import com.example.mrz.newproject.uitls.DBUtils;
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

    private static MySqlHelper sqlHelper;


    /**
     * 查看数据库是否有数据
     *
     * @param context 上下文
     * @return
     */
    public static boolean isHaveCourse(Context context){

        sqlHelper = DBUtils.getInstance(context);
        //获取读的数据库
        SQLiteDatabase db = sqlHelper.getReadableDatabase();

        //获取记录总数
        Cursor cursor = db.rawQuery("select * from Course", null);
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

    /**
     * 从数据库查询数据
     *
     * @return
     */
    public static List<CourseBean>[] queryCourseData(){

        List<CourseBean> courseModels[] = new ArrayList[7];

        for (int i = 0; i < courseModels.length; i++) {
            courseModels[i] = new ArrayList<>();
        }

        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Course", null);

        String arr[][] = new String[6][7];
        int n = 0;
        while(cursor.moveToNext()){
            for(int j = 0;j < 7;j++){
                //Log.d("body",cursor.getString(j));
                arr[n][j] = cursor.getString(j);
            }
            n++;
        }
        for(int i = 0;i < courseModels.length;i++){
            String[] comArr = new String[6];
            for(int j = 0;j < 6;j++){
                comArr[j] = arr[j][i];
            }
            courseModels[i].addAll(getItem(comArr));
        }

        return courseModels;

    }

    //从网络获取课表信息并插入到数据库
    public static void getCourseData(String url) throws IOException {


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

        SQLiteDatabase db = sqlHelper.getWritableDatabase();



        //第一列的数据
        String[] arr = new String[6];
        for (int i = 1;i < 7;i++){
            /*arr[0] = trs.get(2).select("td").get(2+i).text();
            arr[1] = trs.get(4).select("td").get(1+i).text();
            arr[2] = trs.get(6).select("td").get(2+i).text();
            arr[3] = trs.get(8).select("td").get(1+i).text();
            arr[4] = trs.get(10).select("td").get(2+i).text();
            arr[5] = trs.get(12).select("td").get(1+i).text();
            courseModels[i].addAll(getItem(arr));*/
            if(i % 2!=0){
                ContentValues values = new ContentValues();
                values.put("monday",trs.get(i*2).select("td").get(2).text());
                values.put("Tuesday",trs.get(i*2).select("td").get(3).text());
                values.put("Wednesday",trs.get(i*2).select("td").get(4).text());
                values.put("Thursday",trs.get(i*2).select("td").get(5).text());
                values.put("Friday",trs.get(i*2).select("td").get(6).text());
                values.put("Saturday",trs.get(i*2).select("td").get(7).text());
                values.put("sunday",trs.get(i*2).select("td").get(8).text());
                db.insert("Course",null,values);
            }else{
                ContentValues values = new ContentValues();
                values.put("monday",trs.get(i*2).select("td").get(1).text());
                values.put("Tuesday",trs.get(i*2).select("td").get(2).text());
                values.put("Wednesday",trs.get(i*2).select("td").get(3).text());
                values.put("Thursday",trs.get(i*2).select("td").get(4).text());
                values.put("Friday",trs.get(i*2).select("td").get(5).text());
                values.put("Saturday",trs.get(i*2).select("td").get(6).text());
                values.put("sunday",trs.get(i*2).select("td").get(7).text());
                db.insert("Course",null,values);
            }
        }

        db.close();

        /*List<CourseBean> models_2 = new ArrayList<>();
        models_2.add(new CourseBean(3, "Swift", 3, 2, 2, "A222", (int) (Math.random() * 10)));
        models_2.add(new CourseBean(3, "JavaScript", 5, 2, 2, "A777", (int) (Math.random() * 10)));
        courseModels[1].addAll(models_2);*/


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
