package com.example.spirit.template.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.example.spirit.template.interfaces.BitmapCallBack;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtil {
    private static HttpUtil httpUtil;
    final private static OkHttpClient client = new OkHttpClient();
    final private static Gson gson = new Gson();
    private HttpURLConnection connection;
    private Bitmap bitmap;

    public static HttpUtil getHttpUtil() {
        if (httpUtil == null) {
            synchronized (HttpUtil.class) {
                if (httpUtil == null) {
                    httpUtil = new HttpUtil();
                }
            }
        }
        return httpUtil;
    }

    public synchronized void setBitmap(final String url, final ImageView imageView) {
        final Request request = new Request.Builder().url(url).build();
        if (imageView.getTag().equals(url)){
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println(e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    InputStream inputStream = response.body().byteStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);

                    ConstanceUtil.runOnUI(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(bitmap);
                        }
                    });

                }
            });
        }
    }

    public <T> void getData(String url, final Class<T> tClass) {
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                T t = gson.fromJson(data, tClass);
                EventBus.getDefault().post(t);
            }
        });
    }
}
