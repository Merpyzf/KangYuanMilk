package com.merpyzf.kangyuanmilk.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.widget.RecyclerAdapter;
import com.merpyzf.kangyuanmilk.ui.home.contract.ISearchContract;
import com.merpyzf.kangyuanmilk.ui.home.model.SearchBean;
import com.merpyzf.kangyuanmilk.utils.LogHelper;

import java.util.List;

import butterknife.BindView;

/**
 * Created by wangke on 2017-08-01.
 */

public class SearchHistoryAdapter extends RecyclerAdapter {

    private static final int VIEW_SEARCH_INFO = 0x00001;
    private static final int VIEW_CLEAR = 0x00002;
    private ISearchContract.ISearchPresenter mPresenter;

    public SearchHistoryAdapter(ISearchContract.ISearchPresenter presenter,List<SearchBean> mDatas, Context mContext, RecyclerView mRecyclerView) {
        super(mDatas, mContext, mRecyclerView);
        mPresenter = presenter;

    }

    @Override
    public com.merpyzf.kangyuanmilk.common.widget.ViewHolder createHolder(ViewGroup parent, int viewType) {

        switch (viewType) {

            case VIEW_SEARCH_INFO:

                View viewSearchInfo = LayoutInflater.from(mContext).inflate(R.layout.item_search_history, parent, false);


                return new SearchViewHolder(viewSearchInfo);

            case VIEW_CLEAR:

                View viewClear = LayoutInflater.from(mContext).inflate(R.layout.item_search_clear, parent, false);


                return new ClearViewHolder(viewClear);

        }

        LogHelper.i("createHolder==>");

        return null;
    }

    @Override
    public int getItemViewType(int position) {


        if (mDatas.size() - 1 == position) {

            return VIEW_CLEAR;

        } else {

            return VIEW_SEARCH_INFO;
        }

    }

    /**
     * 显示搜索历史的ViewHolder
     */
    class SearchViewHolder extends com.merpyzf.kangyuanmilk.common.widget.ViewHolder<SearchBean> {

        @BindView(R.id.tv_info)
        TextView tv_info;
        @BindView(R.id.iv_del)
        ImageView iv_del;


        public SearchViewHolder(View itemView) {
            super(itemView);


        }

        @Override
        protected void onBindWidget(SearchBean searchBean) {

            tv_info.setText(searchBean.getSearchInfo());

            iv_del.setOnClickListener(view -> {

                mDatas.remove(searchBean);
                //删除数据库中的这一条数据
                mPresenter.delSearchHistoryData(searchBean);

                if(mDatas.size() == 1){
                    mDatas.clear();

                }
                notifyDataSetChanged();
                LogHelper.i("当前点击==>"+searchBean.getSearchInfo());

            });

        }
    }

    /**
     * 清空搜索历史的ViewHolder
     */
    class ClearViewHolder extends com.merpyzf.kangyuanmilk.common.widget.ViewHolder<SearchBean> {

        @BindView(R.id.btn_clear)
        Button btn_clear;

        public ClearViewHolder(View itemView) {
            super(itemView);

        }

        @Override
        protected void onBindWidget(SearchBean searchBean) {

            btn_clear.setOnClickListener(view -> {

                mDatas.clear();
                notifyDataSetChanged();
                //删除数据库中的所有的数据
                mPresenter.delAllSearchHistoryData();


            });

        }
    }
}

