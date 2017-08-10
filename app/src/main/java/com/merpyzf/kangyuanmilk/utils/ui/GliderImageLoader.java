package com.merpyzf.kangyuanmilk.utils.ui;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.merpyzf.kangyuanmilk.R;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by wangke on 2017-08-10.
 * <p>
 * 图片加载器
 */

public class GliderImageLoader extends ImageLoader {

    /**
     * 显示图像到ImageView
     *
     * @param context   上下文
     * @param path      文件路径
     * @param imageView ImageView
     */
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {


        Glide.with(context)
                .load((String) path)
                .centerCrop()
                .placeholder(R.drawable.ic_avater_default)
                .into(imageView);

    }
}
