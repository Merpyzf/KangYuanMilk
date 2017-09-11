package com.merpyzf.kangyuanmilk.ui.home.model;

import com.merpyzf.kangyuanmilk.ui.home.bean.ContentBean;
import com.merpyzf.kangyuanmilk.utils.http.RetrofitFactory;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 春水碧于天 on 2017/9/11.
 */

public class ContentModelImpl implements IContentModel {

    @Override
    public Observable<ContentBean> getActivityContentById(int activityId) {


        return RetrofitFactory.getServiceInstance()
                .getActivityById(String.valueOf(activityId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
