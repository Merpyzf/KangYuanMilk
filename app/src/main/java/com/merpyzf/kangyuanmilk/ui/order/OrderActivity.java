package com.merpyzf.kangyuanmilk.ui.order;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 订单展示 2017-08-15
 * @author wangke
 */
public class OrderActivity extends BaseActivity {

    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.coordinator_Layout)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    String[] mTitles = new String[]{"配送中", "待付款", "已完成", "已取消"};
    private List<Fragment> mFragmentList = new ArrayList<>();


    public static void showAction(Context context) {

        context.startActivity(new Intent(context, OrderActivity.class));

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order;
    }

    @Override
    public void initWidget() {
        mTabLayout.setupWithViewPager(mViewPager);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(R.string.title_order_activity_text);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void initData() {

        for (int i = 0; i < mTitles.length; i++) {

            mFragmentList.add(new OrderFragment(mTitles[i]));

        }

        mViewPager.setAdapter(new pagerAdapter(getSupportFragmentManager()));

    }


    class pagerAdapter extends FragmentPagerAdapter {

        public pagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }


        @Override
        public CharSequence getPageTitle(int position) {

            return mTitles[position];
        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                break;

        }

        return true;
    }
}
