package com.merpyzf.kangyuanmilk.ui.user.contract;

import com.merpyzf.kangyuanmilk.ui.base.IBasePresenter;
import com.merpyzf.kangyuanmilk.ui.base.IBaseView;
import com.merpyzf.kangyuanmilk.ui.base.User;

/**
 * Created by wangke on 2017-08-02.
 */

public interface IModifyInfoContract {


    interface IModifyInfoView extends IBaseView{

        void updateSuccess(String msg);

        void showUserInfo(User user);

    }

    interface IModifyInfoPresenter extends IBasePresenter<IModifyInfoView>{

        /**
         * 更新用户信息
         * @param user
         */
        void updateUserInfo(User user);

        /**
         * 从数据库中获取用户信息
         */
        void getUserInfo();

    }

}
