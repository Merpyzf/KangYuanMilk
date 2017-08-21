package com.merpyzf.kangyuanmilk.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.data.Common;
import com.merpyzf.kangyuanmilk.common.widget.RecyclerAdapter;
import com.merpyzf.kangyuanmilk.common.widget.ViewHolder;
import com.merpyzf.kangyuanmilk.ui.home.bean.Goods;

import java.util.List;

import butterknife.BindView;

/**
 * Created by wangke on 2017-08-21.
 */

public class SearchGoodsAdapter extends RecyclerAdapter<Goods> {

    public SearchGoodsAdapter(List<Goods> mDatas, Context mContext, RecyclerView mRecyclerView) {
        super(mDatas, mContext, mRecyclerView);
    }

    @Override
    public ViewHolder createHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_goods, parent, false);
        return new SearchGoodsHolder(view);
    }


    class SearchGoodsHolder extends ViewHolder<Goods> {


        @BindView(R.id.iv_product)
        ImageView iv_produuct;
        @BindView(R.id.tv_product_name)
        TextView tv_product_name;
        @BindView(R.id.tv_spec)
        TextView tv_spec;
        @BindView(R.id.tv_price)
        TextView tv_price;


        public SearchGoodsHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBindWidget(Goods goods) {

            Glide.with(mContext)
                    .load(Common.OUTSIDE_CHAIN+goods.getImageview())
                    .centerCrop()
                    .placeholder(R.drawable.ic_avater_default)
                    .into(iv_produuct);

            tv_product_name.setText(goods.getTitle());
            tv_spec.setText(goods.getSpec());
            tv_price.setText("￥"+goods.getPrice());

        }
    }

}
