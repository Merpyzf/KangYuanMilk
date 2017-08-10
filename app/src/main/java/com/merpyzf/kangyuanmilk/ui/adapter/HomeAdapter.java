package com.merpyzf.kangyuanmilk.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.widget.RecyclerAdapter;
import com.merpyzf.kangyuanmilk.common.widget.ViewHolder;
import com.merpyzf.kangyuanmilk.utils.ui.GliderImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wangke on 2017-08-10.
 */

public class HomeAdapter extends RecyclerAdapter<String> {

    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;

    public HomeAdapter(List<String> mDatas, Context mContext, RecyclerView mRecyclerView) {
        super(mDatas, mContext, mRecyclerView);
    }

    @Override
    public ViewHolder createHolder(ViewGroup parent, int viewType) {

        switch (viewType) {

            case ONE:
                View view = LayoutInflater.from(mContext)
                        .inflate(R.layout.item_home_header, parent, false);
                return new ViewHolderHeader(view);


        }

        return null;

    }



    @Override
    public int getItemViewType(int position) {

        if (position == 0) {

            return ONE;
        }

        return ONE;
    }

    class ViewHolderHeader extends com.merpyzf.kangyuanmilk.common.widget.ViewHolder<String> {

        @BindView(R.id.banner)
        Banner mBanner;

        public ViewHolderHeader(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBindWidget(String s) {

            String[] images = {
                    "http://otdmrup4y.bkt.clouddn.com/avatar/f404bcfe254b916019077099e6d28a4a",
                    "http://otdmrup4y.bkt.clouddn.com/avatar/f362347c3fa8404670eaf7219793264c",
                    "http://otdmrup4y.bkt.clouddn.com/avatar/d95503979f8f27038a8c020acacbada7",
                    "http://otdmrup4y.bkt.clouddn.com/avatar/cd015e51774f5c773026685ac4498b19"
            };

            List<String> imageList = Arrays.asList(images);

            List<String> titleList = new ArrayList<>();
            titleList.add("第一张图片");
            titleList.add("第二张图片");
            titleList.add("第三张图片");
            titleList.add("第四张图片");


            mBanner.isAutoPlay(true);
            mBanner.setImages(imageList);
            mBanner.setImageLoader(new GliderImageLoader());
            mBanner.setIndicatorGravity(BannerConfig.RIGHT);
            mBanner.setBannerTitles(titleList);
            mBanner.setBannerAnimation(Transformer.Stack);
            mBanner.start();

        }
    }

}
