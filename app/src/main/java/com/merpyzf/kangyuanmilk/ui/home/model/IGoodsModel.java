package com.merpyzf.kangyuanmilk.ui.home.model;

import com.merpyzf.kangyuanmilk.ui.home.bean.GoodsBean;

import io.reactivex.Observable;

/**
 * Created by wangke on 2017-08-14.
 */

public interface IGoodsModel {

     Observable<GoodsBean> getGoodsData (String categoryId, String page, String num);

}
