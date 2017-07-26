package com.merpyzf.kangyuanmilk.common.observer;

/**
 * Created by wangke on 2017-07-26.
 *
 */

public class UserInfoSubject extends Subject {

    private static UserInfoSubject instance = null;

    private UserInfoSubject() {
    }

    public static synchronized UserInfoSubject getInstance() {
        if (instance == null) {
            instance = new UserInfoSubject();
        }
        return instance;
    }

    public void notifyChange() {
        //状态发生改变，通知各个观察者
        this.notifyObservers();
    }


}
