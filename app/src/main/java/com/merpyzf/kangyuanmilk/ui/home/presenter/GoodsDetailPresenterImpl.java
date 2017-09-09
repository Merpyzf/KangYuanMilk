package com.merpyzf.kangyuanmilk.ui.home.presenter;

import com.merpyzf.kangyuanmilk.ui.base.BasePresenter;
import com.merpyzf.kangyuanmilk.ui.home.bean.GoodsDetailBean;
import com.merpyzf.kangyuanmilk.ui.home.contract.IGoodsDetailContract;
import com.merpyzf.kangyuanmilk.ui.home.model.GoodsDetailModelImpl;
import com.merpyzf.kangyuanmilk.ui.home.model.IGoodsDetailModel;
import com.merpyzf.kangyuanmilk.ui.home.view.GoodsDetailActivity;
import com.merpyzf.kangyuanmilk.utils.ErrorHandle;
import com.merpyzf.kangyuanmilk.utils.ErrorHandleHelper;
import com.merpyzf.kangyuanmilk.utils.LogHelper;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wangke on 2017/9/9.
 */

public class GoodsDetailPresenterImpl extends BasePresenter<IGoodsDetailContract.IGoodsDetailView> implements IGoodsDetailContract.IGoodsDetailPresenter {

    private GoodsDetailActivity context;
    private IGoodsDetailModel mModel;

    public GoodsDetailPresenterImpl(GoodsDetailActivity context) {

        this.context = context;
        mModel = new GoodsDetailModelImpl();


    }

    @Override
    public void getGoodsData(int goodsId) {


        mModel.getGoodsDetail(goodsId)
                .compose(context.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new Observer<GoodsDetailBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GoodsDetailBean goodsDetailBean) {

                        LogHelper.i(goodsDetailBean.toString());

                        new ErrorHandle(this, goodsDetailBean.getStatus()) {
                            @Override
                            protected void deal() {

                                if (goodsDetailBean.getResponse().isResult()) {

                                    GoodsDetailBean.ResponseBean.MilkBean milkBean = goodsDetailBean.getResponse().getMilk();

                                    mMvpView.fillGoodsInfo(milkBean.getMilk_name(),
                                            milkBean.getMilk_price() + "",
                                            milkBean.getMilk_spec(), "现在下单,康源乳业最早将于2017-9-11日为您配送");


                                    List<String> bannerImages = new ArrayList<>();

                                    bannerImages.add("http://otdmrup4y.bkt.clouddn.com/1.png");
                                    bannerImages.add("http://otdmrup4y.bkt.clouddn.com/10.png");
                                    mMvpView.fillGoodsBanner(bannerImages);
                                    mMvpView.showDistrLocation();


                                    mMvpView.fillWebView("这是一个商品详情的介绍" +
                                            "<br>"+"这是一个商品详情的介绍"+"<br>"+"这是一个商品详情的介绍" +
                                            "<br>"+"这是一个商品详情的介绍"+"<br>"+"这是一个商品详情的介绍" +
                                            "<br>"+"这是一个商品详情的介绍"+"<br>");


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

                    }
                });


    }

    @Override
    public void addShoppingCart(int goodsId) {

    }

    @Override
    public void buy() {

    }
}
