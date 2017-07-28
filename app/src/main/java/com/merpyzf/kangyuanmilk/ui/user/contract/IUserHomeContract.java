package com.merpyzf.kangyuanmilk.ui.user.contract;

import com.merpyzf.kangyuanmilk.ui.base.IBasePresenter;
import com.merpyzf.kangyuanmilk.ui.base.IBaseView;
import com.merpyzf.kangyuanmilk.ui.base.User;
import com.merpyzf.kangyuanmilk.ui.user.UserHomeActivity;

/**
 * Created by Administrator on 2017-07-27.
 */

public interface IUserHomeContract {


    interface IUserHomeView extends IBaseView{

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
         *
         * @param progress 图片上传的进度
         */
        void currentUpLoadImageProgress(float progress);

        /**
         * 图像上传失败
         */
        void uploadFailed();

        /**
         * 图像上传成功
         */
        void upLoadSuccess(String key);


        /**
         * 展示用户信息
         *
         * @param user 存储在数据库中的用户信息
         */
        void showUserInfo(User user);


    }


    interface IUserHomePresenter extends IBasePresenter<IUserHomeView>{
        /**
         * 上传头像
         *
         * @param imagePath 要上传的文件的路径
         */
        void upLoadAvater(UserHomeActivity context,String imagePath);

        /**
         * 保存头像
         * @param fileName
         */
        void saveAvater(UserHomeActivity context, String fileName);

        /**
         * 获取用户信息
         */
        void getUserInfo();
    }


}
