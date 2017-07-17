package com.merpyzf.kangyuanmilk.common.widget;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * Created by wangke on 17-7-16.
 * 封装RecclerView,简化开发中adpter的书写
 */

public class RecyclerAdapter<Data> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
