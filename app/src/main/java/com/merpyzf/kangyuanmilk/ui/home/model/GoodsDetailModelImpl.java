package com.merpyzf.kangyuanmilk.ui.home.model;

import com.merpyzf.kangyuanmilk.ui.home.bean.GoodsDetailBean;
import com.merpyzf.kangyuanmilk.utils.http.RetrofitFactory;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wangke on 2017/9/9.
 */

public class GoodsDetailModelImpl implements IGoodsDetailModel {

    @Override
    public Observable<GoodsDetailBean> getGoodsDetail(int id) {
        return RetrofitFactory.getServiceInstance()
                .getGoodsDetailById(id + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
