package com.merpyzf.kangyuanmilk.ui.home.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.BaseActivity;

import butterknife.BindView;

/**
 * 商品详情
 */
public class GoodsDetailActivity extends BaseActivity {

    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public static void showAction(Context context) {

        context.startActivity(new Intent(context, GoodsDetailActivity.class));

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_goods_detail;
    }

    @Override
    public void initWidget() {

        mWebView.loadUrl("https://m.jd.com/");
        mWebView.getSettings().setJavaScriptEnabled(true);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("产品详情");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_detailgoods, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            //购物车
            case R.id.action_shopping_cart:

                break;


            case android.R.id.home:

                onBackPressed();

                break;


        }



        return true;
    }
}
