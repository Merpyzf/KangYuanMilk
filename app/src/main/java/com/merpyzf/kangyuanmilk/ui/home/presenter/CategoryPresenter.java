package com.merpyzf.kangyuanmilk.ui.home.presenter;

import com.merpyzf.kangyuanmilk.ui.base.BasePresenter;
import com.merpyzf.kangyuanmilk.ui.home.view.GoodsFragment;
import com.merpyzf.kangyuanmilk.ui.home.bean.Meizi;
import com.merpyzf.kangyuanmilk.ui.home.contract.ICategoryContract;
import com.merpyzf.kangyuanmilk.ui.home.model.CategoryModelImpl;
import com.merpyzf.kangyuanmilk.ui.home.model.ICategoryModel;
import com.merpyzf.kangyuanmilk.utils.LogHelper;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wangke on 2017-08-14.
 */

public class CategoryPresenter extends BasePresenter<ICategoryContract.ICategoryView> implements ICategoryContract.ICategoryPresenter {

    private ICategoryModel mModel;

    public CategoryPresenter() {

        mModel = new CategoryModelImpl();
    }

    @Override
    public void getMeiziData(GoodsFragment context, String page, boolean isRefresh) {

        mModel.getMeizi(page)
                .compose(context.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .subscribe(new Observer<Meizi>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Meizi meizi) {

                        List<Meizi.ResultsBean> results = meizi.getResults();

                        for (Meizi.ResultsBean bean : results) {

                            LogHelper.i("图片地址: " + bean.getUrl());

                        }


                        if (isRefresh) {
                            //刷新时第一页返回的数据
                            mMvpView.getMeiziData(meizi,true);

                        }else {
                            //正常加载
                            mMvpView.getMeiziData(meizi,false);
                        }


                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
