package com.merpyzf.kangyuanmilk.ui.login.model;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2017-07-22.
 */

public interface ILoginModel {

    /**
     * 请求 /user/login 登录
     * @return
     */
    Observable<ResponseBody> login(String user,String pwd);



}
