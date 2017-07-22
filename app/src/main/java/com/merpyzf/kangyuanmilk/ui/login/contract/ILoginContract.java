package com.merpyzf.kangyuanmilk.ui.login.contract;

import com.merpyzf.kangyuanmilk.ui.base.IBasePresenter;
import com.merpyzf.kangyuanmilk.ui.base.IBaseView;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * Created by wangke on 2017-07-22.
 */

public interface ILoginContract {

    /**
     * LoginActivity的View层接口
     */
    interface ILoginView extends IBaseView{

        /**
         * 登录
         * @param username
         * @param pwd
         */
        void login(String username,String pwd);

        /**
         * 登录失败的错误提示
         * @param errorText
         */
        void loginError(String errorText);

        /**
         * 登陆成功
         * @param success
         */
        void loginSuccess(String success);


    }

    /**
     * LoginActivity的Presenter层接口
     */
    interface ILoginPresenter extends IBasePresenter<ILoginView>{

        Observable<ResponseBody> login(String username,String pwd);

    }

}
