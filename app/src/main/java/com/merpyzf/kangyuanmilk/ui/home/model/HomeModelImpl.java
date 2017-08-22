package com.merpyzf.kangyuanmilk.ui.home.model;

import com.merpyzf.kangyuanmilk.ui.home.bean.HomeBean;
import com.merpyzf.kangyuanmilk.utils.http.RetrofitFactory;

import io.reactivex.Observable;

/**
 * Created by wangke on 2017-08-22.
 */

public class HomeModelImpl implements IHomeModel {

    @Override
    public Observable<HomeBean> getHomePageData() {

        return RetrofitFactory.getServiceInstance()
                .getHomePageData();
    }
}
