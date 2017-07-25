package com.merpyzf.kangyuanmilk.ui.login.presenter;

import com.merpyzf.kangyuanmilk.ui.base.BasePresenter;
import com.merpyzf.kangyuanmilk.ui.login.contract.ISMSVerifyContract;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by wangke on 2017-07-25.
 */

public class SMSVerifyPresenterImpl extends BasePresenter<ISMSVerifyContract.ISMSVerifyView> implements ISMSVerifyContract.ISMSVerifyPresenter {

    private String phoneNum = null;

    // 创建EventHandler对象
    EventHandler mEventHandler = new EventHandler() {

        public void afterEvent(int event, int result, Object data) {
            //回调在子线程中的
            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    mMvpView.verifySuccess(phoneNum);


                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //获取验证码成功
                    mMvpView.getVerifyCodeSuccess();

                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    //返回支持发送验证码的国家列表


                }

            } else {
                ((Throwable) data).printStackTrace();
                //验证失败
                mMvpView.verifyFailed();
            }
        }


    };



    public SMSVerifyPresenterImpl() {

        // 注册监听器
        SMSSDK.registerEventHandler(mEventHandler);
    }


    @Override
    public void getVerificationCode(String country, String phoneNum) {

        this.phoneNum = phoneNum;
        SMSSDK.getVerificationCode("86", phoneNum);

    }

    @Override
    public void submitVerify(String code) {
        if(phoneNum == null) return;

        SMSSDK.submitVerificationCode("86",phoneNum,code);
    }

}
