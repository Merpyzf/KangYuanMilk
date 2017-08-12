package com.merpyzf.kangyuanmilk.ui.home;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.BaseFragment;
import com.merpyzf.kangyuanmilk.common.data.Response;
import com.merpyzf.kangyuanmilk.ui.adapter.HomeAdapter;
import com.merpyzf.kangyuanmilk.utils.LogHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 首页
 *
 * @author wangke
 */
public class HomeFragment extends BaseFragment {

    private List<String> mDatas = new ArrayList<>();
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initWidget(View rootview) {

        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);



    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        mDatas.clear();
        mDatas.add("haha");


        Response response = new Response();

        List dataList = new ArrayList<Response.Data>();


        Response.Data dataMilk = new Response.Data();
        List<Response.DataInfo> dataInfoList = new ArrayList<>();


        //顶部Banner
        Response.Data newHeader = new Response.Data();
        List<Response.DataInfo> newHeaderList = new ArrayList<>();

        Response.DataInfo HeaderInfo = new Response.DataInfo();
        HeaderInfo.setId(1);
        HeaderInfo.setTitle("标题1");
        HeaderInfo.setImageview("http://otdmrup4y.bkt.clouddn.com/1.png");
        newHeaderList.add(HeaderInfo);

        Response.DataInfo HeaderInfo1 = new Response.DataInfo();
        HeaderInfo1.setId(2);
        HeaderInfo1.setTitle("标题2");
        HeaderInfo1.setImageview("http://otdmrup4y.bkt.clouddn.com/10.png");
        newHeaderList.add(HeaderInfo1);


        Response.DataInfo HeaderInfo2 = new Response.DataInfo();
        HeaderInfo2.setId(3);
        HeaderInfo2.setTitle("标题3");
        HeaderInfo2.setImageview("http://otdmrup4y.bkt.clouddn.com/11.png");
        newHeaderList.add(HeaderInfo2);

        newHeader.setDataInfoList(newHeaderList);
        newHeader.setType("HEADER_BANNER");



        //最新活动
        Response.Data newActivity = new Response.Data();
        List<Response.DataInfo> newActivityList = new ArrayList<>();

        Response.DataInfo newActivityInfo = new Response.DataInfo();
        newActivityInfo.setId(1);
        newActivityInfo.setTitle("最新活动1");
        newActivityInfo.setImageview("http://otdmrup4y.bkt.clouddn.com/12.jpg");
        newActivityList.add(newActivityInfo);

        Response.DataInfo newActivityInfo1 = new Response.DataInfo();
        newActivityInfo1.setId(2);
        newActivityInfo1.setTitle("最新活动2");
        newActivityInfo1.setImageview("http://otdmrup4y.bkt.clouddn.com/13.png");
        newActivityList.add(newActivityInfo1);


        Response.DataInfo newActivityInfo2 = new Response.DataInfo();
        newActivityInfo2.setId(3);
        newActivityInfo2.setTitle("最新活动3");
        newActivityInfo2.setImageview("http://otdmrup4y.bkt.clouddn.com/2.jpg");
        newActivityList.add(newActivityInfo2);

        newActivity.setDataInfoList(newActivityList);
        newActivity.setType("NEW_ACTIVITY");



        Response.DataInfo dataInfo1 = new Response.DataInfo();
        dataInfo1.setId(1);
        dataInfo1.setTitle("牛奶1");
        dataInfo1.setImageview("http://otdmrup4y.bkt.clouddn.com/4.jpg");
        dataInfo1.setPrice("5");
        dataInfo1.setSpec("500ml");
        dataInfoList.add(dataInfo1);

        Response.DataInfo dataInfo2 = new Response.DataInfo();
        dataInfo2.setId(1);
        dataInfo2.setTitle("牛奶2");
        dataInfo2.setImageview("http://otdmrup4y.bkt.clouddn.com/5.jpg");
        dataInfo2.setPrice("4");
        dataInfo2.setSpec("500ml");
        dataInfoList.add(dataInfo2);


        Response.DataInfo dataInfo3 = new Response.DataInfo();
        dataInfo3.setId(1);
        dataInfo3.setTitle("牛奶3");
        dataInfo3.setImageview("http://otdmrup4y.bkt.clouddn.com/6.jpg");
        dataInfo3.setPrice("4");
        dataInfo3.setSpec("500ml");
        dataInfoList.add(dataInfo3);


        Response.DataInfo dataInfo4 = new Response.DataInfo();
        dataInfo4.setId(4);
        dataInfo4.setTitle("牛奶4");
        dataInfo4.setImageview("http://otdmrup4y.bkt.clouddn.com/7.png");
        dataInfo4.setPrice("4");
        dataInfo4.setSpec("500ml");
        dataInfoList.add(dataInfo4);

        dataMilk.setDataInfoList(dataInfoList);
        dataMilk.setType("HOT_PRODUCT");



        //新品
        Response.Data dataNewProduct = new Response.Data();
        List<Response.DataInfo> dataInfoListNew = new ArrayList<>();

        Response.DataInfo dataInfoNew1 = new Response.DataInfo();
        dataInfoNew1.setId(1);
        dataInfoNew1.setTitle("新品牛奶1");
        dataInfoNew1.setImageview("http://otdmrup4y.bkt.clouddn.com/8.jpg");
        dataInfoNew1.setPrice("5");
        dataInfoNew1.setSpec("500ml");
        dataInfoListNew.add(dataInfo1);

        Response.DataInfo dataInfoNew2 = new Response.DataInfo();
        dataInfoNew2.setId(1);
        dataInfoNew2.setTitle("新品牛奶2");
        dataInfoNew2.setImageview("http://otdmrup4y.bkt.clouddn.com/9.png");
        dataInfoNew2.setPrice("4");
        dataInfoNew2.setSpec("500ml");
        dataInfoListNew.add(dataInfo2);


        Response.DataInfo dataInfoNew3 = new Response.DataInfo();
        dataInfoNew3.setId(1);
        dataInfoNew3.setTitle("新品牛奶3");
        dataInfoNew3.setImageview("http://otdmrup4y.bkt.clouddn.com/1.png");
        dataInfoNew3.setPrice("4");
        dataInfoNew3.setSpec("500ml");
        dataInfoListNew.add(dataInfo3);

        dataNewProduct.setDataInfoList(dataInfoListNew);
        dataNewProduct.setType("NEW_PRODUCT");

        dataList.add(newHeader);
        dataList.add(newActivity);
        dataList.add(dataMilk);
        dataList.add(dataNewProduct);



        response.setData(dataList);

        recyclerView.setAdapter(new HomeAdapter(response.getData(), getContext(), recyclerView));
        mLayoutManager.scrollToPosition(0);

        Gson gson = new Gson();

        String json = gson.toJson(response);

        LogHelper.i("json====> " + json);

    }


    @Override
    public void onDestroyView() {
        LogHelper.i("onDestoryView==>");
        super.onDestroyView();


    }
}
