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

                                    bannerImages.add("/header/IMG_20160310_082752.jpg");
                                    bannerImages.add("/header/IMG_20160310_082752.jpg");
                                    mMvpView.fillGoodsBanner(bannerImages);
                                    mMvpView.showDistrLocation();

//
//
                                    mMvpView.fillWebView(
                                            "<!DOCTYPE HTML><html>" +
                                                    "<head> <meta chartset=\"utf-8\"> " +
                                                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"/></head>"
                                                    +"这是一个商品详情的介绍" +
                                            "<br>" + "<img src=\"http://otdmrup4y.bkt.clouddn.com/Screenshot_2017-09-15-18-38-21-233_%E5%85%89%E6%98%8E%E9%9A%8F%E5%BF%83%E8%AE%A2.png \">" + "<br>" + "这是一个商品详情的介绍" +
                                            "<br>" + "<img src=\" http://otdmrup4y.bkt.clouddn.com/Screenshot_2017-09-15-18-38-12-431_%E5%85%89%E6%98%8E%E9%9A%8F%E5%BF%83%E8%AE%A2.png\">" + "<br>" + "这是一个商品详情的介绍" +
                                            "<br>" + "这是一个商品详情的介绍" + "<br>"+"</html>");


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
