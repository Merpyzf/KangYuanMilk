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
    Toolbar toolBar;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.coordinator_Layout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    String[] titles = new String[]{"配送中", "待付款", "已完成", "已取消"};
    private List<Fragment> fragmentList = new ArrayList<>();


    public static void showAction(Context context) {

        context.startActivity(new Intent(context, OrderActivity.class));

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order;
    }

    @Override
    public void initWidget() {
        tabLayout.setupWithViewPager(viewPager);
        setSupportActionBar(toolBar);
        getSupportActionBar().setTitle("我的订单");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void initData() {

        for (int i = 0; i < titles.length; i++) {

            fragmentList.add(new OrderFragment(titles[i]));

        }

        viewPager.setAdapter(new pagerAdapter(getSupportFragmentManager()));

    }


    class pagerAdapter extends FragmentPagerAdapter {

        public pagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }


        @Override
        public CharSequence getPageTitle(int position) {

            return titles[position];
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
