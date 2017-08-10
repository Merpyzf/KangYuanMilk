package com.merpyzf.kangyuanmilk.common.api;

import com.merpyzf.kangyuanmilk.ui.base.User;
import com.merpyzf.kangyuanmilk.ui.login.bean.LoginBean;
import com.merpyzf.kangyuanmilk.ui.login.bean.RegisterBean;
import com.merpyzf.kangyuanmilk.ui.user.bean.Address;
import com.merpyzf.kangyuanmilk.ui.user.bean.MessageBean;
import com.merpyzf.kangyuanmilk.ui.user.bean.UserAddressBean;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017-07-17.
 */

public interface KangYuanApi {

    String BASE_URL = "http://115.159.127.212:8080/KangYuanMilk/android/";
//    String BASE_URL = "http://192.168.0.46:8089/android/";

    /**
     * 注册前检查用户名是否重复
     *
     * @param user
     * @return
     */
    @POST("user/checkName")
    Observable<RegisterBean> checkRepeat(@Body User user);

    /**
     * 注册
     *
     * @param user
     * @return
     */
    @POST("user/register")
    Observable<RegisterBean> register(@Body User user);

    /**
     * 登录
     *
     * @param user
     * @return
     */
    @POST("user/login")
    Observable<LoginBean> login(@Body User user);

    /**
     * 更新用户信息
     *
     * @return
     */
    @POST("user/update")
    Observable<MessageBean> updateUserInfo(@Body User user);

    /**
     * 获取用户头像
     * @param user
     * @return
     */
    @POST("user/getHead")
    Observable<MessageBean> getUserAvater(@Body User user);

    /**
     * 获取当前用户的所有地址信息
     * @param user
     * @return
     */
    @POST("user/getAddress")
    Observable<UserAddressBean> getUserAddress(@Body User user);

    /**
     * 删除地址
     * @param address
     * @return
     */
    @POST("user/deleteAddress")
    Observable<MessageBean> deleteAddress(@Body Address address);

    /**
     * 设置默认地址
     * @param address
     * @return
     */
    @POST("user/setDefaultAddress")
    Observable<MessageBean> setDefaultAddress(@Body Address address);

    /**
     * 添加地址并设置成默认
     * @param address
     * @return
     */
    @POST("user/setAddress")
    Observable<MessageBean> addAddress(@Body Address address);



    @POST("user/changeAddress")
    Observable<MessageBean> updateAddress(@Body Address address);

}
