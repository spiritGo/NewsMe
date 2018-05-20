package com.example.spirit.template.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetWorkUtil {

    public static final int TYPE_MOBILE = 1;
    public static final int TYPE_WIFI = 2;
    public static final int TYPE_NONE = -1;

    private NetWorkUtil() {}

    public static int getNetState(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService
                (Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info != null && info.isAvailable()) {
                if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                    return TYPE_MOBILE;
                } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                    return TYPE_WIFI;
                }
            }
        }
        return TYPE_NONE;
    }
}
