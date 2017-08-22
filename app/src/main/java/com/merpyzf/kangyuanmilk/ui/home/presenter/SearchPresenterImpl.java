package com.merpyzf.kangyuanmilk.ui.home.presenter;

import com.merpyzf.kangyuanmilk.common.widget.TipView;
import com.merpyzf.kangyuanmilk.ui.base.BasePresenter;
import com.merpyzf.kangyuanmilk.ui.home.bean.QueryKey;
import com.merpyzf.kangyuanmilk.ui.home.bean.SearchBean;
import com.merpyzf.kangyuanmilk.ui.home.contract.ISearchContract;
import com.merpyzf.kangyuanmilk.ui.home.model.ISearchMode;
import com.merpyzf.kangyuanmilk.ui.home.model.SearchHistoryBean;
import com.merpyzf.kangyuanmilk.ui.home.model.SearchModelImpl;
import com.merpyzf.kangyuanmilk.ui.home.view.SearchActivity;
import com.merpyzf.kangyuanmilk.utils.ErrorHandle;
import com.merpyzf.kangyuanmilk.utils.ErrorHandleHelper;
import com.merpyzf.kangyuanmilk.utils.LogHelper;
import com.merpyzf.kangyuanmilk.utils.NetworkHelper;
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
    private TipView mTipView;
    private SearchActivity mContext;

    public SearchPresenterImpl(SearchActivity context, TipView tipView) {
        mModel = new SearchModelImpl();
        this.mTipView = tipView;
        this.mContext = context;
    }

    /**
     * 查询乳品的列表
     *
     * @param queryKey 搜索的关键字对象
     */
    @Override
    public void searchGoodsKey(QueryKey queryKey) {


        if (NetworkHelper.isAvailableByPing()) {

            mTipView.reset();
            mMvpView.showLoadingDialog();
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

                            new ErrorHandle(this, searchBean.getStatus()) {
                                @Override
                                protected void deal() {

                                    if (searchBean.getResponse().getDataList().size() > 0) {

                                        LogHelper.i("查询的长度:" + searchBean.getResponse().getDataList().size());
                                        mMvpView.searchGoodsDataList(searchBean.getResponse().getDataList());

                                    } else {

                                        mTipView.setEmptyTip("服务器空空如也……");

                                    }

                                }
                            };


                        }

                        @Override
                        public void onError(Throwable e) {

                            ErrorHandleHelper.handle(e, mMvpView);

                        }

                        @Override
                        public void onComplete() {

                            mMvpView.cancelLoadingDialog();

                        }
                    });

        } else {

            mTipView.setErrorTip("网络不可用，请检查网络！");

        }
    }

    /**
     * 查询所有的历史搜索数据
     */
    @Override
    public void getSearchHistoryData() {

        List<SearchHistoryBean> searchHistoryData = mModel.getSearchHistoryData();

        if (searchHistoryData.size() > 0) {

            searchHistoryData.add(new SearchHistoryBean(" "));

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
        List<SearchHistoryBean> searchHistoryData = mModel.getSearchHistoryData();

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
