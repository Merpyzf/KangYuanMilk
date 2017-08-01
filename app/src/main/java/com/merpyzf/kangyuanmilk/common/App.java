package com.merpyzf.kangyuanmilk.common;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.mob.MobSDK;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by wangke on 2017-07-20.
 */

public class App extends Application {

    private static Context context;
    private static App instance;

    private RefWatcher mRefWatcher;

    protected String a() {
        return null;
    }

    protected String b() {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mRefWatcher = LeakCanary.install(this);
        MobSDK.init(this, this.a(), this.b());
        context = getApplicationContext();

    }

    /**
     * 返回上下文对象
     * @return context
     */
    public static Context getContext(){

        return context;
    }


    public static App getInstance() {
        return instance;
    }

    public static RefWatcher getRefWatcher() {
        return getInstance().mRefWatcher;
    }

    /**
     * 在被调用的线程打印Toast
     * @param msg
     */
    public static void showToast(String msg) {


        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

    }

    /**
     * 强制在主线程中打印Toast
     * @param context
     * @param msg
     */
    public static void showToast(Activity context, String msg) {

        context.runOnUiThread(() -> {

            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

        });


    }



}
