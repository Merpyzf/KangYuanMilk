package com.merpyzf.kangyuanmilk.ui.home.view;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.BaseFragment;
import com.merpyzf.kangyuanmilk.common.widget.RecyclerAdapter;
import com.merpyzf.kangyuanmilk.common.widget.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.merpyzf.kangyuanmilk.R.id.iv_goods;
import static com.merpyzf.kangyuanmilk.R.id.tv_goods_name;

/**
 * 订单
 *
 * @author wangke
 */
public class IndentFragment extends BaseFragment {

    @BindView(R.id.recycler_shoppingcart)
    RecyclerView mRecyclerView;
    List<ShoppingCart> mDataList;
    @BindView(R.id.btn_pay)
    TextView mBtnPay;

    @Override
    protected int getContentLayoutId() {

        return R.layout.fragment_indent;
    }

    @Override
    protected void initWidget(View rootview) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


    }

    @Override
    protected void initEvent() {

        mBtnPay.setOnClickListener(view -> {

            startActivity(new Intent(getContext(), PayActivity.class));

        });


    }

    @Override
    protected void initData() {

        mDataList = new ArrayList<>();
        mDataList.add(new ShoppingCart("印象酸奶", 200, "http://otdmrup4y.bkt.clouddn.com/%E5%B0%8F%E8%8B%B9%E6%9E%9C%E9%85%B8%E5%A5%B6.jpg"));
        mDataList.add(new ShoppingCart("扬大酸奶", 200, "http://otdmrup4y.bkt.clouddn.com/%E6%89%AC%E5%A4%A7%E9%85%B8%E7%89%9B%E5%A5%B6.jpg"));

        mRecyclerView.setAdapter(new MyAdapter(mDataList, getContext(), mRecyclerView));


    }


    class ShoppingCart {

        private String name;
        private float price;
        private String image;

        public ShoppingCart(String name, float price, String image) {
            this.name = name;
            this.price = price;
            this.image = image;
        }
    }

    class MyAdapter extends RecyclerAdapter<ShoppingCart> {


        public MyAdapter(List<ShoppingCart> mDatas, Context mContext, RecyclerView mRecyclerView) {
            super(mDatas, mContext, mRecyclerView);


        }

        @Override
        public ViewHolder createHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_shoppingcart, parent, false);


            return new MyViewHolder(view);
        }
    }


    class MyViewHolder extends ViewHolder<ShoppingCart> {

        private ImageView iv_milk;
        private TextView tv_name;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_milk = itemView.findViewById(iv_goods);
            tv_name = itemView.findViewById(tv_goods_name);

        }

        @Override
        protected void onBindWidget(ShoppingCart shoppingCart) {

            Glide.with(getContext())
                    .load(shoppingCart.image)
                    .placeholder(R.drawable.ic_avater_default)
                    .centerCrop()
                    .into(iv_milk);

            tv_name.setText(shoppingCart.name);


        }
    }


}
