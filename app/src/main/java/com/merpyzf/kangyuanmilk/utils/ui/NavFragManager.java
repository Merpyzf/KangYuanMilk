package com.merpyzf.kangyuanmilk.utils.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;

import com.merpyzf.kangyuanmilk.utils.LogHelper;

/**
 * Created by wangke on 2017-08-05.
 * 主页Fragment的调度管理
 */

public class NavFragManager {


    private Context context;
    private SparseArray<Tab> mTabs = null;
    private FragmentManager mFragManager = null;
    private int container;
    private Tab mCurrent = null;

    public NavFragManager(Context context, int container, FragmentManager mFragManager) {
        this.context = context;
        this.mFragManager = mFragManager;
        this.container = container;
        mTabs = new SparseArray<>();
    }

    public NavFragManager add(int navMenuId, Tab tab) {
        mTabs.put(navMenuId, tab);
        return this;
    }

    /**
     * 初始化主页(add添加完成之后调用)
     */
    public void initHome(int navMenuId) {

        if (mCurrent == null) {

            Tab tab = mTabs.get(navMenuId);


            if (tab != null) {

                FragmentTransaction transaction = mFragManager.beginTransaction();

                if (tab.fragment == null) {

                    Fragment fragment = Fragment.instantiate(context, tab.clazz.getName());
                    transaction.add(container, fragment);
                    tab.fragment = fragment;

                } else {

                    transaction.attach(tab.fragment);

                }

                transaction.commit();

            }

            mCurrent = tab;

        }

    }


    public void performClickNavMenu(int navMenuId) {

        Tab oldTab = null;

        if (mCurrent != null) {

            oldTab = mCurrent;
        }


        //新点击的tab
        Tab newTab = mTabs.get(navMenuId);
        if (newTab != null) {

            if (newTab == oldTab) {

                LogHelper.i("两次点击的是同一个");
                return;
            }


            doRealNavMenuSelect(oldTab, newTab);
        }


        mCurrent = newTab;

    }

    /**
     * 执行真正的切换操作
     *
     * @param oldTab 上一个tab
     * @param newTab 新点击的tab
     */
    private void doRealNavMenuSelect(Tab oldTab, Tab newTab) {

        FragmentTransaction transaction = mFragManager.beginTransaction();

        if (oldTab != null) {

            if (oldTab.fragment != null) {

                mFragManager.beginTransaction().detach(oldTab.fragment).commit();

            }
        }

       if(newTab.fragment==null){

            Fragment fragment = Fragment.instantiate(context,newTab.clazz.getName());
            newTab.fragment = fragment;
           mFragManager.beginTransaction().add(container,fragment).commit();


       }else {

           mFragManager.beginTransaction().attach(newTab.fragment).commit();

       }



    }

    /**
     * 获取当前所在的Fragment
     * @return 返回当前的fragment
     */
    public Fragment getCurrentTab(){
        return mCurrent.fragment;
    }



    public static class Tab {

        private int navMenuId;
        private Class clazz;
        public Fragment fragment;

        public Tab( Class clazz,int navMenuId) {
            this.navMenuId = navMenuId;
            this.clazz = clazz;
        }
    }

}
