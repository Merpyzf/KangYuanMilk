package com.merpyzf.kangyuanmilk.common.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ldoublem.loadingviewlib.view.LVEatBeans;
import com.merpyzf.kangyuanmilk.R;

import net.qiujuer.genius.res.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangke on 17-7-16.
 * 用来提示页面当前的状态,继承自线性布局
 */

public class TipView extends RelativeLayout implements ITipView {

    private Context mContext;
    private List<View> mViewList = null;
    private View mBindView = null;


    public TipView(Context context) {
        super(context);
        init(context);

    }

    //@Nullable注解表示参数为null
    public TipView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TipView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        mViewList = new ArrayList<View>();
        setGravity(Gravity.CENTER);
        setVisibility(INVISIBLE);

    }


    @Override
    public void emptyTip(String tip) {
        setVisibility(VISIBLE);

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.layout_empty, this, false);
        TextView tvemptyTip = view.findViewById(R.id.tv_empty);
        tvemptyTip.setText(tip);
        removeOtherView(view);
        addView(view);



    }


    @Override
    public void netErrorTip(String tip) {

        setVisibility(VISIBLE);

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.layout_net_error, this, false);
        TextView tvErrorTip = view.findViewById(R.id.tv_error);
        tvErrorTip.setText(tip);
        removeOtherView(view);
        addView(view);
    }

    @Override
    public void loading() {

        setVisibility(VISIBLE);
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.layout_loading, this, false);
        LVEatBeans eatBeans = view.findViewById(R.id.lvEatBeans);
        eatBeans.setEyeColor(Resource.Color.WHITE);
        eatBeans.setViewColor(Resource.Color.CYAN);
        eatBeans.startAnim(2000);
        //隐藏bind的View和其他的错误其实的view
        removeOtherView(view);
        this.addView(view);
        ViewParent parent = view.getParent();

    }

    @Override
    public void loadingCompleted() {

        setVisibility(INVISIBLE);
        mBindView.setVisibility(VISIBLE);

    }


    /**
     * 移除非tip提示的view
     *
     * @param view
     */
    private void removeOtherView(View view) {

        int childCount = this.getChildCount();

        this.removeAllViews();

        if (mBindView != null) {
            mBindView.setVisibility(INVISIBLE);

        }
    }


    @Override
    public void bindView(View view) {

        mBindView = view;

    }


}
