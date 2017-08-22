package com.merpyzf.kangyuanmilk.ui.home.model;

import com.merpyzf.kangyuanmilk.ui.home.bean.HomeBean;

import io.reactivex.Observable;

/**
 * Created by wangke on 2017-08-22.
 */

public interface IHomeModel {


    Observable<HomeBean> getHomePageData();


}
