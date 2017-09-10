package com.merpyzf.kangyuanmilk.ui.order;

import android.view.View;
import android.widget.TextView;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.BaseLazyFragment;
import com.merpyzf.kangyuanmilk.utils.LogHelper;

import butterknife.BindView;

/**
 * 展示订单的Fragment 2017-08-15
 *
 * @author wangke
 */
public class OrderFragment extends BaseLazyFragment {
    int mRefreshCount = 0;
    private String mTag;
    @BindView(R.id.tv_tag_name)
    TextView tv_tag_name;


    public OrderFragment(String tag) {
        super(tag);
        this.mTag = tag;
    }

    @Override
    protected void initWidget(View view) {

        tv_tag_name.setText(mTag);

    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void onFragmentVisibleChange(boolean b) {

        if (b) {

            LogHelper.i(mTag + "==>刷新的次数: " + mRefreshCount++);

        }


    }

    /**
     * 进行初始化时的数据加载，只会在Fragment第一次开启的时候调用
     */
    @Override
    protected void onFragmentFirstVisible() {
        LogHelper.i(mTag + "==>第一次被打开 ");
    }
}
