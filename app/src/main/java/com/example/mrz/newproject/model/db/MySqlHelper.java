package com.example.mrz.newproject.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Mr.z on 2017/9/12.
 */

public class MySqlHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String SWORD="SWORD";
    //三个不同参数的构造函数
    //带全部参数的构造函数，此构造函数必不可少
    public MySqlHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version) {
        super(context, name, factory, version);

    }
    //带两个参数的构造函数，调用的其实是带三个参数的构造函数
    public MySqlHelper(Context context,String name){
        this(context,name,VERSION);
    }
    //带三个参数的构造函数，调用的是带所有参数的构造函数
    public MySqlHelper(Context context, String name, int version){
        this(context, name,null,version);
    }


    //创建数据库
    public void onCreate(SQLiteDatabase db) {

        /*//创建数据库sql语句
        String sql = "create table userInfo(id int,name varchar(20))";
        //执行创建数据库操作
        db.execSQL(sql);*/

        //创建信息键值对
        String sql = "create table InfoKvp(id INTEGER PRIMARY KEY AUTOINCREMENT,infoKey varchar(20),infoValue varchar(30))";
        //执行创建数据库操作
        db.execSQL(sql);

        //创建课程表
        sql = "create table Course(monday varchar(50),Tuesday varchar(50),Wednesday varchar(50),Thursday varchar(50), Friday varchar(50),Saturday varchar(50),sunday varchar(50))";
        //执行创建数据库操作
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }
}
