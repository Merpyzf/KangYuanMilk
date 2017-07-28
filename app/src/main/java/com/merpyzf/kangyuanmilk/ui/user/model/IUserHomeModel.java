package com.merpyzf.kangyuanmilk.ui.user.model;

import com.merpyzf.kangyuanmilk.ui.base.User;
import com.merpyzf.kangyuanmilk.ui.user.bean.UploadAvaterBean;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017-07-27.
 */

public interface IUserHomeModel {
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
    Observable<UploadAvaterBean> uploadAvater(User user);
}
