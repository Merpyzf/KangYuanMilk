package com.merpyzf.kangyuanmilk.ui.login.contract;

import com.merpyzf.kangyuanmilk.ui.base.IBasePresenter;
import com.merpyzf.kangyuanmilk.ui.base.IBaseView;

/**
 * Created by wangke on 2017-07-25.
 */

public interface ISMSVerifyContract {

    interface ISMSVerifyView extends IBaseView {

        /**
         * 验证码提交后验证成功
         */
        void verifySuccess(String phoneNum);

        /**
         * 验证码验证失败
         */
        void verifyFailed();

        /**
         * 验证码获取成功
         */
        void getVerifyCodeSuccess();



    }

    interface ISMSVerifyPresenter extends IBasePresenter<ISMSVerifyView>{

        /**
         * 获取验证码
         */
        void getVerificationCode(String country,String phoneNum);

        /**
         * 提交验证码
         * @param code
         */
        void submitVerify(String code);


    }


}
