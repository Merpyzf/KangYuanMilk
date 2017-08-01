package com.merpyzf.kangyuanmilk.ui.home.presenter;

import com.merpyzf.kangyuanmilk.ui.base.BasePresenter;
import com.merpyzf.kangyuanmilk.ui.home.contract.ISearchContract;
import com.merpyzf.kangyuanmilk.ui.home.model.ISearchMode;
import com.merpyzf.kangyuanmilk.ui.home.model.SearchBean;
import com.merpyzf.kangyuanmilk.ui.home.model.SearchModelImpl;
import com.merpyzf.kangyuanmilk.utils.LogHelper;

import java.util.List;

/**
 * Created by wangke on 2017-08-01.
 */

public class SearchPresenterImpl extends BasePresenter<ISearchContract.ISearchView> implements ISearchContract.ISearchPresenter {

    private ISearchMode mModel;

    public SearchPresenterImpl() {
        mModel = new SearchModelImpl();
    }

    /**
     * 查询乳品的列表
     *
     * @param info 搜索的关键字
     */
    @Override
    public void searchMilkListData(String info) {

    }

    /**
     * 查询所有的历史搜索数据
     */
    @Override
    public void getSearchHistoryData() {

        List<SearchBean> searchHistoryData = mModel.getSearchHistoryData();

        if (searchHistoryData.size() > 0) {

            searchHistoryData.add(new SearchBean(" "));

            LogHelper.i("从数据库中查询到的条目个数==>"+searchHistoryData.size());
            mMvpView.showSearchHistory(searchHistoryData);
        }


    }

    /**
     * 保存当前的搜索的数据
     *
     * @param searchBean
     */
    @Override
    public void saveSearchData(SearchBean searchBean) {

        int num = mModel.saveSearchData(searchBean);

        LogHelper.i("影响的行数==>" + num);
        List<SearchBean> searchHistoryData = mModel.getSearchHistoryData();

        LogHelper.i("wk", "记录的条数==》" + searchHistoryData.size());


    }

    @Override
    public void delSearchHistoryData(SearchBean searchBean) {

        mModel.delSearchData(searchBean);

    }

    @Override
    public void delAllSearchHistoryData() {

        mModel.delAllSearchData();

    }
}
