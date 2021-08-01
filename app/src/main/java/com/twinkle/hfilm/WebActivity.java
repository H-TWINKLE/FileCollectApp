package com.twinkle.hfilm;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebViewClient;

public class WebActivity extends AppCompatActivity {

    private com.tencent.smtt.sdk.WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow(); //隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏状态栏  //定义全屏参数
        int flag= WindowManager.LayoutParams.FLAG_FULLSCREEN; //设置当前窗体为全屏显示
        window.setFlags(flag, flag);
        setContentView(R.layout.activity_web);
       init();
       apply();
    }

    private  void  init(){

        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        web = (com.tencent.smtt.sdk.WebView) findViewById(R.id.web);

    }


    private  void apply(){

        String url = getIntent().getStringExtra("url");
        Log.i("url",url);
        web.loadUrl(url);
        web.setDrawingCacheEnabled(true);
        com.tencent.smtt.sdk.WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(com.tencent.smtt.sdk.WebView webView, String s) {
                return super.shouldOverrideUrlLoading(webView, s);
            }
        });



    }
}
