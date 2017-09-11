package com.merpyzf.kangyuanmilk.ui.home.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.App;
import com.merpyzf.kangyuanmilk.common.BaseActivity;
import com.merpyzf.kangyuanmilk.common.data.Common;
import com.merpyzf.kangyuanmilk.ui.home.contract.IContentContract;
import com.merpyzf.kangyuanmilk.ui.home.presenter.ContentPresenterImpl;
import com.merpyzf.kangyuanmilk.utils.LogHelper;

import butterknife.BindView;

public class ContentActivity extends BaseActivity implements IContentContract.IContentView {

    @BindView(R.id.iv_bg)
    ImageView mIvBg;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapToolbar;

    @BindView(R.id.webView)
    WebView mWebView;

    @BindView(R.id.CoordinatorLayout)
    CoordinatorLayout mCoordinatorLayout;

    @BindView(R.id.toolbar_title)
    TextView mToolBarTitle;

    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    private int mActivityId;
    private IContentContract.IContentPresenter mPresenter = new ContentPresenterImpl(this);


    public static void showAction(Bundle bundle, Context context) {

        Intent intent = new Intent(context, ContentActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);

    }


    @Override
    protected boolean initArgs(Bundle bundle) {

        mActivityId = (int) bundle.get("activity_id");
        LogHelper.i("ActivityId==>" + mActivityId);

        return super.initArgs(bundle);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_content;
    }

    @Override
    public void initWidget() {
        mCollapToolbar.setTitleEnabled(false);
        mPresenter.attachView(this);


        setSupportActionBar(mToolBar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    protected void initData() {

        mPresenter.getActivityContent(mActivityId);

    }

    @Override
    public void showAppbarContent(String title, String image) {

        LogHelper.i("标题==>" + title);

        getSupportActionBar().setTitle(title);
        Glide.with(this)
                .load(Common.OUTSIDE_CHAIN + image)
                .centerCrop()
                .into(mIvBg);


    }

    @Override
    public void showWebContent(String html) {

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 开启DOM storage API 功能
        mWebView.getSettings().setDomStorageEnabled(true);
        // 开启database storage API功能
        mWebView.getSettings().setDatabaseEnabled(true);
        // 开启Application Cache功能
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);


    }

    @Override
    public void showLoadingDialog() {

    }

    @Override
    public void cancelLoadingDialog() {

    }

    @Override
    public void showErrorMsg(String errorMsg) {
        App.showToast(errorMsg);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()){


            case android.R.id.home:

                onBackPressed();

                break;


        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {

        mPresenter.detachView();

        super.onDestroy();
    }
}
