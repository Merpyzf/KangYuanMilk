package com.merpyzf.kangyuanmilk.ui.home.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.BaseActivity;
import com.merpyzf.kangyuanmilk.utils.LogHelper;

import butterknife.BindView;

/**
 * 商品详情
 */
public class GoodsDetailActivity extends BaseActivity {

    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private int mGoodsId = 0;


    public static void showAction(Bundle bundle,Context context) {

        Intent intent = new Intent(context, GoodsDetailActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);

    }


    @Override
    protected boolean initArgs(Bundle bundle) {


        mGoodsId = (int) bundle.get("goods_id");

        LogHelper.i("商品id==>"+mGoodsId);

        return super.initArgs(bundle);
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
