package com.example.spirit.template.utils;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Process;

import com.example.spirit.template.R;
import com.example.spirit.template.application.MyApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConstanceUtil {
    private static HashMap<String, Integer> bottonTitleMap;

    public static Context getContext() {
        return MyApplication.getContext();
    }

    public static HashMap<String, Integer> getBottomTitleList() {
        if (bottonTitleMap == null) {

            bottonTitleMap = new HashMap<>();
            bottonTitleMap.put("国际新闻", R.drawable.international_selector);
            bottonTitleMap.put("体育新闻", R.drawable.pe_selector);
            bottonTitleMap.put("创业新闻", R.drawable.entrepreneurship_selector);
            bottonTitleMap.put("健康咨讯", R.drawable.health_selector);
        }
        return bottonTitleMap;
    }

    private static Handler handler;

    public static Handler getHandler() {
        return MyApplication.getHandler();
    }

    public static boolean isRunOnUI() {
        return MyApplication.getMainThreadId() == Process.myTid();
    }

    public static void runOnUI(Runnable r) {
        if (isRunOnUI()) {
            r.run();
        } else {
            getHandler().post(r);
        }
    }


    public static boolean isApplicationInBackground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskList = am.getRunningTasks(1);
        if (taskList != null && !taskList.isEmpty()) {
            ComponentName topActivity = taskList.get(0).topActivity;
            if (topActivity != null && !topActivity.getPackageName().equals(context
                    .getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public static String isNetConnect(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService
                (Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) {
                if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    return "WIFI网络";
                } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    return "移动网络";
                }
            }
        }
        return "无网络";
    }
}
