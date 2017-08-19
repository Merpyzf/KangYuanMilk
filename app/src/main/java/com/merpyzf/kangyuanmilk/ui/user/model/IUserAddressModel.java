package com.merpyzf.kangyuanmilk.ui.user.model;


import com.merpyzf.kangyuanmilk.ui.user.bean.User;
import com.merpyzf.kangyuanmilk.ui.user.bean.Address;
import com.merpyzf.kangyuanmilk.ui.user.bean.MessageBean;
import com.merpyzf.kangyuanmilk.ui.user.bean.UserAddressBean;

import io.reactivex.Observable;

/**
 * Created by wangke on 2017-08-08.
 */

public interface IUserAddressModel {
    /**
     * 获取用户添加的所有地址
     *
     * @param user
     * @return
     */
    Observable<UserAddressBean> getUserAddress(User user);

    /**
     * 将地址设为默认地址
     *
     * @param address 地址
     * @return 设置结果
     */
    Observable<MessageBean> setAddressAsDefault(Address address);

    /**
     * 删除某一条地址信息
     *
     * @param address
     * @return
     */
    Observable<MessageBean> deleteAddress(Address address);


}
