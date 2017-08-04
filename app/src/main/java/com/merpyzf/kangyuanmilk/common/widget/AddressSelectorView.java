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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.bean.Address;
import com.merpyzf.kangyuanmilk.utils.db.dao.AddressDao;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by wangke on 2017-07-30.
 * 仿照 有物app 地址选择组件开发,移除了一些自己不喜欢的功能
 */

public class AddressSelectorView extends LinearLayout implements View.OnClickListener, RecyclerAdapter.ItemClickListener<Address> {
    //省
    private TextView tv_province = null;
    //市
    private TextView tv_city = null;
    //县
    private TextView tv_country = null;
    //镇
    private TextView tv_town = null;
    //指示器
    private View indicator = null;
    private FastScrollRecyclerView recyclerView;
    //布局状态
    private boolean layoutStatus = true;
    private AddressAdapter mAddressAdapter;
    //记录所选的地址
    private Address provinceAddress = null; //省
    private Address cityAddress = null;
    private Address countryAddress = null;
    private Address townAddress = null;
    //记录tab所选的状态
    private static final int PROVINCE = 0;
    private static final int CITY = 1;
    private static final int COUNTRY = 2;
    private static final int TOWN = 3;
    //记录当前所选中的tab
    private int currentChoice = PROVINCE;
    private OnAddressSelectListenter mOnAddressSelectListenter = null;

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
     * 初始化布局,组件,填充 view
     */
    private void init(Context context) {
        setOrientation(VERTICAL);

        LayoutParams param = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);

        setLayoutParams(param);


        View viewHeader = LayoutInflater.from(context)
                .inflate(R.layout.view_address_header, this, false);
        addView(viewHeader);
        View viewContent = LayoutInflater.from(context)
                .inflate(R.layout.view_address_content, this, false);
        addView(viewContent);

        tv_province = viewHeader.findViewById(R.id.tv_provnice);
        tv_province.setOnClickListener(this);

        tv_city = viewHeader.findViewById(R.id.tv_city);
        tv_city.setOnClickListener(this);

        tv_country = viewHeader.findViewById(R.id.tv_country);
        tv_country.setOnClickListener(this);

        tv_town = viewHeader.findViewById(R.id.tv_town);
        tv_town.setOnClickListener(this);

        indicator = viewHeader.findViewById(R.id.indicator);
        recyclerView = viewContent.findViewById(R.id.recycler_address);

        //设置recyclerView的布局方式
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //首次打开地址选择器的时候,默认显示全国各个省份的信息
        mAddressAdapter = new AddressAdapter(AddressDao.getInstance().getAddressList(0), getContext(), recyclerView);
        //监听recyclerView中item的点击事件
        mAddressAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(mAddressAdapter);

