package com.merpyzf.kangyuanmilk.common.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by wangke on 17-7-16.
 * 用来提示页面当前的状态的ViewGroup继承自线性布局
 * 包含的几种提示：
 *  1. 当前页没有数据为空的提示
 *  2. 网络错误
 *  3. 加载中的提示
 */

public class TipView extends LinearLayout {
    public TipView(Context context) {
        super(context);
    }

    public TipView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TipView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
