package com.example.mrz.newproject.model.dao;

import okhttp3.Request;

/**
 * Created by Mr.z on 2017/9/19.
 */

public class QueryScoreDao {

    public void getScore(String url){

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
    }
}
