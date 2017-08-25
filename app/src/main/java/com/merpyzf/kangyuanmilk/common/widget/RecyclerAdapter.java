package com.merpyzf.kangyuanmilk.common.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.bean.PageBean;

import java.util.List;

/**
 * Created by wangke on 17-7-16.
 * 封装RecclerView,简化开发中adpter的书写
 */

public abstract class RecyclerAdapter<Data> extends RecyclerView.Adapter<ViewHolder<Data>> implements View.OnClickListener, View.OnLongClickListener {

    protected List<Data> mDatas;
    protected Context mContext;
    protected ItemClickListener mItemClickListener = null;
    protected AutoLoadListener mAutoLoadListener = null;
    protected RecyclerView mRecyclerView;
    private LinearLayoutManager manager = null;
    protected boolean isAutoLoadStatus = true;
    private boolean isOpenAutoLoad = false;
    private View mItemView;
    private PageBean mPageBean;


    public RecyclerAdapter(List<Data> mDatas, Context mContext, final RecyclerView mRecyclerView) {

        this.mDatas = mDatas;
        this.mContext = mContext;
        this.mRecyclerView = mRecyclerView;
        this.mPageBean = new PageBean();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //创建ViewHolder

        ViewHolder viewHolder = createHolder(parent, viewType);
        mItemView = viewHolder.getItemView();

        mItemView.setOnClickListener(this);
        mItemView.setOnLongClickListener(this);
        //给每一个itemview存储对应着的ViewHolder
        mItemView.setTag(R.id.tag_holder, viewHolder);

        return viewHolder;
    }

    /**
     * 将数据绑定到ViewHolder中的控件
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //给ViewHodler设置数据
        holder.bind(mDatas.get(position), position);


        if (isOpenAutoLoad) {

            if (mRecyclerView != null) {

                manager = (LinearLayoutManager) mRecyclerView.getLayoutManager();

                if (manager != null) {

                    mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                            super.onScrollStateChanged(recyclerView, newState);


                        }

                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            int itemCount = manager.getItemCount();

                            int lastVisibleItemPosition = manager.findLastVisibleItemPosition();

                            Log.i("wk", "当前滑动的状态: " + "itemCount: " + itemCount + "lastVisibleItemPosition: " + lastVisibleItemPosition);

                            // 当距离滑动到底部还有三条数据的时候,开始进行数据的预先加载
                            if (lastVisibleItemPosition > itemCount - 3) {

                                if (isAutoLoadStatus) {

                                    Log.i("wk", "开始预先加载数据==>" + lastVisibleItemPosition);

                                    //设置标记位，避免多次加载0
                                    isAutoLoadStatus = false;
                                    //调用预先加载的方法
                                    if (mAutoLoadListener != null) {
                                        mAutoLoadListener.startLoading(lastVisibleItemPosition);
                                    }

                                }


                            }
                        }
                    });
                }

            }

        }
    }


    public void setDatas(List<Data> mDatas) {
        this.mDatas = mDatas;
    }

    /**
     * 获取item的总数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * 子类实现创建ViewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    public abstract ViewHolder createHolder(ViewGroup parent, int viewType);


    /**
     * 手指点击的事件回调
     *
     * @param view
     */
    @Override
    public void onClick(View view) {

        if (mItemClickListener != null) {

            ViewHolder<Data> viewHolder = (ViewHolder) view.getTag(R.id.tag_holder);
            Data data = viewHolder.getData();
            int position = viewHolder.getAdapterPosition();

            mItemClickListener.onItemClick(viewHolder, data, position);
        }

    }

    /**
     * 手指长按的事件回调
     *
     * @param view
     * @return
     */
    @Override
    public boolean onLongClick(View view) {

        if (mItemClickListener != null) {

            ViewHolder<Data> viewHolder = (ViewHolder) view.getTag(R.id.tag_holder);
            Data data = viewHolder.getData();
            int position = viewHolder.getAdapterPosition();

            return mItemClickListener.onItemLongClick(viewHolder, data, position);
        }


        return false;
    }


    /**
     * RecyclerView中条目的更新删除等操作的封装
     */

    /**
     * 更新指定位置的值
     *
     * @param position
     * @param data
     */
    public void updateItem(int position, Data data) {
        mDatas.remove(position);
        mDatas.add(position, data);
        notifyItemChanged(position);

    }

    /**
     * 插入到指定位置
     *
     * @param position
     * @param data
     */
    public void insertItem(int position, Data data) {

        mDatas.add(position, data);
        notifyItemInserted(position);
    }

    /**
     * 刷新指定位置
     *
     * @param position
     */
    public void updateItem(int position) {
        notifyItemChanged(position);
    }


    /**
     * 根据传入的对象找到位置并刷新view
     *
     * @param data
     */
    public void updateItem(Data data) {

        for (int i = 0; i < mDatas.size(); i++) {

            if (mDatas.get(i) == data) {

                notifyItemChanged(i);

            }

        }


    }


    /**
     * 重新设置数据
     *
     * @param dataList
     */
    public void resetData(List<Data> dataList) {

        mDatas.clear();
        mDatas.addAll(dataList);

        notifyDataSetChanged();
    }

    /**
     * 向尾部添加一条数据
     *
     * @param data
     */
    public void addData(Data data) {

        mDatas.add(data);
        notifyItemChanged(mDatas.size() - 1);

    }

    /**
     * 向尾部添加多条数据
     *
     * @param dataList
     */
    public void addDatas(List<Data> dataList) {

        int start = mDatas.size();
        mDatas.addAll(dataList);
        int end = mDatas.size() - 1;

        if (dataList.size() == 1) {
            notifyItemChanged(0);

        } else {

            notifyItemRangeChanged(start, end);
        }


    }


    /**
     * 移除一条数据
     *
     * @param data
     */
    public void remove(Data data) {

        mDatas.remove(data);
        notifyDataSetChanged();

    }

    /**
     * 清空所有数据
     */
    public void clearData() {

        mDatas.clear();
        notifyDataSetChanged();
    }


    public PageBean getPageBean() {
        return mPageBean;
    }

    public View getItemView() {

        return mItemView;
    }

    /**
     * 设置完成自动预加载
     */
    public void setAutoLoadCompleted() {
        isAutoLoadStatus = true;
    }


    /**
     * 设置item点击事件的兼听
     * todo 这里泛型有点问题！！！
     *
     * @param <Data>
     */
    public interface ItemClickListener<Data> {
        void onItemClick(ViewHolder viewHolder, Data data, int position);

        boolean onItemLongClick(ViewHolder viewHolder, Data data, int position);
    }

    public void setOnItemClickListener(ItemClickListener<Data> itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    /**
     * 设置预先加载的事件监听(成功加载之后调用setAutoLoadCompleted方法将标记位重置为true)
     */
    public interface AutoLoadListener {
        void startLoading(int position);
    }

    public void setOnAutoLoadListener(AutoLoadListener autoLoadListener) {
        this.mAutoLoadListener = autoLoadListener;
    }

    public List<Data> getDatas() {
        return mDatas;
    }
}
