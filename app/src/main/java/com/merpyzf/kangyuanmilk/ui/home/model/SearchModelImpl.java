package com.merpyzf.kangyuanmilk.ui.home.model;

import com.merpyzf.kangyuanmilk.ui.home.bean.QueryKey;
import com.merpyzf.kangyuanmilk.ui.home.bean.SearchBean;
import com.merpyzf.kangyuanmilk.utils.db.dao.SearchDao;
import com.merpyzf.kangyuanmilk.utils.http.RetrofitFactory;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by wangke on 2017-08-01.
 */

public class SearchModelImpl implements ISearchMode {

    @Override
    public List<SearchHistoryBean> getSearchHistoryData() {

        return SearchDao.getInstance().getAllhistorySearchData();
    }

    @Override
    public int saveSearchData(SearchHistoryBean searchHistoryBean) {


        //




        return SearchDao.getInstance().saveSearchData(searchHistoryBean);
    }


    @Override
    public int delSearchData(SearchHistoryBean searchHistoryBean) {
        return SearchDao.getInstance().delSearchData(searchHistoryBean);
    }

    @Override
    public void delAllSearchData() {
        SearchDao.getInstance().delAllSearchData();
    }

    @Override
    public Observable<SearchBean> searchGoods(QueryKey queryKey) {

        return RetrofitFactory.getServiceInstance()
                .searchGoods(queryKey);


    }
}
