package com.merpyzf.kangyuanmilk.ui.user.model;

import com.merpyzf.kangyuanmilk.ui.base.User;
import com.merpyzf.kangyuanmilk.ui.user.bean.Address;
import com.merpyzf.kangyuanmilk.ui.user.bean.MessageBean;
import com.merpyzf.kangyuanmilk.ui.user.bean.UserAddressBean;
import com.merpyzf.kangyuanmilk.utils.http.RetrofitFactory;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wangke on 2017-08-08.
 */

public class UserAddressModelImpl implements IUserAddressModel {

    @Override
    public Observable<UserAddressBean> getUserAddress(User user) {

        return RetrofitFactory.getServiceInstance()
                .getUserAddress(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<MessageBean> setAddressAsDefault(Address address) {

        return RetrofitFactory.getServiceInstance()
                .setDefaultAddress(address)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<MessageBean> deleteAddress(Address address) {

        return RetrofitFactory.getServiceInstance()
                .deleteAddress(address)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }



}
