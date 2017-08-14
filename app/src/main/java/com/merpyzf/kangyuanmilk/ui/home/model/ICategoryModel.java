package com.merpyzf.kangyuanmilk.ui.home.model;

import com.merpyzf.kangyuanmilk.ui.home.bean.Meizi;

import io.reactivex.Observable;

/**
 * Created by wangke on 2017-08-14.
 */

public interface ICategoryModel {

    public Observable<Meizi> getMeizi(String page);

}
