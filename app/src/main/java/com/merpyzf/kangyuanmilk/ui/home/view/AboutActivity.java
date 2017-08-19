package com.merpyzf.kangyuanmilk.ui.home.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.BaseActivity;
import com.merpyzf.kangyuanmilk.utils.LogHelper;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AboutActivity extends BaseActivity {

    @BindView(R.id.iv_milkcow)
    ImageView mIvMilkCow;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }


    public static void showAction(Context context) {

        context.startActivity(new Intent(context, AboutActivity.class));

    }

    @Override
    public void initWidget() {

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.title_about_activity_text);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //加载gif动画
        Glide.with(this)
                .load(R.drawable.test_gif)
                .listener(new RequestListener<Integer, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, Integer model,
                                               Target<GlideDrawable> target,
                                               boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, Integer model,
                                                   Target<GlideDrawable> target,
                                                   boolean isFromMemoryCache,
                                                   boolean isFirstResource) {

                        Observable.just(resource)
                                .flatMap(glideDrawable -> {
                                    int duration = 0;
                                    try {
                                        GifDrawable gifDrawable = (GifDrawable) glideDrawable;
                                        GifDecoder decoder = gifDrawable.getDecoder();
                                        //获取Gif图的帧数
                                        for (int i = 0; i < gifDrawable.getFrameCount(); i++) {

                                            //计算播放完整个gif动画所需要的时间（毫秒）
                                            duration += decoder.getDelay(i);

                                            LogHelper.i("第 " + duration + " 帧");

                                        }


                                    } catch (Throwable e) {

                                    }

                                    //计算出来的时间不准确
                                    return Observable.just("completed")
                                            .delay(duration + 4000, TimeUnit.MILLISECONDS);

                                })
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(o -> {

                                    LogHelper.i("gif动画加载完成");

                                });

                        return false;
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mIvMilkCow);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                onBackPressed();

                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initEvent() {

    }

    @Override
    protected void initData() {

    }


}
