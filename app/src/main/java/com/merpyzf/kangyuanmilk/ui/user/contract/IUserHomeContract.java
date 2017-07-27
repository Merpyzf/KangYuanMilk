package com.merpyzf.kangyuanmilk.ui.user.contract;

import com.merpyzf.kangyuanmilk.ui.login.bean.LoginBean;

/**
 * Created by Administrator on 2017-07-27.
 */

public interface IUserHomeContract {


    interface IUserHomeView{

        /**
         * AppBar中头像的动画,根据手指的滑动进行缩小和隐藏
         */
        void initAvaterAnim();

        /**
         * 显示点击头像的dialog
         */
        void showAvaterDialog();

        /**
         * 隐藏dialog
         */
        void dissmissAvaterDialog();


        /**
         * 弹出一个图片选择器
         */
        void showGallery();


        /**
         * 当前上传图片的一个进度
         * @param progress 图片上传的进度
         */
        void currentUpLoadImageProgress(float progress);

        /**
         * 展示用户信息
         * @param user 存储在数据库中的用户信息
         */
        void showUserInfo(LoginBean.ResponseBean.UserBean user);





    }


    interface IUserHomePresenter{
        /**
         * 上传头像
         * @param imagePath 要上传的文件的路径
         */
        void upLoadImage(String imagePath);

        /**
         * 获取用户信息
         */
        void getUserInfo();
    }



}
