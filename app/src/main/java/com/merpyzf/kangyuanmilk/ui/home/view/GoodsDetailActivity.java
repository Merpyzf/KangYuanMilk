package com.merpyzf.kangyuanmilk.ui.home.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.App;
import com.merpyzf.kangyuanmilk.common.BaseActivity;
import com.merpyzf.kangyuanmilk.common.widget.GoodsParamsPickerFragment;
import com.merpyzf.kangyuanmilk.ui.home.contract.IGoodsDetailContract;
import com.merpyzf.kangyuanmilk.ui.home.presenter.GoodsDetailPresenterImpl;
import com.merpyzf.kangyuanmilk.utils.LogHelper;
import com.merpyzf.kangyuanmilk.utils.db.dao.UserDao;
import com.merpyzf.kangyuanmilk.utils.ui.GliderImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.List;

import butterknife.BindView;

/**
 * 商品详情
 */
public class GoodsDetailActivity extends BaseActivity implements IGoodsDetailContract.IGoodsDetailView
        , View.OnClickListener {

    @BindView(R.id.webView)
    WebView mWebView;

    @BindView(R.id.banner_goods)
    Banner mBanner;

    //商品名称
    @BindView(R.id.tv_goods_name)
    TextView mTvGoodsName;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    //商品价格
    @BindView(R.id.tv_goods_price)
    TextView mTvPrice;
    //商品的规格
    @BindView(R.id.tv_goods_spec)
    TextView mTvGoodsSpec;
    //订单配送的规则
    @BindView(R.id.tv_order_spec)
    TextView mTvOrderSpec;
    //配送的地址位置
    @BindView(R.id.tv_location)
    TextView mTvLocation;
    //配送的时间
    @BindView(R.id.tv_distr_date)
    TextView mTvDistrDate;
    //每次配送的份数
    @BindView(R.id.tv_goods_num)
    TextView mTvGoodsNum;

    //订购商品的总份数
    @BindView(R.id.tv_goods_total)
    TextView mTvGoodsTotal;

    //订单的总金额
    @BindView(R.id.tv_money)
    TextView mOrderMoney;
    //加入购物车
    @BindView(R.id.btn_shoppingcart)
    Button mBtnCart;
    //购买
    @BindView(R.id.btn_buy)
    Button mBtnBuy;

    //点击选择配送的时间日期份数等
    @BindView(R.id.ll_choice)
    LinearLayout mLinearChoice;

    private int mGoodsId = 0;
    private String mGoodsImage;
    private IGoodsDetailContract.IGoodsDetailPresenter mPresenter;


    public static void showAction(Bundle bundle, Context context) {

        Intent intent = new Intent(context, GoodsDetailActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);

    }


    @Override
    protected boolean initArgs(Bundle bundle) {


        mGoodsId = (int) bundle.get("goods_id");

        LogHelper.i("商品id==>" + mGoodsId);

        return super.initArgs(bundle);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_goods_detail;
    }
    @Override
    public void initWidget() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("产品详情");

    }

    @Override
    public void initEvent() {
        super.initEvent();

        mLinearChoice.setOnClickListener(this);
        mBtnCart.setOnClickListener(this);
        mBtnBuy.setOnClickListener(this);


    }

    @Override
    protected void initData() {
        super.initData();

        mPresenter = new GoodsDetailPresenterImpl(this);
        mPresenter.attachView(this);
        mPresenter.getGoodsData(mGoodsId);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_detailgoods, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            //购物车
            case R.id.action_shopping_cart:

                break;


            case android.R.id.home:

                onBackPressed();

                break;


        }

        return true;
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
    public void fillGoodsBanner(List<String> images) {

        mGoodsImage = images.get(0);
        mBanner.isAutoPlay(true);
        mBanner.setImages(images);
        mBanner.setImageLoader(new GliderImageLoader());
        mBanner.setIndicatorGravity(BannerConfig.RIGHT);
        mBanner.setBannerAnimation(Transformer.Stack);
        mBanner.start();



    }

    @Override
    public void fillGoodsInfo(String milkName, String price, String spec, String orderSpec) {


        mTvGoodsName.setText(milkName);
        mTvPrice.setText(price);
        mTvGoodsSpec.setText(spec);
        mTvOrderSpec.setText(orderSpec);


    }

    @Override
    public void fillWebView(String html) {

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
    public void showDistrLocation() {

        if (UserDao.getInstance().isLogin()) {

            //已登录，但是如果没有设置默认地址就跳转进行默认地址的设置


            String address_content = UserDao.getInstance().getUserInfo().getAddress_content();

            mTvLocation.setText(address_content);



        } else {

            //未登录,就提示请先登录

            mTvLocation.setText("您还未登录,请先登录！");


        }


    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {

            //加入购物车
            case R.id.btn_shoppingcart:

                break;

            //购买
            case R.id.btn_buy:


                GoodsParamsPickerFragment pickerFragment = new GoodsParamsPickerFragment();


                Bundle bundle = new Bundle();

                bundle.putString("goods_name",mTvGoodsName.getText().toString());
                bundle.putString("goods_spec",mTvGoodsSpec.getText().toString());
                bundle.putString("goods_image",mGoodsImage);
                bundle.putInt("goods_price",Integer.valueOf(mTvPrice.getText().toString()));


                pickerFragment.setArguments(bundle);

                pickerFragment.show(getSupportFragmentManager(),"tag");




                break;

            //商品参数选择
            case R.id.ll_choice:

                break;


        }


    }
}
