package com.merpyzf.kangyuanmilk.common.observer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by wangke on 2017-07-26.
 * 目标对象
 */

public class Subject {

    /**
     * 用来保存观察者对象
     */
    protected Map<String,Observer> mMap = new HashMap<String,Observer>();

    public void attach(String clazzName,Observer observer){

        mMap.put(clazzName,observer);
        System.out.println(clazzName+ "==> 注册一个观察者");
    }
    /**
     * 删除指定的观察者对象
     * @param clazzName 观察者对象的名字
     */
    public void detach(String clazzName){

        mMap.remove(clazzName);

    }
    /**
     * 通知所有注册的观察者对象进行更新
     */
    public void notifyObservers(){

        //便利啊通知所有的观察者刷新
        Iterator<Map.Entry<String, Observer>> iterator = mMap.entrySet().iterator();
        while (iterator.hasNext()){

            Map.Entry<String, Observer> entry = iterator.next();
            entry.getValue().update();

        }
    }

}
