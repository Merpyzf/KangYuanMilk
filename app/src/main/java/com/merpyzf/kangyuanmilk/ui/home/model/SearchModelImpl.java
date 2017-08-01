package com.merpyzf.kangyuanmilk.ui.home.model;

import com.merpyzf.kangyuanmilk.utils.db.dao.SearchDao;

import java.util.List;

/**
 * Created by wangke on 2017-08-01.
 */

public class SearchModelImpl implements ISearchMode {

    @Override
    public List<SearchBean> getSearchHistoryData() {

        return SearchDao.getInstance().getAllhistorySearchData();
    }

    @Override
    public int saveSearchData(SearchBean searchBean) {

        return SearchDao.getInstance().saveSearchData(searchBean);
    }



    @Override
    public int delSearchData(SearchBean searchBean) {
        return SearchDao.getInstance().delSearchData(searchBean);
    }

    @Override
    public void delAllSearchData() {
         SearchDao.getInstance().delAllSearchData();
    }
}
