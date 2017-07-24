package com.merpyzf.kangyuanmilk.common;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wangke on 17-7-16.
 */

public abstract class BaseFragment extends RxFragment {

    private View mRootView = null;
    private Unbinder unbinder;

    /**
     * 当Fragment添加到Activity的回调
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initArgs(getArguments());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(mRootView!=null){

            if(mRootView.getParent()!=null){
                //移除当前的view,避免一个view多次加载到一个相同的父布局
                ((ViewGroup)mRootView.getParent()).removeView(mRootView);

            }

        }else {

            mRootView = inflater.inflate(getContentLayoutId(), container, false);

        }


        if(unbinder == null) {

            unbinder = ButterKnife.bind(this, mRootView);

        }

        initWidget(mRootView);
        initEvent();
        return mRootView;
    }



    /**
     * 获取传递过来的参数
     * @param arguments
     */
    protected void initArgs(Bundle arguments) {

    }


    /**
     * 获取Fragment的布局id
     * @return
     */
    protected abstract int getContentLayoutId();

    /**
     * 控件的初始化
     * @param rootview
     */
    protected abstract void initWidget(View rootview);
    /**
     * 事件的初始化
     */
    protected abstract void initEvent();


    /**
     * 当View创建成功时调用
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //初始化数据
        initData();

    }


    /**
     * 初始化数据
     */
    protected abstract void initData();




    /**
     * 当View销毁的时候取消注解的绑定
     */
    @Override
    public void onDestroyView() {

        if(unbinder!=null) {
            unbinder.unbind();
            unbinder = null;
        }

        super.onDestroyView();

    }

    /**
     * fragment内部是否自行处理返回事件
     * @return ture : 处理
     *         false ： 不处理
     */
    public boolean onBackPressed(){

        return false;
    }
}
