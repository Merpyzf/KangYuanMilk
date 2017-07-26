package com.merpyzf.kangyuanmilk.common.api;

import com.merpyzf.kangyuanmilk.ui.login.bean.LoginBean;
import com.merpyzf.kangyuanmilk.ui.login.bean.RegisterBean;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017-07-17.
 */

public interface KangYuanApi {

    String BASE_URL = "http://115.159.127.212:8080/KangYuanMilk/";

    /**
     * 注册前检查用户名是否重复
     * @param user
     * @return
     */
    @POST("user/checkName")
    Observable<RegisterBean> checkRepeat(@Body com.merpyzf.kangyuanmilk.ui.login.bean.User user);
    /**
     * 注册
     * @param user
     * @return
     */
    @POST("user/register")
    Observable<RegisterBean> register(@Body com.merpyzf.kangyuanmilk.ui.login.bean.User user);
    /**
     * 登录
     * @param user
     * @return
     */
    @POST("user/login")
    Observable<LoginBean> login(@Body com.merpyzf.kangyuanmilk.ui.login.bean.User user);


}
