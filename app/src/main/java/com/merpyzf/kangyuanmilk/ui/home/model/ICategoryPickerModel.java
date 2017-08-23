package com.merpyzf.kangyuanmilk.ui.home.model;

import com.merpyzf.kangyuanmilk.ui.home.bean.CategoryBean;

import io.reactivex.Observable;

/**
 * Created by wangke on 2017-08-23.
 * <p>
 * 商品分类选择
 */

public interface ICategoryPickerModel {

    Observable<CategoryBean> getGoodsCategory();

}
