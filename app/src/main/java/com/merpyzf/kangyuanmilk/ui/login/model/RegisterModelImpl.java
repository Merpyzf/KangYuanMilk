package com.merpyzf.kangyuanmilk.ui.login.model;

import com.merpyzf.kangyuanmilk.ui.login.bean.RegisterBean;
import com.merpyzf.kangyuanmilk.ui.user.bean.User;
import com.merpyzf.kangyuanmilk.utils.http.RetrofitFactory;

import io.reactivex.Observable;

/**
 * Created by wangke on 2017-07-25.
 */

public class RegisterModelImpl implements IRegisterModel {

    /**
     * 新用户注册
     *
     * @param user user对象
     * @return Observable<RegisterBean>
     */
    @Override
    public Observable<RegisterBean> Register(User user) {
        return RetrofitFactory.getServiceInstance()
                .register(user);

    }

    /**
     * 检查用户名是否重复
     *
     * @param user user对象
     * @return Observable<RegisterBean>
     */
    @Override
    public Observable<RegisterBean> checkUserRepeat(User user) {

        return RetrofitFactory.getServiceInstance()
                .checkRepeat(user);
    }


}
