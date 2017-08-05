package com.merpyzf.kangyuanmilk.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.widget.RecyclerAdapter;
import com.merpyzf.kangyuanmilk.common.widget.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StudyRecycler1ViewActivity extends AppCompatActivity {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.btn_add)
    Button btn_add;

    private Unbinder unbinder;
    List<User> mDatas = null;
    private MyAdapter mAdapter;
    private ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_recycler_1view);
        unbinder = ButterKnife.bind(this);
        initData();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyAdapter(mDatas, this, recyclerView);

        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDuration(2000);
        defaultItemAnimator.setRemoveDuration(2000);
        recyclerView.setItemAnimator(defaultItemAnimator);

        recyclerView.setAdapter(mAdapter);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                User user = new User();
                user.setName("test");
                mDatas.add(user);
                mAdapter.notifyItemChanged(mDatas.size()-1);

            }
        });


        SelctionDecoration.groupInfoCallback callback = new SelctionDecoration.groupInfoCallback() {
            @Override
            public User getGroupInfo(int position) {

                int groupId = position / 5;
                int index = position % 5;

                User user = mDatas.get(position);
                user.setGroupLength(5);
                user.setGroupInfo("第 " + groupId + "组");
                user.setPosition(index);

                return user;
            }
        };

        recyclerView.addItemDecoration(new SelctionDecoration(callback));


    }


    private void initData() {

        mDatas = new ArrayList<>();

        for (int i = 0; i < 30; i++) {

            User user = new User();
            user.setName("batman==>" + i);
            mDatas.add(user);

        }


    }


    public static class SelctionDecoration extends RecyclerView.ItemDecoration {

        private groupInfoCallback mGroupInfoCallback;
        private Paint mPaintText = null;
        private Paint mPaint = null;

        private int decorHeight = 100;

        public SelctionDecoration(groupInfoCallback mGroupInfoCallback) {
            this.mGroupInfoCallback = mGroupInfoCallback;
            mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaintText.setColor(Color.BLACK);
            mPaintText.setTextSize(40);
            mPaintText.setTextAlign(Paint.Align.LEFT);


            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setColor(Color.YELLOW);


        }


        public SelctionDecoration() {
            super();
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);


        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDrawOver(c, parent, state);
            int childCount = parent.getChildCount();

            for (int i = 0; i < childCount; i++) {


                View view = parent.getChildAt(i);
                int position = parent.getChildAdapterPosition(view);

                if (mGroupInfoCallback != null) {

                    User user = mGroupInfoCallback.getGroupInfo(position);
                    int left = parent.getPaddingLeft();
                    int right = parent.getWidth() - parent.getPaddingRight();


                    if (i != 0) {

                        if (user.isFirst()) {

                            int top = view.getTop() - decorHeight;


                            int bottom = view.getTop();

                            c.drawRect(left, top, right, bottom, mPaint);
                            c.drawText(user.getGroupInfo(), view.getLeft(), view.getTop(), mPaintText);
                        }

                    } else {

                        int top = parent.getPaddingTop();

                        if (user.isLastItem()) {

                            int suggestTop = view.getBottom() - decorHeight;

                            if (suggestTop < top) {

                                top = suggestTop;
                            }

                        }

                        int bottom = top + decorHeight;
                        c.drawRect(left, top, right, bottom, mPaint);
                        c.drawText(user.getGroupInfo(), left, bottom, mPaintText);
                    }
                }
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            if (mGroupInfoCallback != null) {

                User user = mGroupInfoCallback.getGroupInfo(parent.getChildAdapterPosition(view));
                //如果是第一个的话
                if (user.isFirst()) {

                    outRect.top = decorHeight;

                }

            }


        }

        public interface groupInfoCallback {

            User getGroupInfo(int position);
        }


    }


    class MyAdapter extends RecyclerAdapter<User> {

        public MyAdapter(List<User> mDatas, Context mContext, RecyclerView mRecyclerView) {
            super(mDatas, mContext, mRecyclerView);
        }

        @Override
        public ViewHolder createHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_test, parent, false);

            return new MyHolder(view);
        }
    }


    class MyHolder extends ViewHolder<User> {

        @BindView(R.id.tv_test)
        TextView tv_test;

        public MyHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBindWidget(User s) {

            tv_test.setText(s.getName());

        }
    }


    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }


    class User {

        private String name;
        private String groupInfo;
        private int position;
        private int groupLength;

        public User() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGroupInfo() {
            return groupInfo;
        }

        public void setGroupInfo(String groupInfo) {
            this.groupInfo = groupInfo;
        }

        public int getPosition() {
            return position;
        }

        public int getGroupLength() {
            return groupLength;
        }

        public void setGroupLength(int groupLength) {
            this.groupLength = groupLength;
        }

        public boolean isFirst() {

            return position == 0;
        }


        public void setPosition(int position) {

            this.position = position;

        }

        public boolean isLastItem() {

            return position == getGroupLength() - 1 && position >= 0;

        }


    }
}
