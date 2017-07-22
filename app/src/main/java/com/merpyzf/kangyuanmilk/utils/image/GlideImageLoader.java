package com.merpyzf.kangyuanmilk.utils.image;

import android.support.design.widget.AppBarLayout;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.target.ViewTarget;

import java.io.File;

/**
 * Created by wangke on 2017-07-18.
 *
 * Glide图片加载框架二次封装
 */

public  class  GlideImageLoader  {

    /**
     * 加载网络图片
     * @param imageView ImageView
     * @param url  待加载的url
     * @param options 图片加载所需参数
     */
    public static void  showImage(ImageView imageView, String url, ImageLoaderOptions options) {

        DrawableTypeRequest dtr = Glide.with(imageView.getContext())
                .load(url);
        loadOptions(dtr,options);
        dtr.into(imageView);

    }



    /**
     * 加载本地图片资源
     * @param imageView ImageView
     * @param drawable  本地资源
     * @param options 图片加载所需参数
     */
    public static void showImage(ImageView imageView, int drawable, ImageLoaderOptions options) {

        DrawableTypeRequest dtr = Glide.with(imageView.getContext())
                .load(drawable);

        loadOptions(dtr,options);
        dtr.into(imageView);

    }

    /**
     * 加载file类型的图片
     * @param imageView ImageView
     * @param file  待加载的本地文件
     * @param options 图片加载所需参数
     */
    public static void showImage(ImageView imageView, File file, ImageLoaderOptions options) {

        DrawableTypeRequest dtr = Glide.with(imageView.getContext())
                .load(file);
        loadOptions(dtr,options);
        dtr.into(imageView);

    }






    /**
     * 装载Glide的设置项
     * @param dtr
     * @param options
     */
    private static void loadOptions(DrawableTypeRequest dtr,ImageLoaderOptions options) {
        if(options == null) return;


        if(options.getPlaceHolder()!= -1) {
            dtr.placeholder(options.getPlaceHolder());
        }
        if(options.getSize()!=null){
            dtr.override(options.getSize().reWidth,options.getSize().reHeight);
        }
        if(options.getErrorDrawable()!=-1){
            dtr.error(options.getErrorDrawable());
        }
        if (options.isCenterCrop()){
            dtr.centerCrop();
        }
        if(options.isFitCenter()){
            dtr.fitCenter();
        }
        if(options.isCrossFade()){
            dtr.crossFade();
        }
        if(options.isSkipMemoryCache()){
            dtr.skipMemoryCache(true);
        }

        if(options.getAnimator()!=null){
            dtr.animate(options.getAnimator());
        }
    }



    /*
    * 使用:
    *      ImageLoaderOptions.Bulider bulider = new ImageLoaderOptions.Bulider();
        ImageLoaderOptions options = bulider.isCenterCrop(true)
                .size(new ImageLoaderOptions.ImageReSize(100, 100))
                .build();
        GlideImageLoader.showImage(imageView,"https://user-gold-cdn.xitu.io/2017/6/26/f004a43bd15451f54759a32e31e41089",options);

    *
    * */
}
