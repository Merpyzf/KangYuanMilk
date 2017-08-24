package com.merpyzf.kangyuanmilk.ui.home.presenter;

import com.merpyzf.kangyuanmilk.ui.base.BasePresenter;
import com.merpyzf.kangyuanmilk.ui.home.bean.Goods;
import com.merpyzf.kangyuanmilk.ui.home.bean.GoodsBean;
import com.merpyzf.kangyuanmilk.ui.home.contract.IGoodsContract;
import com.merpyzf.kangyuanmilk.ui.home.model.GoodsModelImpl;
import com.merpyzf.kangyuanmilk.ui.home.model.IGoodsModel;
import com.merpyzf.kangyuanmilk.ui.home.view.GoodsFragment;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wangke on 2017-08-14.
 */

public class GoodsPresenterImpl extends BasePresenter<IGoodsContract.IGoodsView> implements IGoodsContract.IGoodsPresenter {

    private IGoodsModel mModel;

    public GoodsPresenterImpl() {

        mModel = new GoodsModelImpl();
    }

    @Override
    public void getGoodsData(GoodsFragment context, String categoryId, String page, String num) {

        mModel.getGoodsData(categoryId, page, num)
                .compose(context.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .subscribe(new Observer<GoodsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GoodsBean goodsBean) {

                        List<Goods> results = goodsBean.getResponse().getDataList();

                        mMvpView.getGoodsData(results);


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
