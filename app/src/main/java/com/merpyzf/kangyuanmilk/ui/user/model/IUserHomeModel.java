package com.merpyzf.kangyuanmilk.ui.user.model;

import com.merpyzf.kangyuanmilk.ui.login.bean.LoginBean;

/**
 * Created by Administrator on 2017-07-27.
 */

public interface IUserHomeModel {
    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    LoginBean.ResponseBean.UserBean getUserInfo();

    /**
     * 上传图片
     *
     * @param imagePath 图片路径
     */
    void upLoadImage(String imagePath);
}
