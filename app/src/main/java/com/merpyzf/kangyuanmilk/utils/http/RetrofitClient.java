package com.merpyzf.kangyuanmilk.utils.http;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.merpyzf.kangyuanmilk.common.api.KangYuanApi;

import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wangke on 2017-07-17.
 * RetrofitClient封装创建过程
 */

public class RetrofitClient {

    KangYuanApi service = null;

    public RetrofitClient(){

        OkHttpClient.Builder bulider = new OkHttpClient.Builder();

        if(RetrofitFactory.isDebug){

            //添加logging日志打印功能
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            bulider.addInterceptor(logging);

        }

        //设置连接超时时间
        bulider.connectTimeout(10, TimeUnit.SECONDS);
        //设置读取超时时间
        bulider.readTimeout(10,TimeUnit.SECONDS);

        OkHttpClient okHttpClient = bulider.build();


        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();

        Retrofit retrofit = retrofitBuilder.baseUrl(KangYuanApi.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(KangYuanApi.class);
    }



    public KangYuanApi getService(){

       return service;
    }




}
