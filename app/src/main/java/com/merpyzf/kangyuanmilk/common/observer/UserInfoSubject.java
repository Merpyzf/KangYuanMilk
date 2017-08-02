package com.merpyzf.kangyuanmilk.common.observer;

/**
 * Created by wangke on 2017-07-26.
 */

public class UserInfoSubject extends Subject {

    private static UserInfoSubject instance = null;

    private UserInfoSubject() {

    }

    public static UserInfoSubject getInstance() {

        if (instance == null) {
            synchronized (Object.class) {

                if (instance == null) {
                    instance = new UserInfoSubject();
                }
            }
        }
        return instance;
    }

    /**
     * 状态发生改变，通知所有的观察者更新
     */
    public void notifyChange() {

        super.notifyObservers();
    }

    /**
     * 通知指定的观察者刷新
     *
     * @param clazzName 观察者的名字
     */
    public void notifyChange(String clazzName) {


        Observer observer = mMap.get(clazzName);
        if (observer != null) {
            observer.update();
        }

        return;

    }

}
