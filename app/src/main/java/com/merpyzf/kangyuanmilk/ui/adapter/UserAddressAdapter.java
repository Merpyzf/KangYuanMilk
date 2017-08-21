package com.merpyzf.kangyuanmilk.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.widget.RecyclerAdapter;
import com.merpyzf.kangyuanmilk.ui.user.bean.Address;

import java.util.List;

import butterknife.BindView;

/**
 * Created by wangke on 2017-08-08.
 * <p>
 * 展示用户地址信息的adapter
 */

public class UserAddressAdapter extends RecyclerAdapter<Address> {

    private IUserAddressCallBack.onItemWidgetClickListener mOnItemWidgetClickListener = null;

    public UserAddressAdapter(List<Address> mDatas, Context mContext, RecyclerView mRecyclerView) {
        super(mDatas, mContext, mRecyclerView);
    }

    @Override
    public ViewHolder createHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_user_address, parent, false);

        return new ViewHolder(view);
    }

    class ViewHolder extends com.merpyzf.kangyuanmilk.common.widget.ViewHolder<Address> implements View.OnClickListener {


        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_phone)
        TextView mTvPhone;
        @BindView(R.id.tv_address)
        TextView mTvAddress;
        @BindView(R.id.tv_edit)
        TextView mTvEdit;
        @BindView(R.id.tv_remove)
        TextView mTvRemove;
        @BindView(R.id.checkbox)
        CheckBox mCheckBox;
        @BindView(R.id.tv_cb)
        TextView mTvCb;
        @BindView(R.id.linearlayout_cb)
        LinearLayout mLinearlayoutCb;
        private int mSelectedPosition = -1;


        public ViewHolder(View itemView) {
            super(itemView);

        }

        @Override
        protected void onBindWidget(Address address) {

            mTvName.setText(address.getConsignee());
            mTvPhone.setText(address.getConsignee_tel());
            mTvAddress.setText(address.getAddress_all() + " " + address.getAddress_content());
            mCheckBox.setChecked(address.isDefault());
            if (mCheckBox.isChecked()) {
                mLinearlayoutCb.setClickable(false);
                mTvCb.setText(R.string.tv_cb_text_checked);
            } else {
                mCheckBox.setEnabled(true);
                mTvCb.setText(R.string.tv_cb_text);
                mLinearlayoutCb.setOnClickListener(this);

            }
            mTvEdit.setOnClickListener(this);
            mTvRemove.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {

            switch (view.getId()) {

                case R.id.tv_edit:

                    if (mOnItemWidgetClickListener != null) {

                        mOnItemWidgetClickListener.onEditClick(data);

                    }

                    break;

                case R.id.tv_remove:

                    if (mOnItemWidgetClickListener != null) {

                        mOnItemWidgetClickListener.onRemoveClick(data);

                    }
                    break;


                case R.id.linearlayout_cb:

                    //获取默认选中的那个item对应的position
                    for (int i = 0; i < mDatas.size(); i++) {
                        if (mDatas.get(i).isDefault()) {
                            mSelectedPosition = i;
                        }
                    }


                    //获取当前选中的position的下标
                    int position = getPosition();

                    //局部item刷新
                    if (mSelectedPosition != position) {

                        //列表中有被选中的默认地址
                        if (mSelectedPosition != -1) {

                            mDatas.get(mSelectedPosition).setDefault(false);
                            notifyItemChanged(mSelectedPosition);

                        }

                        mSelectedPosition = position;
                        mDatas.get(mSelectedPosition).setDefault(true);
                        notifyItemChanged(mSelectedPosition);

                    }


                    if (mOnItemWidgetClickListener != null) {

                        mOnItemWidgetClickListener.onCheckedChanged(data);

                    }


                    break;
            }


        }
    }

    public void setmOnItemWidgetClickListener(IUserAddressCallBack.onItemWidgetClickListener mOnItemWidgetClickListener) {
        this.mOnItemWidgetClickListener = mOnItemWidgetClickListener;
    }


}
