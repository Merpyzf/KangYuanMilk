package com.merpyzf.kangyuanmilk.utils;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.merpyzf.kangyuanmilk.common.widget.RecyclerAdapter;

/**
 * Created by wangke on 2017-08-21.
 */

public class RecyclerViewHelper {

    private RecyclerViewHelper() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }



    /**
     * 将RecyclerView中的item移动到指定的位置
     *
     * @param position     要移动的位置
     * @param recyclerView recyclerview
     */
    private void moveToPosition(int position, RecyclerView recyclerView, RecyclerAdapter adapter) {

        StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();

        //先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
        int[] firstItem = manager.findFirstVisibleItemPositions(new int[]{0, 2});
        int[] lastItem = manager.findLastVisibleItemPositions(new int[]{adapter.getItemCount() - 2, adapter.getItemCount() - 1});
        //然后区分情况
        if (position <= firstItem[0]) {

            //当要置顶的项在当前显示的第一个项的前面时
            recyclerView.scrollToPosition(position);
        } else if (position <= lastItem[0]) {

            //当要置顶的项已经在屏幕上显示时
            int top = recyclerView.getChildAt(position - firstItem[0]).getTop();
            recyclerView.scrollBy(0, top);
        } else {

            //当要置顶的项在当前显示的最后一项的后面时
            recyclerView.scrollToPosition(position);
            //这里这个变量是用在RecyclerView滚动监听里面的
        }

    }


}