        //初始化省的状态
        tv_province.setText("请选择");
        tv_province.setClickable(false);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (layoutStatus) {
            //第一次布局的时候调整指示器的初始位置,只执行一次
            startIndicatorAnimator(tv_province);
            layoutStatus = false;
        }
    }

    /**
     * 顶部tab的点击事件监听
     * 展示城市列表
     *
     * @param view view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_provnice:

                startIndicatorAnimator((TextView) view);
                //根据获取view中存放的当前的tab已选的address的信息
                if (view.getTag() != null) {
                    List<Address> provniceAddressList = AddressDao.getInstance().getAddressList((int) view.getTag());
                    mAddressAdapter.resetData(provniceAddressList);
                    //选择的那个地址移动的recyclerView中开始位置
                    moveToPosition(getAddressIndex(provniceAddressList, provinceAddress), recyclerView);
                }

                //记录当前选中的tab是哪一个
                currentChoice = PROVINCE;
                break;

            case R.id.tv_city:
                startIndicatorAnimator((TextView) view);

                if (view.getTag() != null) {
                    List<Address> cityAddressList = AddressDao.getInstance().getAddressList((int) view.getTag());
                    mAddressAdapter.resetData(cityAddressList);
                    moveToPosition(getAddressIndex(cityAddressList, cityAddress), recyclerView);
                }

                currentChoice = CITY;
                break;

            case R.id.tv_country:

                startIndicatorAnimator((TextView) view);
                if (view.getTag() != null) {

                    List<Address> countryAddressList = AddressDao.getInstance().getAddressList((int) view.getTag());


                    mAddressAdapter.resetData(countryAddressList);
                    int index = getAddressIndex(countryAddressList, countryAddress);
                    moveToPosition(index, recyclerView);

                }

                currentChoice = COUNTRY;
                break;

            case R.id.tv_town:

                startIndicatorAnimator((TextView) view);

                if (view.getTag() != null) {
                    List<Address> townAddressList = AddressDao.getInstance().getAddressList((int) view.getTag());
                    mAddressAdapter.resetData(townAddressList);

                    int index = getAddressIndex(townAddressList, townAddress);
                    moveToPosition(index, recyclerView);

                }

                currentChoice = TOWN;
                break;


        }
    }

    /**
     * item点击事件的兼听，包含了地址选择器的主要逻辑
     *
     * @param viewHolder item对应的viewHolder
     * @param address    address对象
     * @param position   position位置下标
     */
    @Override
    public void onItemClick(ViewHolder viewHolder, Address address, int position) {

        //根据所选的address一级一级向下获取地址列表，直到获取到的城市的数量为0结束
        List<Address> addressList = AddressDao.getInstance().getAddressList(address.getId());
        mAddressAdapter.resetData(addressList);
        switch (currentChoice) {

            case PROVINCE:

                tv_province.setTag(0);
                provinceAddress = address;
                //设置当前所选的tab的名字
                tv_province.setText(address.getName());
                //当前点击的item所选择的地址将作为下一个tab的tag值
                tv_city.setTag(address.getId());
                tv_city.setText("请选择");
                //默认不可见
                tv_city.setVisibility(VISIBLE);
                startIndicatorAnimator(tv_city);
                //当第一个tab(省级)条目点击过了之后才设置为可点击状态
                tv_province.setClickable(true);
                //如果下级的已经选择过了就将其全部隐藏
                //如果重新选择省份，那么省一下的级别的地址全部设置为null,并且将省一下级别的所有tab隐藏
                if (cityAddress != null) {

                    tv_country.setVisibility(GONE);
                    tv_town.setVisibility(GONE);
                    cityAddress = null;
                    countryAddress = null;
                    townAddress = null;

                }

                currentChoice = CITY;
                break;


            case CITY:

                cityAddress = address;
                tv_city.setText(address.getName());
                tv_country.setTag(address.getId());

                //当重新选择城市的时候执行下面的逻辑
                if (countryAddress != null) {

                    tv_town.setVisibility(GONE);

                    countryAddress = null;
                    townAddress = null;

                }
                //当选择到city这一个级别后面就没有了
                if (addressList.size() == 0) {

                    if (mOnAddressSelectListenter != null) {

                        mOnAddressSelectListenter.onSelected(provinceAddress.getName() + " " +
                                cityAddress.getName(), cityAddress.getId());

                    }

                    startIndicatorAnimator(tv_city);
                    return;
                }

                //city后面还有的情况
                tv_country.setText("请选择");
                tv_country.setVisibility(VISIBLE);
                startIndicatorAnimator(tv_country);


                currentChoice = COUNTRY;
                break;


            case COUNTRY:

                countryAddress = address;
                tv_country.setText(address.getName());
                tv_town.setTag(address.getId());


                //当重新选择城市的时候执行下面的逻辑
                if (townAddress != null) {

                    townAddress = null;

                }


                if (addressList.size() == 0) {

                    if (mOnAddressSelectListenter != null) {

                        mOnAddressSelectListenter.onSelected(provinceAddress.getName() + " " +
                                        cityAddress.getName() + " " + countryAddress.getName(),
                                countryAddress.getId());
                    }

                    startIndicatorAnimator(tv_country);
                    return;
                }

                tv_town.setText("请选择");
                tv_town.setVisibility(VISIBLE);
                startIndicatorAnimator(tv_town);

                currentChoice = TOWN;

                break;


            case TOWN:
                //最后的一个地区等级,再往后就没有了
                tv_town.setText(address.getName());
                startIndicatorAnimator(tv_town);
                townAddress = address;

                if (mOnAddressSelectListenter != null) {

                    mOnAddressSelectListenter.onSelected(provinceAddress.getName() + " " +
                            cityAddress.getName() + " " + countryAddress.getName() + " " +
                            townAddress.getName(), townAddress.getId());

                }

                break;
        }


    }

    @Override
    public boolean onItemLongClick(ViewHolder viewHolder, Address address, int position) {
        return false;
    }
    /**
     * 创建指示器运动的动画
     *
     * @param view 待移动的view
     */
    private void startIndicatorAnimator(final TextView view) {

        //保证view layout布局完成之后,才开始执行更新指示器的动画
        view.post(() -> {

            //位置
            ObjectAnimator objectAnimator = ObjectAnimator.
                    ofFloat(indicator, "X", indicator.getX(), view.getX());
            //宽度
            ValueAnimator withAnimator = ValueAnimator
                    .ofInt(indicator.getMeasuredWidth(), view.getMeasuredWidth());

            final ViewGroup.LayoutParams params = indicator.getLayoutParams();

            withAnimator.addUpdateListener(valueAnimator -> {
                //动态根据tab的宽度更新指示器的宽度
                params.width = (int) valueAnimator.getAnimatedValue();
                indicator.setLayoutParams(params);

            });

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setInterpolator(new FastOutSlowInInterpolator());
            animatorSet.playTogether(objectAnimator, withAnimator);
            animatorSet.start();
        });


    }
    /**
     * 将RecyclerView中的item移动到指定的位置
     *
     * @param position     要移动的位置
     * @param recyclerView recyclerview
     */
    private void moveToPosition(int position, RecyclerView recyclerView) {

        LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();

        //先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
        int firstItem = manager.findFirstVisibleItemPosition();
        int lastItem = manager.findLastVisibleItemPosition();
        //然后区分情况
        if (position <= firstItem) {

            //当要置顶的项在当前显示的第一个项的前面时
            recyclerView.scrollToPosition(position);
        } else if (position <= lastItem) {

            //当要置顶的项已经在屏幕上显示时
            int top = recyclerView.getChildAt(position - firstItem).getTop();
            recyclerView.scrollBy(0, top);
        } else {

            //当要置顶的项在当前显示的最后一项的后面时
            recyclerView.scrollToPosition(position);
            //这里这个变量是用在RecyclerView滚动监听里面的
        }

    }
    /**
     * 获取address对象在存放地址的集合中所对应的index下标值
     *
     * @param addressList 当前recyclerView显示的地址的集合
     * @param address     要获取下标位置的address对象
     * @return 下标
     */
    private int getAddressIndex(List<Address> addressList, Address address) {
        int index = 0;

        if (address != null) {

            for (int i = 0; i < addressList.size(); i++) {

                int id = addressList.get(i).getId();

                if (id == address.getId()) {

                    return index;
                }

                index++;

            }
        }

        return 0;
    }

    class AddressAdapter extends RecyclerAdapter<Address> implements FastScrollRecyclerView.SectionedAdapter {

        private AddressAdapter(List<Address> mDatas, Context mContext, RecyclerView mRecyclerView) {
            super(mDatas, mContext, mRecyclerView);
        }

        @Override
        public ViewHolder createHolder(ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_address, parent, false);

            return new ViewHodler(itemView);
        }

        /**
         * FastScrollRecyclerView 显示选择菜单的第一个文字
         *
         * @param position position下标
         * @return 要显示的文字
         */
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

        ViewHodler(View itemView) {
            super(itemView);

        }

        @Override
        protected void onBindWidget(Address address) {

            tv_name.setText(address.getName());

        }
    }

    /**
     * 地址选择完成后的回调监听
     */
    public interface OnAddressSelectListenter {

        void onSelected(String address, int id);

    }

    public void setOnAddressSelectListenter(OnAddressSelectListenter selectListenter) {

        mOnAddressSelectListenter = selectListenter;

    }
}
