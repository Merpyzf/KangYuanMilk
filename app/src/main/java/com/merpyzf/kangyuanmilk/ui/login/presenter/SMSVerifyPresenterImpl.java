package com.merpyzf.kangyuanmilk.ui.login.presenter;

import com.merpyzf.kangyuanmilk.ui.base.BasePresenter;
import com.merpyzf.kangyuanmilk.ui.login.contract.ISMSVerifyContract;
import com.merpyzf.kangyuanmilk.utils.LogHelper;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by wangke on 2017-07-25.
 */

public class SMSVerifyPresenterImpl extends BasePresenter<ISMSVerifyContract.ISMSVerifyView> implements ISMSVerifyContract.ISMSVerifyPresenter {

    private static String phoneNum = null;


    public SMSVerifyPresenterImpl() {

        // 注册监听器
        SMSSDK.registerEventHandler(mEventHandler);
    }


    // 验证结果的回调
    private EventHandler mEventHandler = new EventHandler() {

        public void afterEvent(int event, int result, Object data) {
            //回调在子线程中的
            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功

                    if (mMvpView == null) {

                        LogHelper.i("提交验证码的时候===>mMvpView = null");

                    } else {

                        mMvpView.verifySuccess(phoneNum);

                    }


                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //获取验证码成功
                    if (mMvpView == null) {

                        LogHelper.i("获取验证码的时候===>mMvpView = null");

                    } else {
                        mMvpView.getVerifyCodeSuccess();

                    }
                }
            } else {
                ((Throwable) data).printStackTrace();

                //验证失败
                if (mMvpView != null) {
                    mMvpView.verifyFailed();
                }

            }
        }
    };

    /**
     * 获取短信验证码
     *
     * @param country  城市区号
     * @param phoneNum 手机号码
     */
    @Override
    public void getVerificationCode(String country, String phoneNum) {
        SMSVerifyPresenterImpl.phoneNum = phoneNum;
        SMSSDK.getVerificationCode("86", phoneNum);

    }

    /**
     * 提交验证码
     *
     * @param code 验证码
     */
    @Override
    public void submitVerify(String code) {
        if (phoneNum == null) return;
        SMSSDK.submitVerificationCode("86", phoneNum, code);
    }

}
