package com.merpyzf.kangyuanmilk.utils;

import com.merpyzf.kangyuanmilk.common.data.Common;

import io.reactivex.Observer;

/**
 * Created by wangke on 2017-07-25.
 * 请求返回的状态码统一进行处理
 */

public abstract class ErrorHandle {

    private Observer mObserver = null;
    private int mStatus;
    public ErrorHandle(Observer mObserver, int mStatus) {
        this.mObserver = mObserver;
        this.mStatus = mStatus;
        handleStatus();

    }


    public void handleStatus() {

        if (mStatus == Common.HTTP_OK) {

            deal();

        } else if (mStatus == Common.HTTP_ERROR) {

            mObserver.onError(new Throwable("500"));


        } else if (mStatus == Common.HTTP_FAILED) {
            mObserver.onError(new Throwable("400"));
        }


    }

    protected abstract void deal();
}
