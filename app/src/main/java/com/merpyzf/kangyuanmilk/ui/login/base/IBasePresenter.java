package com.merpyzf.kangyuanmilk.ui.login.base;

/**
 * Created by wangke on 17-7-16.
 *
 */

public interface IBasePresenter<V extends IBaseView> {

    /**
     * View层注入引用给p层
     * @param mvpView
     */
    void attachView(V mvpView);

    /**
     * 移除P层持有的View层的引用
     */
    void detachView();




}
