package com.merpyzf.kangyuanmilk.test;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.AdapterDiffCallback;
import com.merpyzf.kangyuanmilk.common.widget.RecyclerAdapter;
import com.merpyzf.kangyuanmilk.common.widget.ViewHolder;
import com.merpyzf.kangyuanmilk.utils.LogHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StudyRecyclerw2viewActivity extends AppCompatActivity {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.btn_add)
    Button btn_add;

    private Unbinder unbinder;

    List<String> mLastDatas = null;
    List<String> mNewDatas = null;
    private MyAdapter mAdapter;
    private ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_recycler_view);
        unbinder = ButterKnife.bind(this);
        initData();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyAdapter(mLastDatas, this, recyclerView);
        recyclerView.setAdapter(mAdapter);


        btn_add.setOnClickListener(view -> {


            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new AdapterDiffCallback(mLastDatas, mNewDatas));


            diffResult.dispatchUpdatesTo(mAdapter);

        });


        recyclerView.addOnItemTouchListener(new MyOnItemTouchListener() {
            @Override
            void onItemClick(ViewHolder<String> holder) {

                LogHelper.i("onItemClick==>" + holder.getData());

            }

            @Override
            void onLongItemClick(ViewHolder<String> holder) {

                LogHelper.i("onItemClick==>" + holder.getData());

            }
        });

        itemTouchHelper = new ItemTouchHelper(new MyItemTouchCallbak());

        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    /**
     * RecyclerView实现item的点击监听
     */
    abstract class MyOnItemTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetectorCompat mGestureDetectorCompat = null;

        public MyOnItemTouchListener() {

            mGestureDetectorCompat = new GestureDetectorCompat(recyclerView.getContext(), new ItemTouchHelperGestureListener());

        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            return mGestureDetectorCompat.onTouchEvent(e);
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }

        abstract void onItemClick(ViewHolder<String> holder);

        abstract void onLongItemClick(ViewHolder<String> holder);

        private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {


            @Override
            public boolean onSingleTapUp(MotionEvent e) {


                View childViewUnder = recyclerView.findChildViewUnder(e.getX(), e.getY());
                MyHolder viewHolder = (MyHolder) recyclerView.getChildViewHolder(childViewUnder);
                onItemClick(viewHolder);

                return true;

            }

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);

                View childViewUnder = recyclerView.findChildViewUnder(e.getX(), e.getY());
                MyHolder viewHolder = (MyHolder) recyclerView.getChildViewHolder(childViewUnder);

                if (viewHolder.getAdapterPosition() != mLastDatas.size() - 1) {
                    itemTouchHelper.startDrag(viewHolder);
                }

                onLongItemClick(viewHolder);

            }
        }

    }


    class MyItemTouchCallbak extends ItemTouchHelper.Callback {

        //通过返回值来设置是否处理某次拖拽或滑动事件
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

            //如果是列表类型的RecyclerView拖拽只有UP,DOWN两个方向
            //如果是网格类型则有UP DOWN LEFT RIGHT 四个方法

            if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {

                //拖拽标志
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                //滑动标志
                int swipeFlags = 0;
                return makeFlag(dragFlags, swipeFlags);

            } else if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int swipeFlags = 0;

                return makeMovementFlags(dragFlags, swipeFlags);

            } else {


                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                //注意：和拖曳的区别就是在这里
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags);

            }

        }

        //当长按并进入拖拽状态时，拖拽的过程中不断地回调此方法
        //如果设置了相关的dragFlag,那么当长按item的时候,就会进入拖拽，并在拖拽的过程中不断回调 onMove方法
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {


            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();

            //交换数据的位置
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {

                    Collections.swap(mAdapter.getDatas(), i, i + 1);

                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(mAdapter.getDatas(), i, i - 1);
                }
            }

            //刷新item的位置
            mAdapter.notifyItemMoved(fromPosition, toPosition);
            return true;

        }


        //滑动删除的回调
        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            LogHelper.i("==>onSwipe");

            int adapterPosition = viewHolder.getAdapterPosition();
            mAdapter.notifyItemRemoved(adapterPosition);
            mAdapter.getDatas().remove(adapterPosition);

        }


        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            super.onSelectedChanged(viewHolder, actionState);
            LogHelper.i("==>onSelectedChanged");
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                //给被拖曳的 item 设置一个深颜色背景
                viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
            }

        }


        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            LogHelper.i("==>clearView");

            viewHolder.itemView.setBackgroundColor(Color.WHITE);


        }

        @Override
        public boolean isLongPressDragEnabled() {

            return false;
        }
    }


    private void initData() {

        mLastDatas = new ArrayList<>();
        mNewDatas = new ArrayList<>();


        for (int i = 0; i < 30; i++) {

            mLastDatas.add("batman==>" + i);

            mNewDatas.add("batman==>" + i);

        }
        mNewDatas.add("添加的数据");


    }

    class MyAdapter extends RecyclerAdapter<String> {

        public MyAdapter(List<String> mDatas, Context mContext, RecyclerView mRecyclerView) {
            super(mDatas, mContext, mRecyclerView);
        }

        @Override
        public ViewHolder createHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_test, parent, false);

            return new MyHolder(view);
        }
    }


    class MyHolder extends ViewHolder<String> {

        @BindView(R.id.tv_test)
        TextView tv_test;

        public MyHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBindWidget(String s) {

            tv_test.setText(s);

        }
    }


    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
