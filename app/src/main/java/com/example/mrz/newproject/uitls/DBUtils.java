package com.example.mrz.newproject.uitls;

import android.content.Context;

import com.example.mrz.newproject.model.db.MySqlHelper;

/**
 * Created by Mr.z on 2017/9/12.
 */

public class DBUtils {

    private Context ctx;
    private MySqlHelper sqlHelper ;
    private DBUtils(Context ctx){
        this.ctx = ctx;
        sqlHelper = new MySqlHelper(ctx, "User.db", 1);
    }
    private static DBUtils instance;
    public static synchronized MySqlHelper getInstance(Context ctx) {

        if (instance == null) {
            instance = new DBUtils(ctx);
        }
        return instance.sqlHelper;
    }
}
