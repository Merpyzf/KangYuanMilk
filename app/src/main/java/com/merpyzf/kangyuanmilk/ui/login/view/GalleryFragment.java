package com.merpyzf.kangyuanmilk.ui.login.view;


import android.app.Dialog;
import android.app.LoaderManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.widget.GalleryView;
import com.merpyzf.kangyuanmilk.utils.ui.TransStatusBottomSheetDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 图片选择器显示的BottomSheetDialogFragment
 * @author wangke
 */
public class GalleryFragment extends BottomSheetDialogFragment {

    @BindView(R.id.galleryView)
    GalleryView galleryView;
    private LoaderManager loaderManager;
    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        unbinder = ButterKnife.bind(this, view);

        loaderManager = getActivity().getLoaderManager();
        //需要从外部传入一个loaderManager对象进去，用于从数据库中读取图片,注意使用完之后一定要进行销毁
        galleryView.init(getContext(), loaderManager);
        galleryView.setMaxSelected(1);

        return  view;


}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //使用自己定义的dialog样式,避免顶部状态栏出现被dialog覆盖而变成黑色
        return new TransStatusBottomSheetDialog(getContext());
    }

    @Override
    public void onDestroyView() {

        //销毁LoadManager
        galleryView.destory();
        //取消绑定
        unbinder.unbind();
        super.onDestroyView();

    }

    /**
     * 拍照结束时的一个回调,真实的调用发生在开启这个Fragment的Activity,需要在Activity中手动
     * 调用onActivityResult方法
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //刷新GalleryView将拍摄的图片加载进去
        galleryView.updatePhoto();

    }
}
