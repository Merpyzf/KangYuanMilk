package com.merpyzf.kangyuanmilk.ui.base;

/**
 * Created by wangke on 17-7-16.
 * 基类View接口
 *
 */

public interface IBaseView {

    void showLoadingDialog();
    void cancleLoadingDialog();
    void showErrorMsg(String errorMsg);

}
