package com.rssreader.wxf.rssreader.rssreader.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import com.rssreader.wxf.rssreader.R;

public class WebView extends AppCompatActivity {

    private android.webkit.WebView wb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        wb = (android.webkit.WebView) findViewById(R.id.wb);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");

        Log.d("url",url);
        WebSettings settings = wb.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        settings.setJavaScriptEnabled(true);
        //设置可以访问文件
        settings.setAllowFileAccess(true);
        //设置支持缩放
        settings.setBuiltInZoomControls(true);
        //加载需要显示的网页
        wb.loadUrl(url);
        //设置Web视图
        wb.setWebViewClient(new WebViewClient());

    }
}
