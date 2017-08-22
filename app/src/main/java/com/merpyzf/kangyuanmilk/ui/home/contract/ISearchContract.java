package com.merpyzf.kangyuanmilk.ui.home.contract;

import com.merpyzf.kangyuanmilk.ui.base.IBasePresenter;
import com.merpyzf.kangyuanmilk.ui.base.IBaseView;
import com.merpyzf.kangyuanmilk.ui.home.bean.Goods;
import com.merpyzf.kangyuanmilk.ui.home.bean.QueryKey;
import com.merpyzf.kangyuanmilk.ui.home.model.SearchHistoryBean;

import java.util.List;

/**
 * Created by wangke on 2017-08-01.
 */

public class ISearchContract {


    public interface ISearchView extends IBaseView {

        /**
         * 展示搜索历史数据
         */
        void showSearchHistory(List<SearchHistoryBean> searchHistoryBeanList);

        /**
         * 搜索返回的数据
         * @param dataList
         */
        void searchGoodsDataList(List<Goods> dataList);

    }


    public interface ISearchPresenter extends IBasePresenter<ISearchView> {

        /**
         * 搜索乳品信息
         *
         * @param queryKey 搜索的关键字信息
         */
        void searchGoodsKey(QueryKey queryKey);

        /**
         * 获取搜索历史的数据
         */
        void getSearchHistoryData();

        /**
         * 保存搜索数据
         */
        void saveSearchData(SearchHistoryBean searchHistoryBean);

        /**
         * 删除搜索历史的数据
         */
        void delSearchHistoryData(SearchHistoryBean searchHistoryBean);

        /**
         * 删除所有的搜索历史的数据
         */
        void delAllSearchHistoryData();


    }


}
