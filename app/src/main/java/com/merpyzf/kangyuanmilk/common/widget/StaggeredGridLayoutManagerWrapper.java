package com.merpyzf.kangyuanmilk.common.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

/**
 * Created by wangke on 2017-08-22.
 *
 * 捕获 IndexOutOfBoundsException 异常，避免在刷新的时候程序崩溃
 */

public class StaggeredGridLayoutManagerWrapper extends StaggeredGridLayoutManager {

    public StaggeredGridLayoutManagerWrapper(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public StaggeredGridLayoutManagerWrapper(int spanCount, int orientation) {
        super(spanCount, orientation);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        //捕获 IndexOutOfBoundsException 异常，避免在刷新的时候程序崩溃
        try {
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
}
