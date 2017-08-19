package com.merpyzf.kangyuanmilk.ui.login.model;

import com.merpyzf.kangyuanmilk.ui.login.bean.RegisterBean;
import com.merpyzf.kangyuanmilk.ui.user.bean.User;

import io.reactivex.Observable;

/**
 * Created by wangke on 2017-07-25.
 */

public interface IRegisterModel {


    Observable<RegisterBean> Register(User user);

    Observable<RegisterBean> checkUserRepeat(User user);
}
