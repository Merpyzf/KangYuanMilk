package com.merpyzf.kangyuanmilk.utils.http;

import com.merpyzf.kangyuanmilk.common.api.KangYuanApi;

/**
 * Created by wangke on 2017-07-17.
 * 获取单例的请求Service对象
 */

public class RetrofitFactory {

   public static boolean isDebug = true;

   private static KangYuanApi sKangYuanApi = null;

   private RetrofitFactory(){};

   public static KangYuanApi getServiceInstance(){


       if(sKangYuanApi == null){

           synchronized (Object.class){

               if(sKangYuanApi ==null){

                   RetrofitClient retrofitClient = new RetrofitClient();

                   sKangYuanApi = retrofitClient.getService();

               }
           }

       }

       return sKangYuanApi;
   }

}
