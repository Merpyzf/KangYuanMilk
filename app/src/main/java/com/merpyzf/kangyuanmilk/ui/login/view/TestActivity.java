package com.merpyzf.kangyuanmilk.ui.login.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.BaseActivity;
import com.merpyzf.kangyuanmilk.utils.image.GlideImageLoader;
import com.merpyzf.kangyuanmilk.utils.image.ImageLoaderOptions;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Administrator on 2017-07-17.
 */

public class TestActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ArrayList<String> mDatas = new ArrayList<>();


    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    public void initWidget() {

    }

    @Override
    public void initEvent() {


    }

    @Override
    protected void initData() {

        for(int i=0;i<100;i++){

            mDatas.add(i,"test==>"+i);

        }

        MyAdapter myAdapter = new MyAdapter(mDatas, this,recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);
    }
}
