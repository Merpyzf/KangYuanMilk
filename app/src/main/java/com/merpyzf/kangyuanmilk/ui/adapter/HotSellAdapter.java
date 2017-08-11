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
import com.merpyzf.kangyuanmilk.common.data.Response;
import com.merpyzf.kangyuanmilk.common.widget.RecyclerAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017-08-11.
 */

public class HotSellAdapter extends RecyclerAdapter<Response.DataInfo> {

    public HotSellAdapter(List<Response.DataInfo> mDatas, Context mContext, RecyclerView mRecyclerView) {
        super(mDatas, mContext, mRecyclerView);
    }

    @Override
    public ViewHolder createHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_hot_product, parent, false);

        return new ViewHolder(view);
    }


    class ViewHolder extends com.merpyzf.kangyuanmilk.common.widget.ViewHolder<Response.DataInfo> {

        @BindView(R.id.iv_product)
        ImageView iv_produuct;
        @BindView(R.id.tv_product_name)
        TextView tv_product_name;
        @BindView(R.id.tv_spec)
        TextView tv_spec;
        @BindView(R.id.tv_price)
        TextView tv_price;


        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBindWidget(Response.DataInfo dataInfo) {

            Glide.with(mContext)
                    .load(dataInfo.getImageview())
                    .centerCrop()
                    .placeholder(R.drawable.ic_avater_default)
                    .into(iv_produuct);


            tv_product_name.setText(dataInfo.getTitle());
            tv_spec.setText(dataInfo.getSpec());
            tv_price.setText("￥ "+dataInfo.getPrice());
        }
    }


}
