package com.merpyzf.kangyuanmilk.ui.home.model;

import com.merpyzf.kangyuanmilk.ui.home.bean.GoodsDetailBean;

import io.reactivex.Observable;

/**
 * Created by wangke on 2017/9/9.
 */

public interface IGoodsDetailModel {


    /**
     * 获取商品详情
     * @param id
     * @return
     */
    Observable<GoodsDetailBean> getGoodsDetail(int id);


}
