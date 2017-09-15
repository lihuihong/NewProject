package com.example.mrz.newproject.uitls;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 图片下载工具类
 *
 * Created by Mr.z on 2017/9/14.
 */

public class ImageDownload {


    public static Bitmap download(String url) throws IOException {
        URL myFileUrl;
        Bitmap bitmap;
        myFileUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
        conn.setDoInput(true);
        conn.connect();
        InputStream is = conn.getInputStream();
        bitmap = BitmapFactory.decodeStream(is);
        is.close();

        return bitmap;
    }

}
