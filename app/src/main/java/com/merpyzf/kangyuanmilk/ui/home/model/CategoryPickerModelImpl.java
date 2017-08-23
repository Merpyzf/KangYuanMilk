package com.merpyzf.kangyuanmilk.ui.home.model;

import com.merpyzf.kangyuanmilk.ui.home.bean.CategoryBean;
import com.merpyzf.kangyuanmilk.utils.http.RetrofitFactory;

import io.reactivex.Observable;

/**
 * Created by wangke on 2017-08-23.
 */

public class CategoryPickerModelImpl implements ICategoryPickerModel {

    @Override
    public Observable<CategoryBean> getGoodsCategory() {
        return RetrofitFactory.getServiceInstance()
                .getGooodsCategory();
    }
}
