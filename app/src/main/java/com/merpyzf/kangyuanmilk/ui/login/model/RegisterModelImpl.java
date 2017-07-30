package com.merpyzf.kangyuanmilk.ui.login.model;

import com.merpyzf.kangyuanmilk.ui.login.bean.RegisterBean;
import com.merpyzf.kangyuanmilk.ui.base.User;
import com.merpyzf.kangyuanmilk.utils.http.RetrofitFactory;

import io.reactivex.Observable;

/**
 * Created by wangke on 2017-07-25.
 */

public class RegisterModelImpl implements IRegisterModel{

    @Override
    public Observable<RegisterBean> Register(User user) {
        return RetrofitFactory.getServiceInstance()
                .register(user);

    }

    @Override
    public Observable<RegisterBean> checkUserRepeat( User user) {

        return  RetrofitFactory.getServiceInstance()
                .checkRepeat(user);
    }


}
