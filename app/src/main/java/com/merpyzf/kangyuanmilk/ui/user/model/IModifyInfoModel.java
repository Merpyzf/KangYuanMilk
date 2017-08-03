package com.merpyzf.kangyuanmilk.ui.user.model;

import com.merpyzf.kangyuanmilk.ui.base.User;
import com.merpyzf.kangyuanmilk.ui.user.bean.MessageBean;

import io.reactivex.Observable;

/**
 * Created by wangke on 2017-08-3.
 */

public interface IModifyInfoModel {
    /**
     * 更新用户信息
     * @param user
     */
    Observable<MessageBean> updateUserInfo(User user);
}
