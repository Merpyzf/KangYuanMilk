package com.merpyzf.kangyuanmilk.common;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment的基类(懒加载)
 * Created by wangke on 17-6-19.
 */

public abstract class BaseLazyFragment extends Fragment {

    protected View mRoot = null; //缓存Fragment中的view
    protected Activity mActivity = null;

    /**
     * 判断Fragment的布局是否已经加载
     */
    protected  boolean isPrepare = false;

    /**
     * 是否显示布局
     */
    protected boolean isVisible = false;


    /**
     * 是否第一次加载
     */
    protected  boolean isFirst = false;


    /**
     * 当Fragment添加到Activity的时候最先调用此方法
     * @param context 上下文对象
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //获取Fragment之间传递过来的参数
        initArgs(getArguments());
        mActivity = (Activity) context;

        Log.i("wk","==>onAttach");

    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            //Fragment之间切换时每次都会调用onCreateView方法，导致每次Fragment的布局都重绘，无法保持Fragment原有状态
        if(mRoot == null) {
            View root = inflater.inflate(getContentLayoutId(), container, false);
            mRoot = root;

            //布局已经加载
            isPrepare = true;
            //第一次加载
            isFirst = true;
            /**
             * 初始化ui
             */
            initWidget(root);

        }else {

            if(mRoot.getParent()!=null){

                //把当前的布局从父View中进行移除,否则会发生这个rootView已经有了一个父View的错误
                ((ViewGroup)mRoot.getParent()).removeAllViews();

            }
        }

        Log.i("wk","==>onCreateView");

        return mRoot;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();

        Log.i("wk","==>onViewCreated");

    }



    protected abstract int getContentLayoutId();

    /**
     * 初始化控件
     * @param view
     */
    protected void initWidget(View view){

        //进行ButterKnife的绑定


    }

    /**
     * 初始化数据
     */
    protected  abstract void initData();

    /**
     * 初始化参数(获取传递过来的参数)
     * @param bundle
     */
    protected void initArgs(Bundle bundle){

    }


    /**
     * fragment处理返回键
     *
     * @return true : 表示fragment自己来处理当前的返回事件
     *         false : 表示将frgment返回的逻辑交给activity处理
     */
    protected boolean onBackPressed(){

        return false;

    }



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(getUserVisibleHint()){

            isVisible = true;
            onVisible();

        }else {

            isVisible = false;

        }




    }

    /**
     * 当前的Fragment处于可见状态
     */
    protected void onVisible(){

        lazy();
    }

    /**
     * 是否要进行懒加载的判断
     */
    protected  void lazy(){

        //当页面已经加载，并且用户已经滑动到要查看的页面,才进行数据的加载
        if(isPrepare && isVisible() && isFirst){

            lazyLoadData();

            isFirst = false;
        }

    }

    /**
     * 需要进行懒加载的数据,在这个方法中初始化
     */
    protected abstract void lazyLoadData();


}
