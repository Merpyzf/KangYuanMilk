package com.merpyzf.kangyuanmilk.ui.login.model;

import com.merpyzf.kangyuanmilk.ui.login.bean.LoginBean;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017-07-22.
 */

public interface ILoginModel {

    /**
     * 请求 /user/login 登录
     * @return
     */
    Observable<LoginBean> login(String user, String pwd);



}
