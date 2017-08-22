package com.merpyzf.kangyuanmilk.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
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
import com.merpyzf.kangyuanmilk.ui.home.bean.HomeBean;
import com.merpyzf.kangyuanmilk.utils.LogHelper;
import com.merpyzf.kangyuanmilk.utils.ui.GliderImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 主页显示的RecyclerView的适配器
 * <p>
 * Created by wangke on 2017-08-10.
 */

public class HomeAdapter extends RecyclerAdapter<HomeBean.ResponseBean.ResultListBean> {

    private static final int HEADER_BANNER = 0;
    private static final int NEW_ACTIVITY = 1;
    private static final int HOT_PRODUCT = 2;
    private static final int NEW_PRODUCT = 3;

    public HomeAdapter(List<HomeBean.ResponseBean.ResultListBean> mDatas, Context mContext, RecyclerView mRecyclerView) {
        super(mDatas, mContext, mRecyclerView);
    }

    @Override
    public ViewHolder createHolder(ViewGroup parent, int viewType) {

        switch (viewType) {

            case HEADER_BANNER:
                View view_header = LayoutInflater.from(mContext)
                        .inflate(R.layout.item_home_header, parent, false);

                return new HeaderViewHolder(view_header);

            case NEW_ACTIVITY:

                View view_active = LayoutInflater.from(mContext)
                        .inflate(R.layout.item_home_new_activity, parent, false);

                return new ActiveViewHolder(view_active);


            case HOT_PRODUCT:

                View view_hot = LayoutInflater.from(mContext)
                        .inflate(R.layout.item_home_hot_product, parent, false);
                return new HotSellViewHolder(view_hot);

            case NEW_PRODUCT:

                View view_new = LayoutInflater.from(mContext)
                        .inflate(R.layout.item_home_new_product, parent, false);
                return new NewProductViewHolder(view_new);


        }


        return null;

    }


    @Override
    public int getItemViewType(int position) {

        if (mDatas.get(position).getType().equals("HEADER_BANNER")) {

            return HEADER_BANNER;
        } else if (mDatas.get(position).getType().equals("NEW_ACTIVITY")) {

            return NEW_ACTIVITY;
        } else if (mDatas.get(position).getType().equals("HOT_PRODUCT")) {

            return HOT_PRODUCT;
        } else if (mDatas.get(position).getType().equals("NEW_PRODUCT")) {

            return NEW_PRODUCT;
        }

        return -1;
    }

    /**
     * HeaderBanner
     */
    class HeaderViewHolder extends com.merpyzf.kangyuanmilk.common.widget.ViewHolder<HomeBean.ResponseBean.ResultListBean> {

        @BindView(R.id.banner)
        Banner mBanner;

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBindWidget(HomeBean.ResponseBean.ResultListBean data) {


            List<String> imageList = new ArrayList<>();
            List<String> titleList = new ArrayList<>();
            for (int i = 0; i < data.getDataInfoList().size(); i++) {

                imageList.add(data.getDataInfoList().get(i).getImageview());
                titleList.add(data.getDataInfoList().get(i).getTitle());
            }

            mBanner.post(new Runnable() {
                @Override
                public void run() {

                    int height = mBanner.getHeight();
                    LogHelper.i("Banner的高度==>" + height);


                }
            });


            mBanner.isAutoPlay(true);
            mBanner.setImages(imageList);
            mBanner.setImageLoader(new GliderImageLoader());
            mBanner.setIndicatorGravity(BannerConfig.RIGHT);
            mBanner.setBannerTitles(titleList);
            mBanner.setBannerAnimation(Transformer.Stack);
            mBanner.start();


        }
    }

    /**
     * 最新活动
     */
    class ActiveViewHolder extends com.merpyzf.kangyuanmilk.common.widget.ViewHolder<HomeBean.ResponseBean.ResultListBean> {

        @BindView(R.id.banner_new_active)
        Banner mBanner;
        @BindView(R.id.tv_title)
        TextView tv_title;


        public ActiveViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBindWidget(HomeBean.ResponseBean.ResultListBean data) {

            tv_title.setText(data.getModuleTitle());

            List<String> imageList = new ArrayList<>();

            for (int i = 0; i < data.getDataInfoList().size(); i++) {
                imageList.add(data.getDataInfoList().get(i).getImageview());
            }
            mBanner.isAutoPlay(true);
            mBanner.setImages(imageList);
            mBanner.setImageLoader(new GliderImageLoader());
            mBanner.setIndicatorGravity(BannerConfig.RIGHT);
            mBanner.start();

        }
    }

    /**
     * 热销产品
     */
    class HotSellViewHolder extends com.merpyzf.kangyuanmilk.common.widget.ViewHolder<HomeBean.ResponseBean.ResultListBean> {

        @BindView(R.id.recyclerView)
        RecyclerView recyclerView;
        @BindView(R.id.tv_title)
        TextView tv_title;


        public HotSellViewHolder(View itemView) {
            super(itemView);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(linearLayoutManager);

        }

        @Override
        protected void onBindWidget(HomeBean.ResponseBean.ResultListBean data) {
            tv_title.setText(data.getModuleTitle());
            HotSellAdapter adapter = new HotSellAdapter(data.getDataInfoList(), mContext, recyclerView);
            recyclerView.setAdapter(adapter);
        }
    }

    /**
     * 新品尝鲜
     */
    class NewProductViewHolder extends com.merpyzf.kangyuanmilk.common.widget.ViewHolder<HomeBean.ResponseBean.ResultListBean> {

        @BindView(R.id.iv_large)
        ImageView iv_large;
        @BindView(R.id.iv_small1)
        ImageView iv_small1;
        @BindView(R.id.iv_small2)
        ImageView iv_small2;
        @BindView(R.id.tv_title)
        TextView tv_title;


        public NewProductViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBindWidget(HomeBean.ResponseBean.ResultListBean data) {

            tv_title.setText(data.getModuleTitle());
            List<HomeBean.ResponseBean.ResultListBean.DataInfoListBean> dataInfoList = data.getDataInfoList();

            loadImage(iv_large, dataInfoList.get(0).getImageview());
            loadImage(iv_small1, dataInfoList.get(1).getImageview());
            loadImage(iv_small2, dataInfoList.get(2).getImageview());

        }

        private void loadImage(ImageView iv_large, String imageview) {

            Glide.with(mContext)
                    .load(Common.OUTSIDE_CHAIN+imageview)
                    .centerCrop()
                    .placeholder(R.drawable.ic_avater_default)
                    .into(iv_large);

        }

    }


}
