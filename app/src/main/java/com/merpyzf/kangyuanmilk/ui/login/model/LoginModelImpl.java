package com.merpyzf.kangyuanmilk.ui.login.model;

import com.merpyzf.kangyuanmilk.common.api.KangYuanApi;
import com.merpyzf.kangyuanmilk.ui.login.bean.LoginBean;
import com.merpyzf.kangyuanmilk.ui.base.User;
import com.merpyzf.kangyuanmilk.ui.user.bean.MessageBean;
import com.merpyzf.kangyuanmilk.utils.http.RetrofitFactory;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017-07-24.
 */

public class LoginModelImpl implements ILoginModel {


    @Override
    public Observable<LoginBean> login(String username, String pwd) {


        KangYuanApi serviceInstance = RetrofitFactory.getServiceInstance();

        User user = new User();

        user.setUser_name(username);
        user.setUser_pwd(pwd);

        return serviceInstance.login(user).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread());
    }
    @Override
    public Observable<MessageBean> userAvater(User user) {


        return RetrofitFactory.getServiceInstance().getUserAvater(user)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
    }
}
