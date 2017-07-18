package com.merpyzf.kangyuanmilk.utils.image;

import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;

/**
 * Created by wangke on 2017-07-18.
 * 使用Builder构造图片加载所要设置的参数
 */

public class ImageLoaderOptions {


    private int placeHolder = -1; // 当没有加载成功的时候要显示的图片
    private ImageReSize size = null; //重新设置容器的宽高
    private int errorDrawable = -1; //加载失败显示的图片
    private boolean isCenterCrop = false; //图片加载成功之后填充到容器的模式
    private boolean isFitCenter = false; //图片是否居中显示到容器
    private boolean isCrossFade = false;  //是否渐变平滑显示图片
    private boolean isSkipMemoryCache = false; //是否在内存中缓存
    private Animation animator = null; //图片的加载动画

    public ImageLoaderOptions(int placeHolder, ImageReSize size, int errorDrawable, boolean isCenterCrop, boolean isCrossFade, boolean isSkipMemoryCache, Animation animator) {
        this.placeHolder = placeHolder;
        this.size = size;
        this.errorDrawable = errorDrawable;
        this.isCenterCrop = isCenterCrop;
        this.isCrossFade = isCrossFade;
        this.isSkipMemoryCache = isSkipMemoryCache;
        this.animator = animator;
    }

    public static class ImageReSize{

        int reWidth=0;
        int reHeight=0;

        public ImageReSize(int reWidth,int reHeight){
            if (reHeight<=0){
                reHeight=0;
            }
            if (reWidth<=0) {
                reWidth=0;
            }
            this.reHeight=reHeight;
            this.reWidth=reWidth;
        }
    }

    public int getPlaceHolder() {
        return placeHolder;
    }

    public void setPlaceHolder(int placeHolder) {
        this.placeHolder = placeHolder;
    }

    public ImageReSize getSize() {
        return size;
    }

    public void setSize(ImageReSize size) {
        this.size = size;
    }

    public int getErrorDrawable() {
        return errorDrawable;
    }

    public void setErrorDrawable(int errorDrawable) {
        this.errorDrawable = errorDrawable;
    }

    public boolean isCenterCrop() {
        return isCenterCrop;
    }

    public void setCenterCrop(boolean centerCrop) {
        isCenterCrop = centerCrop;
    }

    public boolean isCrossFade() {
        return isCrossFade;
    }

    public void setCrossFade(boolean crossFade) {
        isCrossFade = crossFade;
    }

    public boolean isSkipMemoryCache() {
        return isSkipMemoryCache;
    }

    public void setSkipMemoryCache(boolean skipMemoryCache) {
        isSkipMemoryCache = skipMemoryCache;
    }

    public Animation getAnimator() {
        return animator;
    }

    public void setAnimator(Animation animator) {
        this.animator = animator;
    }

    public boolean isFitCenter() {
        return isFitCenter;
    }

    public void setFitCenter(boolean fitCenter) {
        isFitCenter = fitCenter;
    }

    public static final class Bulider{

        private int placeHolder = -1; // 当没有加载成功的时候要显示的图片
        private ImageReSize size = null; //重新设置容器的宽高
        private int errorDrawable = -1; //加载失败显示的图片
        private boolean isCenterCrop = false; //图片加载成功之后填充到容器的模式
        private boolean isFitCenter = false; //图片是否居中显示到容器
        private boolean isCrossFade = false;  //是否渐变平滑显示图片
        private boolean isSkipMemoryCache = false; //是否在内存中缓存
        private Animation animator = null; //图片的加载动画
        public Bulider(){

        }

        public Bulider placeHolder(int drawable){

            this.placeHolder = drawable;

            return this;
        }

        public Bulider size(ImageReSize size){

            this.size = size;

            return this;
        }

        public Bulider errorDrawable(int drawable){

            this.errorDrawable = drawable;

            return this;
        }

        public Bulider isCenterCrop(boolean isCenterCrop){

            this.isCenterCrop = isCenterCrop;
            return this;

        }

        public Bulider isFitCenter(boolean isFitCenter){

            this.isFitCenter = isFitCenter;
            return this;

        }

        public Bulider isCrossFade(boolean isCrossFade){

            this.isCrossFade = isCrossFade;
            return this;

        }

        public Bulider isSkipMemoryCache(boolean isSkipMemoryCache){

            this.isSkipMemoryCache = isSkipMemoryCache;
            return this;

        }

        public Bulider  animator(Animation animator){

            this.animator = animator;

            return this;
        }


        public ImageLoaderOptions build(){

            return new ImageLoaderOptions(this.placeHolder,this.size,this.errorDrawable,this.isCenterCrop,this.isCrossFade,this.isSkipMemoryCache,this.animator);

        }



    }



}
