package com.merpyzf.kangyuanmilk.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.widget.RecyclerAdapter;
import com.merpyzf.kangyuanmilk.ui.home.bean.Category;

import java.util.List;

import butterknife.BindView;

/**
 * 商品分类选择的适配器
 * <p>
 * Created by wangek on 2017-08-16.
 */

public class CategoryPickerAdapter extends RecyclerAdapter<Category> {

    public CategoryPickerAdapter(List<Category> mDatas, Context mContext, RecyclerView mRecyclerView) {
        super(mDatas, mContext, mRecyclerView);
    }

    @Override
    public ViewHolder createHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_category_picker, parent, false);

        return new ViewHolder(view);
    }

    //记录上一次选中的item的下标
    private int OldCheckedPosition = 0;

    class ViewHolder extends com.merpyzf.kangyuanmilk.common.widget.ViewHolder<Category> {

        @BindView(R.id.linearlayout_category_cb)
        LinearLayout mLinearCategoryCb;
        @BindView(R.id.tv_category_name)
        CheckBox tv_category_name;


        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBindWidget(Category category) {

            tv_category_name.setText(category.getCategory_name());
            tv_category_name.setChecked(category.isChoice());


        }
    }


}
