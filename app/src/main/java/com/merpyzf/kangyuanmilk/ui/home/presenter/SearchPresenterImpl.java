package com.merpyzf.kangyuanmilk.ui.home.presenter;

import com.merpyzf.kangyuanmilk.ui.base.BasePresenter;
import com.merpyzf.kangyuanmilk.ui.home.bean.QueryKey;
import com.merpyzf.kangyuanmilk.ui.home.bean.SearchBean;
import com.merpyzf.kangyuanmilk.ui.home.contract.ISearchContract;
import com.merpyzf.kangyuanmilk.ui.home.model.ISearchMode;
import com.merpyzf.kangyuanmilk.ui.home.model.SearchHistoryBean;
import com.merpyzf.kangyuanmilk.ui.home.model.SearchModelImpl;
import com.merpyzf.kangyuanmilk.ui.home.view.SearchActivity;
import com.merpyzf.kangyuanmilk.utils.LogHelper;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wangke on 2017-08-01.
 */

public class SearchPresenterImpl extends BasePresenter<ISearchContract.ISearchView> implements ISearchContract.ISearchPresenter {

    private ISearchMode mModel;
    private SearchActivity mContext;

    public SearchPresenterImpl(SearchActivity context) {
        mModel = new SearchModelImpl();
        this.mContext = context;

    }


    /**
     * 查询乳品的列表
     *
     * @param queryKey 搜索的关键字对象
     */
    @Override
    public void searchGoodsKey(QueryKey queryKey) {

        mModel.searchGoods(queryKey)
                .compose(mContext.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SearchBean searchBean) {

                        LogHelper.i("查询的长度:"+searchBean.getResponse().getDataList().size());
                        mMvpView.searchGoodsDataList(searchBean.getResponse().getDataList());


                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    /**
     * 查询所有的历史搜索数据
     */
    @Override
    public void getSearchHistoryData() {

        List<SearchHistoryBean> searchHistoryData = mModel.getSearchHistoryData();

        if (searchHistoryData.size() > 0) {

            searchHistoryData.add(new SearchHistoryBean(" "));

            LogHelper.i("从数据库中查询到的条目个数==>"+searchHistoryData.size());
            mMvpView.showSearchHistory(searchHistoryData);
        }


    }

    /**
     * 保存当前的搜索的数据
     *
     * @param searchHistoryBean
     */
    @Override
    public void saveSearchData(SearchHistoryBean searchHistoryBean) {

        int num = mModel.saveSearchData(searchHistoryBean);

        LogHelper.i("影响的行数==>" + num);
        List<SearchHistoryBean> searchHistoryData = mModel.getSearchHistoryData();

        LogHelper.i("wk", "记录的条数==》" + searchHistoryData.size());


    }

    @Override
    public void delSearchHistoryData(SearchHistoryBean searchHistoryBean) {

        mModel.delSearchData(searchHistoryBean);

    }

    @Override
    public void delAllSearchHistoryData() {

        mModel.delAllSearchData();

    }




}
