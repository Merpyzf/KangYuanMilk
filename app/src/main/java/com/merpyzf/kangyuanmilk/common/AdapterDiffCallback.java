package com.merpyzf.kangyuanmilk.common;

import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * Created by wangke on 2017-08-18.
 *
 *
 */

public class AdapterDiffCallback<T> extends DiffUtil.Callback {

    private List<T> mOldList;
    private List<T> mNewList;


    public AdapterDiffCallback(List<T> mOldList, List<T> mNewList) {
        this.mOldList = mOldList;
        this.mNewList = mNewList;
    }

    @Override
    public int getOldListSize() {

        return mOldList.size();
    }

    @Override
    public int getNewListSize() {

        return mNewList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {

        return mOldList.get(oldItemPosition).getClass().equals(mNewList.get(newItemPosition).getClass());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

        return mOldList.get(oldItemPosition).equals(mNewList.get(newItemPosition));
    }
}
