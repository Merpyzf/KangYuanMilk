package com.merpyzf.kangyuanmilk.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.widget.ImageView;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.utils.LogHelper;
import com.merpyzf.kangyuanmilk.utils.ui.SearchViewHelper;

public class SearchActivity extends AppCompatActivity {

    private ImageView iv_search;
    private CardView cardView;
    private ImageView iv_search_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        iv_search = (ImageView) findViewById(R.id.iv_search);
        cardView = (CardView) findViewById(R.id.cardView);
        iv_search_back = (ImageView) findViewById(R.id.iv_search_back);

        iv_search.post(() -> LogHelper.i("iv_search: getX==>" + iv_search.getX() + "iv_search: getY==>" + iv_search.getY()));

        iv_search.setOnClickListener(view -> SearchViewHelper.excuteAnimator(cardView));

        iv_search_back.setOnClickListener(view -> SearchViewHelper.excuteAnimator(cardView));

    }
}
