package com.merpyzf.kangyuanmilk.common.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.App;
import com.merpyzf.kangyuanmilk.common.bean.Address;
import com.merpyzf.kangyuanmilk.utils.LogHelper;
import com.merpyzf.kangyuanmilk.utils.db.dao.AddressDao;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by wangke on 2017-07-30.
 * 地址选择器
 */

public class AddressSelectorView extends LinearLayout implements View.OnClickListener, RecyclerAdapter.ItemClickListener<Address> {

    private TextView tv_province = null; //省
    private TextView tv_city = null; //市
    private TextView tv_country = null; //县
    private View indicator = null; //指示器
    private FastScrollRecyclerView recyclerView;
    private List<Address> mAddressList = null;
    private boolean status = true;
    private AddressAdapter mAddressAdapter;

    //记录所选择的状态
    private Address provinceAddress = null;

    private Address cityAddress = null;

    private Address countryAddress = null; //






    public AddressSelectorView(Context context) {
        super(context);
        init(context);
    }


    public AddressSelectorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AddressSelectorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化布局，填充view
     */
    private void init(Context context) {

        setOrientation(VERTICAL);
        View viewHeader = LayoutInflater.from(context)
                .inflate(R.layout.view_address_header, this, false);
        addView(viewHeader);

        View viewContent = LayoutInflater.from(context)
                .inflate(R.layout.view_address_content, this, false);
        addView(viewContent);
        LinearLayout.LayoutParams layoutParams = (LayoutParams) viewContent.getLayoutParams();
        layoutParams.weight = 1;
        layoutParams.height = 0;
        setLayoutParams(layoutParams);

        tv_province = viewHeader.findViewById(R.id.tv_provnice);
        tv_province.setOnClickListener(this);

        tv_city = viewHeader.findViewById(R.id.tv_city);
        tv_city.setOnClickListener(this);

        tv_country = viewHeader.findViewById(R.id.tv_country);
        tv_country.setOnClickListener(this);

        indicator = viewHeader.findViewById(R.id.indicator);
        recyclerView = viewContent.findViewById(R.id.recycler_address);

        mAddressList = AddressDao.getInstance().getProvinceList();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAddressAdapter = new AddressAdapter(mAddressList, getContext(), recyclerView);
        recyclerView.setAdapter(mAddressAdapter);

        //初始化的一个状态
        tv_province.setText("请选择");

        mAddressAdapter.setOnItemClickListener(this);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (status) {
            startIndicatorAnimator(tv_province);
            status = false;
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_provnice:

                startIndicatorAnimator((TextView) view);

                break;

            case R.id.tv_city:
                startIndicatorAnimator((TextView) view);
                break;

            case R.id.tv_country:

                startIndicatorAnimator((TextView) view);
                break;
        }
    }

    void startIndicatorAnimator(final TextView view) {


        ObjectAnimator objectAnimator = new ObjectAnimator().ofFloat(indicator, "X", indicator.getX(), view.getX());

        LogHelper.i("indicator.getWidth()==>" + indicator.getWidth() + "view.getWidth()==>" + view.getWidth());

        ValueAnimator withAnimator = new ValueAnimator().ofInt(indicator.getMeasuredWidth(), view.getMeasuredWidth());

        final ViewGroup.LayoutParams params = indicator.getLayoutParams();

        withAnimator.addUpdateListener(valueAnimator -> {

            params.width = (int) valueAnimator.getAnimatedValue();
            indicator.setLayoutParams(params);
            Log.i("wk", "value==>" + valueAnimator.getAnimatedValue());

        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new FastOutSlowInInterpolator());
        animatorSet.playTogether(objectAnimator, withAnimator);
        animatorSet.start();


    }

    @Override
    public void onItemClick(ViewHolder viewHolder, Address address, int position) {

        App.showToast(address.getName());

        //记录所选择的 省 市 县 然后更新到tab 并开始下一项的选择


    }

    @Override
    public boolean onItemLongClick(ViewHolder viewHolder, Address address, int position) {
        return false;
    }


    class AddressAdapter extends RecyclerAdapter<Address> implements FastScrollRecyclerView.SectionedAdapter {

        public AddressAdapter(List<Address> mDatas, Context mContext, RecyclerView mRecyclerView) {
            super(mDatas, mContext, mRecyclerView);
        }

        @Override
        public ViewHolder createHolder(ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_address, parent, false);

            ViewHodler viewHodler = new ViewHodler(itemView);

            return viewHodler;
        }

        @NonNull
        @Override
        public String getSectionName(int position) {

            //取出第一个字的信息
            return mDatas.get(position).getName().substring(0, 1);
        }
    }


    class ViewHodler extends ViewHolder<Address> {
        @BindView(R.id.tv_name)
        TextView tv_name;

        public ViewHodler(View itemView) {
            super(itemView);

        }

        @Override
        protected void onBindWidget(Address address) {

            tv_name.setText(address.getName());

        }
    }


}
