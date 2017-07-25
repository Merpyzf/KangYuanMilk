package com.merpyzf.kangyuanmilk.ui.login.contract;

import com.merpyzf.kangyuanmilk.ui.base.IBasePresenter;
import com.merpyzf.kangyuanmilk.ui.base.IBaseView;
import com.merpyzf.kangyuanmilk.ui.login.LoginActivity;
import com.merpyzf.kangyuanmilk.ui.login.bean.LoginBean;

/**
 * Created by wangke on 2017-07-22.
 */

public interface ILoginContract {

    /**
     * LoginActivity的View层接口
     */
    interface ILoginView extends IBaseView {

        /**
         * 登录
         *
         * @param username
         * @param pwd
         */
        void login(String username, String pwd);

        /**
         * 登录失败的错误提示
         *
         * @param errorText
         */
        void loginError(String errorText);

        /**
         * 登录成功
         *
         * @param success  登录成功后的文本提示
         * @param username 返回用户名密码用于记录用户登录信息
         * @param pwd
         */
        void loginSuccess(String success, String username, String pwd);


        /**
         * 读取用户的登录信息
         */
        void readLoginInfo();



    }

    /**
     * void
     * LoginActivity的Presenter层接口
     */
    interface ILoginPresenter extends IBasePresenter<ILoginView> {

        void login(LoginActivity context, String username, String pwd);

        /**
         * 保存登录信息
         * @param username
         * @param pwd
         */
        void saveLoginInfo(String username, String pwd);

        /**
         * 登录成功后保存用户信息
         * @param user
         */
        void saveUserInfo(LoginBean.ResponseBean.UserBean user);


    }

}
