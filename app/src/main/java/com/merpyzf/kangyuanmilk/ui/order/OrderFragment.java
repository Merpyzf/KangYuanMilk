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
    int refreshCount = 0;
    private String tag;
    @BindView(R.id.tv_tag_name)
    TextView tv_tag_name;

    public OrderFragment(String tag) {
        super(tag);
        this.tag = tag;
    }

    @Override
    protected void initWidget(View view) {

        tv_tag_name.setText(tag);

    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void onFragmentVisibleChange(boolean b) {

        if (b) {

            LogHelper.i(tag + "==>刷新的次数: " + refreshCount++);

        }


    }

    /**
     * 进行初始化时的数据加载，只会在Fragment第一次开启的时候调用
     */
    @Override
    protected void onFragmentFirstVisible() {
        LogHelper.i(tag + "==>第一次被打开 ");
    }
}
