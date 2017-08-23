package com.merpyzf.kangyuanmilk.common.widget;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wangke on 17-7-16.
 */

public abstract class ViewHolder<Data> extends RecyclerView.ViewHolder {
    private View mItemView;
    //当前item所对应的那条数据
    protected Data data;
    private final Unbinder unbinder;

    public ViewHolder(View itemView) {
        super(itemView);
        mItemView = itemView;
        unbinder = ButterKnife.bind(this, itemView);
    }
    /**
     * 获取当前item的View
     */
    public View getItemView() {

        return mItemView;
    }

    public void bind(Data data, int position) {
        //将数据存储到当前的ViewHodler
        this.data = data;
        onBindWidget(data);

    }

    public Data getData(){

        return data;
    }

    /**
     * 将数据绑定到控件
     * @param data
     */
    protected abstract void onBindWidget(Data data);

    /**
     * 调用接触绑定
     */
    public void unBind(){

        unbinder.unbind();

    }

}
