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
import com.merpyzf.kangyuanmilk.common.widget.RecyclerAdapter;
import com.merpyzf.kangyuanmilk.common.widget.ViewHolder;
import com.merpyzf.kangyuanmilk.ui.home.bean.Meizi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

/**
 * Created by wangke on 2017-08-14.
 */

public class CategoryAdapter extends RecyclerAdapter<Meizi.ResultsBean> {

    private List<Integer> mHeight = new ArrayList();

    public CategoryAdapter(List<Meizi.ResultsBean> mDatas, Context mContext, RecyclerView mRecyclerView) {
        super(mDatas, mContext, mRecyclerView);
    }

    @Override
    public ViewHolder createHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_goods, parent, false);


        return new MyViewHolder(view);
    }


    class MyViewHolder extends ViewHolder<Meizi.ResultsBean> {

        @BindView(R.id.iv_goods)
        ImageView iv_goods;
        @BindView(R.id.tv_info)
        TextView tv_info;

        public MyViewHolder(View itemView) {
            super(itemView);


        }

        @Override
        protected void onBindWidget(Meizi.ResultsBean resultsBean) {


            ViewGroup.LayoutParams params = iv_goods.getLayoutParams();

            if (iv_goods.getTag(R.id.item_iv_height) != null) {

                params.height = (int) iv_goods.getTag(R.id.item_iv_height);
            } else {

                Random random = new Random();
                int height = 500 + random.nextInt(200);
                iv_goods.setTag(R.id.item_iv_height, height);
                params.height = height;
            }


            iv_goods.setLayoutParams(params);

//            LogHelper.i("高度==》"+integer);


            Glide.with(mContext)
                    .load(resultsBean.getUrl())
                    .centerCrop()
                    .into(iv_goods);

            tv_info.setText(resultsBean.getDesc());


        }
    }


}
