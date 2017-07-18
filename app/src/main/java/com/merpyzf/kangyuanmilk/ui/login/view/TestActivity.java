package com.merpyzf.kangyuanmilk.ui.login.view;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.BaseActivity;
import com.merpyzf.kangyuanmilk.utils.image.GlideImageLoader;
import com.merpyzf.kangyuanmilk.utils.image.ImageLoaderOptions;

import butterknife.BindView;

/**
 * Created by Administrator on 2017-07-17.
 */

public class TestActivity extends BaseActivity {

    @BindView(R.id.iv_test)
    ImageView imageView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    public void initWidget() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    protected void initData() {

        ImageLoaderOptions.Bulider bulider = new ImageLoaderOptions.Bulider();
        ImageLoaderOptions options = bulider.isCenterCrop(true)
                .size(new ImageLoaderOptions.ImageReSize(100, 100))
                .build();
        GlideImageLoader.showImage(imageView,"https://user-gold-cdn.xitu.io/2017/6/26/f004a43bd15451f54759a32e31e41089",options);

    }
}
