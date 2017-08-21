package com.merpyzf.kangyuanmilk.ui.home.model;

import com.merpyzf.kangyuanmilk.ui.home.bean.QueryKey;
import com.merpyzf.kangyuanmilk.ui.home.bean.SearchBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by wangke on 2017-08-01.
 */

public interface ISearchMode  {

    /**
     * 查询历史搜索的数据
     * @return
     */
    List<SearchHistoryBean> getSearchHistoryData();

    /**
     * 保存查询数据
     * @param searchHistoryBean
     * @return
     */
    int saveSearchData(SearchHistoryBean searchHistoryBean);

    /**
     * 删除某一条历史查询的数据
     * @param searchHistoryBean
     * @return
     */
    int delSearchData(SearchHistoryBean searchHistoryBean);

    /**
     * 删除所有历史查询的数据
     * @return
     */
    void delAllSearchData();


    /**
     * 商品搜索
     * @param queryKey 关键字对象
     */
    Observable<SearchBean> searchGoods(QueryKey queryKey);



}
