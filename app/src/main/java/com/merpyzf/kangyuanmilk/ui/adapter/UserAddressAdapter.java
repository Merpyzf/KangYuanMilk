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

    private onItemWidgetClickListener mOnItemWidgetClickListener = null;

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
        TextView tv_name;
        @BindView(R.id.tv_phone)
        TextView tv_phone;
        @BindView(R.id.tv_address)
        TextView tv_address;
        @BindView(R.id.tv_edit)
        TextView tv_edit;
        @BindView(R.id.tv_remove)
        TextView tv_remove;
        @BindView(R.id.checkbox)
        CheckBox checkBox;
        @BindView(R.id.tv_cb)
        TextView tv_cb;
        @BindView(R.id.ll_cb)
        LinearLayout ll_cb;


        public ViewHolder(View itemView) {
            super(itemView);

        }

        @Override
        protected void onBindWidget(Address address) {

            tv_name.setText(address.getConsignee());
            tv_phone.setText(address.getConsignee_tel());
            tv_address.setText(address.getAddress_content());
            checkBox.setChecked(address.isDefault());
            if (checkBox.isChecked()) {
                ll_cb.setClickable(false);
                tv_cb.setText("默认地址");
            } else {
                checkBox.setEnabled(true);
                tv_cb.setText("设置地址");
                ll_cb.setOnClickListener(this);

            }
            tv_edit.setOnClickListener(this);
            tv_remove.setOnClickListener(this);
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


                case R.id.ll_cb:


                    mDatas.forEach(address -> {

                        address.setDefault(false);

                    });
                    data.setDefault(true);
                    if (mOnItemWidgetClickListener != null) {

                        mOnItemWidgetClickListener.onCheckedChanged(data);

                    }
                    mRecyclerView.post(() -> notifyDataSetChanged());

                    break;
            }


        }


    }


    public interface onItemWidgetClickListener {
        /**
         * 点击checkbox的回调，用来设置默认地址
         *
         * @param address 要设置成默认地址
         */
        void onCheckedChanged(Address address);

        /**
         * 编辑的按钮被点击后的回调
         *
         * @param address 待编辑的那条地址的信息
         */
        void onEditClick(Address address);

        /**
         * 删除按钮被点击后的回调
         *
         * @param address
         */
        void onRemoveClick(Address address);
    }

    public void setmOnItemWidgetClickListener(onItemWidgetClickListener mOnItemWidgetClickListener) {
        this.mOnItemWidgetClickListener = mOnItemWidgetClickListener;
    }
}
