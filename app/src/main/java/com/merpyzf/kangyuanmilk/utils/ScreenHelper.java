package com.merpyzf.kangyuanmilk.utils;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

import com.merpyzf.kangyuanmilk.common.App;

/**
 * Created by wangke on 2017-08-14.
 * 屏幕相关工具类
 */

public final class ScreenHelper {

    private ScreenHelper() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取屏幕的宽度(px)
     *
     * @return 屏幕宽度
     */
    public static int getScreenWidth() {

        return App.getContext().getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕的高度(px)
     *
     * @return 屏幕高度
     */
    public static int getScreenHeight() {

        return App.getContext().getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 设置屏幕为全屏
     * 需要在setContent之前调用
     *
     * @param activity
     */
    public static void setFullScreen(Activity activity) {

        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
    }


}
