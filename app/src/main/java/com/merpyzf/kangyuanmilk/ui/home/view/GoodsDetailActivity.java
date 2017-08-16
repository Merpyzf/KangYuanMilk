package com.merpyzf.kangyuanmilk.ui.home.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.merpyzf.kangyuanmilk.R;

/**
 * 商品详情
 */
public class GoodsDetailActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);

        mWebView = (WebView) findViewById(R.id.webView);

        mWebView.loadUrl("https://m.jd.com/");
        mWebView.getSettings().setJavaScriptEnabled(true);

    }



}
