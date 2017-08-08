package com.merpyzf.kangyuanmilk.ui.user.model;


import com.merpyzf.kangyuanmilk.ui.base.User;
import com.merpyzf.kangyuanmilk.ui.user.bean.UserAddressBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by wangke on 2017-08-08.
 */

public interface IUserAddressModel {
    /**
     * 获取用户添加的所有地址
     * @param user
     * @return
     */
    Observable<UserAddressBean> getUserAddress(User user);

}
