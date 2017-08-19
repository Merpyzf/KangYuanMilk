package com.merpyzf.kangyuanmilk.ui.user.model;

import com.merpyzf.kangyuanmilk.ui.user.bean.User;
import com.merpyzf.kangyuanmilk.ui.user.bean.MessageBean;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017-07-27.
 */

public interface IUserInfoModel {
    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    User getUserInfo();

    /**
     * 更新用户信息
     */
    void updateUserInfo(User user);


    /**
     * 上传用户头像信息到服务器
     * @param user
     * @return
     */
    Observable<MessageBean> uploadAvater(User user);
}
