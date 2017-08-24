package com.merpyzf.kangyuanmilk.ui.home.contract;

import com.merpyzf.kangyuanmilk.ui.base.IBasePresenter;
import com.merpyzf.kangyuanmilk.ui.base.IBaseView;
import com.merpyzf.kangyuanmilk.ui.home.bean.Goods;
import com.merpyzf.kangyuanmilk.ui.home.view.GoodsFragment;

import java.util.List;

/**
 * Created by wangke on 2017-08-14.
 */

public interface IGoodsContract {

    interface IGoodsView extends IBaseView {

        void getGoodsData(List<Goods> goodsList);



    }


    interface IGoodsPresenter extends IBasePresenter<IGoodsView> {


        void getGoodsData(GoodsFragment context, String categoryId, String page, String num);

    }

}
