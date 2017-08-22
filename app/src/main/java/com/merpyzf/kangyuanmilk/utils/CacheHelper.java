package com.merpyzf.kangyuanmilk.utils;

/**
 * Created by wangke on 2017-08-22.
 *  缓存工具类
 */

public class CacheHelper {


    private CacheHelper(){
        throw new UnsupportedOperationException("u can't instantiante me……");
    }

    public static void cacheHomePage(String cacheJson){

        SharedPreHelper.putString("Cache","homePage",cacheJson);

    }


    public static String getHomePageCache(){


        return SharedPreHelper.getString("Cache","homePage","no_cache");

    }


}
