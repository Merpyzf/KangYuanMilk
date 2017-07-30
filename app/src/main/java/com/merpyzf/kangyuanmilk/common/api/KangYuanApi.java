package com.merpyzf.kangyuanmilk.common.api;

import com.merpyzf.kangyuanmilk.ui.base.User;
import com.merpyzf.kangyuanmilk.ui.login.bean.LoginBean;
import com.merpyzf.kangyuanmilk.ui.login.bean.RegisterBean;
import com.merpyzf.kangyuanmilk.ui.user.bean.MessageBean;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017-07-17.
 */

public interface KangYuanApi {

    String BASE_URL = "http://115.159.127.212:8080/KangYuanMilk/";
//    String BASE_URL = "http://192.168.0.57:8089/";

    /**
     * 注册前检查用户名是否重复
     * @param user
     * @return
     */
    @POST("user/checkName")
    Observable<RegisterBean> checkRepeat(@Body User user);
    /**
     * 注册
     * @param user
     * @return
     */
    @POST("user/register")
    Observable<RegisterBean> register(@Body User user);
    /**
     * 登录
     * @param user
     * @return
     */
    @POST("user/login")
    Observable<LoginBean> login(@Body User user);

    /**
     * 更新用户信息
     * @return
     */
    @POST("user/update")
    Observable<MessageBean> upLoadAvater(@Body User user);

    @POST("user/getHead")
    Observable<MessageBean> getUserAvater(@Body User user);

}
