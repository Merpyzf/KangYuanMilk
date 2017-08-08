package com.merpyzf.kangyuanmilk.ui.user.model;

import com.merpyzf.kangyuanmilk.ui.user.bean.Address;
import com.merpyzf.kangyuanmilk.ui.user.bean.MessageBean;

import io.reactivex.Observable;

/**
 * Created by wangke on 2017-08-07.
 */

public interface IModifyAddressModel {
    /**
     * 保存地址
     * @param address 地址
     */
    Observable<MessageBean> addAddress(Address address);

}
