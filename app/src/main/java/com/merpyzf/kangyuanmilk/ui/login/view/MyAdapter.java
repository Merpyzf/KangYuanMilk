package com.merpyzf.kangyuanmilk.ui.login.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.widget.RecyclerAdapter;
import com.merpyzf.kangyuanmilk.common.widget.ViewHolder;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017-07-18.
 */

public class MyAdapter extends RecyclerAdapter<String> {

    public MyAdapter(List<String> mDatas, Context mContext,RecyclerView recyclerView) {
        super(mDatas, mContext,recyclerView);
    }

    @Override
    public ViewHolder createHolder(ViewGroup parent, int viewType) {

        View item = LayoutInflater.from(mContext)
                .inflate(R.layout.item_test, parent, false);

        return new MyHolder(item);
    }

    class MyHolder extends ViewHolder<String>{

        @BindView(R.id.tv_test)
        TextView tv_test;
        public MyHolder(View itemView) {
            super(itemView);
        }
        @Override
        protected void onBindWidget(String s) {

            tv_test.setText(s);
        }


    }
}
