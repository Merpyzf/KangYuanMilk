package com.merpyzf.kangyuanmilk.ui.user.contract;

import com.merpyzf.kangyuanmilk.ui.base.IBasePresenter;
import com.merpyzf.kangyuanmilk.ui.base.IBaseView;
import com.merpyzf.kangyuanmilk.ui.user.HomeActivity;

/**
 * Created by wangke on 2017-07-29.
 */

public interface IHomeContract {


    interface IHomeView extends IBaseView{

        /**
         * 验证当前的状态,是否处于登录状态
         * @param isLogin true : 已登录
         *                fales : 未登录
         */
        void currentStatus(boolean isLogin);

        /**
         * 验证失败
         * 处于已登录状态，但用户名/密码在其他的设备上面被修改
         */
        void authFailed();

        /**
         * 更新用户的头像和用户名信息
         */
        void updateUserInfo();


    }


    interface IHomePresenter extends IBasePresenter<IHomeView>{

        /**
         * 根据用户的用户名和密码请求服务器检查验证用户是否合法,并进行更新用户的信息
         */
        void checkUserCurrentStatus(HomeActivity context);

    }

}
