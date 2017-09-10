package com.merpyzf.kangyuanmilk.ui.home.contract;

import com.merpyzf.kangyuanmilk.ui.base.IBasePresenter;
import com.merpyzf.kangyuanmilk.ui.base.IBaseView;

import java.util.List;

/**
 * Created by wangke on 2017/9/9.
 */

public interface IGoodsDetailContract {


    interface IGoodsDetailView extends IBaseView {

        /**
         * 填充Banner用来展示商品图片
         *
         * @param images
         */
        void fillGoodsBanner(List<String> images);

        /**
         * 填充商品的相关信息
         *
         * @param milkName  商品的名字
         * @param price     商品价格
         * @param spec      商品的规格
         * @param orderSpec 商品订购的规则
         */
        void fillGoodsInfo(String milkName, String price, String spec, String orderSpec);


        /**
         * 填充webView的内容
         *
         * @param html
         */
        void fillWebView(String html);

        /**
         * 设置收货地址的位置
         * <p>
         * 如果用户已经登录，但是没有设置默认的收货地址，点击当前项 跳转到地址选择的界面，进行地址的设置
         */
        void showDistrLocation();

        /**
         * 初始化商品的已选参数 并计算价格
         * @param month
         * @return
         */
        int initGoodParams(int month);


    }


    interface IGoodsDetailPresenter extends IBasePresenter<IGoodsDetailView> {

        /**
         * 获取商品数据
         *
         * @param goodsId 商品id
         */
        void getGoodsData(int goodsId);

        /**
         * 将商品加入购物车
         *
         * @param goodsId
         */
        void addShoppingCart(int goodsId);

        /**
         * 立即购买
         */
        void buy();


    }


}
