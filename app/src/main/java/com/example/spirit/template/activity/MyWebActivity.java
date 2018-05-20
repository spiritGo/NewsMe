package com.example.spirit.template.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.spirit.template.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MyWebActivity extends Activity {

    @InjectView(R.id.pb_bar)
    ProgressBar pbBar;
    @InjectView(R.id.webView)
    WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.inject(this);
        String url = getIntent().getStringExtra("url");
        webView.loadUrl(url);
        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDefaultTextEncodingName("utf-8");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress==100){
                    pbBar.setVisibility(View.GONE);
                }else {
                    pbBar.setVisibility(View.VISIBLE);
                    pbBar.setProgress(newProgress);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
        webView = null;
    }
}
