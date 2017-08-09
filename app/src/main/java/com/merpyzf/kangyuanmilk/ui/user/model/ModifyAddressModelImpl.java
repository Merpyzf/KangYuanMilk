package com.merpyzf.kangyuanmilk.ui.user.model;

import com.merpyzf.kangyuanmilk.ui.user.bean.Address;
import com.merpyzf.kangyuanmilk.ui.user.bean.MessageBean;
import com.merpyzf.kangyuanmilk.utils.http.RetrofitFactory;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wangke on 2017-08-07.
 */

public class ModifyAddressModelImpl implements IModifyAddressModel {
    /**
     * 给用户添加一个地址
     *
     * @param address 地址
     */
    @Override
    public Observable<MessageBean> addAddress(Address address) {

        return RetrofitFactory.getServiceInstance()
                .addAddress(address)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<MessageBean> updateAddress(Address address) {

        return RetrofitFactory.getServiceInstance()
                .updateAddress(address)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
