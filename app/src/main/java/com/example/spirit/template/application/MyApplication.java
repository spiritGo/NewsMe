package com.example.spirit.template.application;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.example.spirit.template.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

public class MyApplication extends Application {

    private static Context context;
    private static int mainThreadId;
    private static android.os.Handler handler;

    static {
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull
                    RefreshLayout layout) {
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });

        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull
                    RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
                return new ClassicsHeader(context);
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        mainThreadId = android.os.Process.myTid();
        handler = new Handler();
    }

    public static Context getContext() {
        return context;
    }

    public static int getMainThreadId() {
        return mainThreadId;
    }

    public static Handler getHandler() {
        return handler;
    }
}
