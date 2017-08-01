package com.merpyzf.kangyuanmilk.ui.home.model;

import java.util.List;

/**
 * Created by wangke on 2017-08-01.
 */

public interface ISearchMode  {

    /**
     * 查询历史搜索的数据
     * @return
     */
    List<SearchBean> getSearchHistoryData();

    /**
     * 保存查询数据
     * @param searchBean
     * @return
     */
    int saveSearchData(SearchBean searchBean);

    /**
     * 删除某一条历史查询的数据
     * @param searchBean
     * @return
     */
    int delSearchData(SearchBean searchBean);

    /**
     * 删除所有历史查询的数据
     * @return
     */
    void delAllSearchData();




}
