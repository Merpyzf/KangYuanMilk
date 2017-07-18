package com.merpyzf.kangyuanmilk.common.widget;

import android.app.Activity;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.utils.image.GlideImageLoader;
import com.merpyzf.kangyuanmilk.utils.image.ImageLoaderOptions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wangke on 2017-07-18.
 * 图片选择器
 * 计划实现功能: 1. cell中的第一项默认为拍照,拍照成功后返回当前图片选择的页面
 *             2.滑动多选:
 *                  列值等于x轴移动的距离／每个item 的宽度
                    行值等于（RecyclerView滑动的距离＋手指滑动的距离－RecylerView距离顶部偏移）（一般不设置magin就是0，兼容多种情况）。
 *
 *
 */

public class GalleryView extends RecyclerView implements android.app.LoaderManager.LoaderCallbacks<Cursor>,RecyclerAdapter.ItemClickListener<GalleryView.Image> {

    private Context mContext;
    private static final int LOAD_IMAGE = 1;
    private List<Image> mImages = null;
    private GalleryAdapter mGalleryAdapter;

    public GalleryView(Context context) {
        super(context);
        init(context);
    }

    public GalleryView(Context context, @Nullable AttributeSet attrs) {
       super(context, attrs);
        init(context);
    }

    public GalleryView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);

    }

    private void init(Context context) {

        mContext = context;

        mImages = mImages == null? new ArrayList<Image>(): mImages;

        Loader<Cursor> loader = ((Activity) context).getLoaderManager().initLoader(LOAD_IMAGE, null, this);

        setLayoutManager(new GridLayoutManager(mContext,3));

        mGalleryAdapter = new GalleryAdapter(mImages, mContext, this);

        setAdapter(mGalleryAdapter);

        mGalleryAdapter.setOnItemClickListener(this);

    }


    @Override
    public android.content.Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        Log.i("wk","i==>"+i);

        if(i == LOAD_IMAGE) {

            Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            String[] projection = new String[]{MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA, MediaStore.Images.Media.DATE_ADDED};
           return new CursorLoader(mContext, uri, projection, null, null, MediaStore.Images.Media.DATE_ADDED+" DESC");
        }
        return null;
    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor cursor) {

        int count = 0;
        while (cursor.moveToNext()){

            Log.i("wk","count==>"+count++);

            String id = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
            String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
            String add_date = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED));

            File file = new File(path);

            Log.i("wk","文件长度:"+file.length());

            if(file!=null && file.length()>200) {

                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

                Date date = new Date(Long.valueOf(add_date));
                String sDate = dateFormat.format(date);
                Log.i("wk", "id==> " + id + "path==>" + path + "add_date==>" + sDate);

                Image image = new Image(id, path, sDate);
                mImages.add(image);
            }
        }

        //刷新适配器

        mGalleryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {

    }

    @Override
    public void onItemClick(com.merpyzf.kangyuanmilk.common.widget.ViewHolder viewHolder, Image image, int position) {

        Log.i("wk","当前选中的图片:"+image.getPath());

        //如果是选中状态就取消勾选
        if(image.isSelected){

            image.setSelected(false);

        }else {

            //进行选中图片
            image.setSelected(true);

        }

        //刷新当前选中的条目
        mGalleryAdapter.updataItem(position,image);

    }

    @Override
    public boolean onItemLongClick(com.merpyzf.kangyuanmilk.common.widget.ViewHolder viewHolder, Image image, int position) {
        return false;
    }


    /**
     * 封装图片信息
     */
    static class Image{

        private String id;
        //路径
        private String path;
        //添加时间
        private String add_date;
        //当前image是否被选中
        private boolean isSelected = false;

        public Image() {
        }

        public String getAdd_date() {
            return add_date;
        }

        public void setAdd_date(String add_date) {
            this.add_date = add_date;
        }

        public Image(String id, String path, String add_date) {
            this.id = id;
            this.path = path;
            this.add_date = add_date;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }

    class GalleryAdapter extends RecyclerAdapter<Image>{


        public GalleryAdapter(List<Image> mDatas, Context mContext, RecyclerView mRecyclerView) {
            super(mDatas, mContext, mRecyclerView);
        }

        @Override
        public com.merpyzf.kangyuanmilk.common.widget.ViewHolder createHolder(ViewGroup parent, int viewType) {

            View cellView = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_gallery,parent,false);

            GalleryHolder galleryHolder = new GalleryHolder(cellView);

            return galleryHolder;
        }

    }


    class GalleryHolder extends com.merpyzf.kangyuanmilk.common.widget.ViewHolder<Image>{

        @BindView(R.id.iv_gallery)
        ImageView iv_gallery;
        @BindView(R.id.view_cover)
        View view_cover;
        @BindView(R.id.cb_gallery)
        CheckBox cb_gallery;


        public GalleryHolder(View itemView) {
            super(itemView);

            iv_gallery = (ImageView) itemView.findViewById(R.id.iv_gallery);
            view_cover = (View)itemView.findViewById(R.id.view_cover);
            cb_gallery = (CheckBox) itemView.findViewById(R.id.cb_gallery);
        }

        @Override
        protected void onBindWidget(Image image) {

            ImageLoaderOptions.Bulider bulider = new ImageLoaderOptions.Bulider();


            ImageLoaderOptions options = bulider.isCenterCrop(true)
                    .errorDrawable(R.drawable.ic_default)
                    .isCrossFade(true)
                    .isSkipMemoryCache(true)
                    .size(new ImageLoaderOptions.ImageReSize(400,400))
                    .build();
            GlideImageLoader.showImage(iv_gallery,new File(image.getPath()),options);

            cb_gallery.setChecked(image.isSelected());

            if(image.isSelected){

                view_cover.setVisibility(VISIBLE);

            }else {

                view_cover.setVisibility(INVISIBLE);
            }


        }
    }


}
