package com.merpyzf.kangyuanmilk.ui.home.contract;

import com.merpyzf.kangyuanmilk.ui.base.IBasePresenter;
import com.merpyzf.kangyuanmilk.ui.base.IBaseView;
import com.merpyzf.kangyuanmilk.ui.home.model.SearchBean;

import java.util.List;

/**
 * Created by wangke on 2017-08-01.
 */

public class ISearchContract {


    public interface ISearchView extends IBaseView {

        /**
         * 展示搜索历史数据
         */
        void showSearchHistory(List<SearchBean> searchBeanList);

        /**
         * 展示查询出来的乳品列表
         */
        void showMilkDataList();


    }


    public interface ISearchPresenter extends IBasePresenter<ISearchView> {

        /**
         * 搜索乳品信息
         *
         * @param info 搜索的关键字
         */
        void searchMilkListData(String info);

        /**
         * 获取搜索历史的数据
         */
        void getSearchHistoryData();

        /**
         * 保存搜索数据
         */
        void saveSearchData(SearchBean searchBean);

        /**
         * 删除搜索历史的数据
         */
        void delSearchHistoryData(SearchBean searchBean);

        /**
         * 删除所有的搜索历史的数据
         */
        void delAllSearchHistoryData();

    }


}
