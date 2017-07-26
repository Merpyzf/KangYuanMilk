package com.merpyzf.kangyuanmilk.common.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangke on 2017-07-26.
 * 通知者的角色
 */

public class Subject {

    /**
     * 用来保存被通知者
     */
    private List<Observer> list = new ArrayList<Observer>();

    public void attach(Observer observer){

        list.add(observer);
        System.out.println("注册一个观察者");
    }
    /**
     * 删除观察者对象
     * @param observer    观察者对象
     */
    public void detach(Observer observer){

        list.remove(observer);
    }
    /**
     * 通知所有注册的观察者对象
     */
    public void notifyObservers(){

        for(Observer observer : list){
            observer.update();
        }
    }






}
