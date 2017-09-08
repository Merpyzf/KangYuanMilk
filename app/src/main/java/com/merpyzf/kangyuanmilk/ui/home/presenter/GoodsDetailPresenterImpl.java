package com.merpyzf.kangyuanmilk.ui.home.presenter;

import com.merpyzf.kangyuanmilk.ui.base.BasePresenter;
import com.merpyzf.kangyuanmilk.ui.home.contract.IGoodsDetailContract;
import com.merpyzf.kangyuanmilk.ui.home.model.GoodsDetailModelImpl;
import com.merpyzf.kangyuanmilk.ui.home.model.IGoodsDetailModel;
import com.merpyzf.kangyuanmilk.ui.home.view.GoodsDetailActivity;

/**
 * Created by wangke on 2017/9/9.
 */

public class GoodsDetailPresenterImpl extends BasePresenter<IGoodsDetailContract.IGoodsDetailView> implements IGoodsDetailContract.IGoodsDetailPresenter{

    private GoodsDetailActivity context;
    private IGoodsDetailModel mModel;

    public GoodsDetailPresenterImpl(GoodsDetailActivity context) {

        this.context = context;
        mModel = new GoodsDetailModelImpl();


    }

    @Override
    public void getGoodsData( int goodsId) {

    }

    @Override
    public void addShoppingCart( int goodsId) {

    }

    @Override
    public void buy() {

    }
}
