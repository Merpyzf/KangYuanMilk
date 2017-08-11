package com.merpyzf.kangyuanmilk.utils.ui;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.qiujuer.genius.ui.Ui;

/**
 * Created by wangke on 2017-08-08.
 * 给RecyclerView的item上下添加margin间距
 */

public class ItemMarginDecoration extends RecyclerView.ItemDecoration {
    private int margin = 5;
    private int flag = 0;

    public ItemMarginDecoration() {
        super();
    }

    public ItemMarginDecoration(int margin, int flag) {
        this.margin = margin;
        this.flag = flag;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view);

        if (flag == 0) {

            if (position != 0) {

                outRect.top = (int) Ui.dipToPx(view.getResources(), margin);

            }
        } else if (flag == 1) {


            outRect.top = (int) Ui.dipToPx(view.getResources(), margin);
            outRect.left = (int) Ui.dipToPx(view.getResources(), margin);
            outRect.right = (int) Ui.dipToPx(view.getResources(), margin);

        }

    }
}
