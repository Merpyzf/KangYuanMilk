package com.merpyzf.kangyuanmilk.common.api;

import com.merpyzf.kangyuanmilk.ui.home.bean.CategoryBean;
import com.merpyzf.kangyuanmilk.ui.home.bean.GoodsBean;
import com.merpyzf.kangyuanmilk.ui.home.bean.HomeBean;
import com.merpyzf.kangyuanmilk.ui.home.bean.QueryKey;
import com.merpyzf.kangyuanmilk.ui.home.bean.SearchBean;
import com.merpyzf.kangyuanmilk.ui.user.bean.User;
import com.merpyzf.kangyuanmilk.ui.home.bean.Meizi;
import com.merpyzf.kangyuanmilk.ui.login.bean.LoginBean;
import com.merpyzf.kangyuanmilk.ui.login.bean.RegisterBean;
import com.merpyzf.kangyuanmilk.ui.user.bean.Address;
import com.merpyzf.kangyuanmilk.ui.user.bean.MessageBean;
import com.merpyzf.kangyuanmilk.ui.user.bean.UserAddressBean;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2017-07-17.
 */

public interface KangYuanApi {

    String BASE_URL = "http://115.159.127.212:80/KangYuanMilk/android/";
//    String BASE_URL = "http://192.168.0.54:8089/android/";

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
     *
     * @param user
     * @return
     */
    @POST("user/getHead")
    Observable<MessageBean> getUserAvater(@Body User user);

    /**
     * 获取当前用户的所有地址信息
     *
     * @param user
     * @return
     */
    @POST("user/getAddress")
    Observable<UserAddressBean> getUserAddress(@Body User user);

    /**
     * 删除地址
     *
     * @param address
     * @return
     */
    @POST("user/deleteAddress")
    Observable<MessageBean> deleteAddress(@Body Address address);

    /**
     * 设置默认地址
     *
     * @param address
     * @return
     */
    @POST("user/setDefaultAddress")
    Observable<MessageBean> setDefaultAddress(@Body Address address);

    /**
     * 添加地址并设置成默认
     *
     * @param address
     * @return
     */
    @POST("user/setAddress")
    Observable<MessageBean> addAddress(@Body Address address);

    /**
     * 更新地址
     *
     * @param address
     * @return
     */
    @POST("user/changeAddress")
    Observable<MessageBean> updateAddress(@Body Address address);


    @GET("http://gank.io/api/data/福利/{num}/{page}")
    Observable<Meizi> getMeizi(@Path("page") String page, @Path("num") String num);

    /**
     * 商品搜索
     *
     * @param queryKey
     * @return
     */
    @POST("shop/search")
    Observable<SearchBean> searchGoods(@Body QueryKey queryKey);

    @POST("shop/getIndex")
    Observable<HomeBean> getHomePageData();

    /**
     * 获取商品分类
     *
     * @return
     */
    @POST("shop/getMilkCategory")
    Observable<CategoryBean> getGooodsCategory();

    /**
     * 根据分类id获取商品列表
     *
     * @param id   商品分类id
     * @param page 要显示的页
     * @param num  每一个页面显示商品的个数
     * @return
     */
    @POST("shop/getMilkByCategory/{id}/{page}/{num}")
    Observable<GoodsBean> getGoodsById(@Path("id") String id, @Path("page") String page, @Path("num") String num);


}
