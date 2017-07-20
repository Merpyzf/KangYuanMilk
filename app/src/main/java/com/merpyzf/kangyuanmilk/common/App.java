package com.merpyzf.kangyuanmilk.common;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.merpyzf.kangyuanmilk.utils.LogHelper;

/**
 * Created by Administrator on 2017-07-20.
 */

public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        LogHelper.i("App中的额onCreate方法执行了");
    }

    public static void showToast(String msg){

        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();

    }

}
