package com.merpyzf.kangyuanmilk.ui.home.model;

import com.merpyzf.kangyuanmilk.ui.home.bean.GoodsBean;
import com.merpyzf.kangyuanmilk.utils.http.RetrofitFactory;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wangke on 2017-08-14.
 */

public class GoodsModelImpl implements IGoodsModel {

    @Override
    public Observable<GoodsBean> getGoodsData(String categoryId, String page, String num) {

        return RetrofitFactory.getServiceInstance()
                .getGoodsById(categoryId, page, num)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());


    }
}
