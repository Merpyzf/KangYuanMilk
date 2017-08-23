package com.merpyzf.kangyuanmilk.ui.home.model;

import com.merpyzf.kangyuanmilk.ui.home.bean.Meizi;
import com.merpyzf.kangyuanmilk.utils.http.RetrofitFactory;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wangke on 2017-08-14.
 */

public class CategoryModelImpl implements ICategoryModel {

    @Override
    public Observable<Meizi> getMeizi(String page) {

        return RetrofitFactory.getServiceInstance()
                .getMeizi(page,"10")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());


    }
}
