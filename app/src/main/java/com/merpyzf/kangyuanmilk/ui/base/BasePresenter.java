package com.merpyzf.kangyuanmilk.ui.base;

/**
 * Created by wangke on 17-7-16.
 */

public class BasePresenter<T extends IBaseView> implements IBasePresenter<T>{

    private T mMvpView;

    @Override
    public void attachView(T mvpView) {

        this.mMvpView = mvpView;

    }

    @Override
    public void detachView() {

        this.mMvpView = null;

    }

    public T getMvpView() {
        return mMvpView;
    }
}
