package com.merpyzf.kangyuanmilk.ui.welcom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.BaseActivity;
import com.merpyzf.kangyuanmilk.utils.image.GlideImageLoader;
import com.merpyzf.kangyuanmilk.utils.image.ImageLoaderOptions;

import butterknife.BindView;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.iv_splash)
    ImageView iv_splash;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initWidget() {


       GlideImageLoader.showImage(iv_splash,R.drawable.ic_default,null);


    }

    @Override
    public void initEvent() {

    }

    @Override
    protected void initData() {

    }
}
