package com.merpyzf.kangyuanmilk.common.api;

import com.merpyzf.kangyuanmilk.common.bean.User;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017-07-17.
 */

public interface KangYuanApi {

    String BASE_URL = "http://192.168.0.57:8089/";

    @POST("user/register")
    Observable<ResponseBody> getJson(@Body com.merpyzf.kangyuanmilk.ui.login.bean.User user);

}
