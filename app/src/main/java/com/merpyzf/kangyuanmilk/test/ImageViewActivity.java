package com.merpyzf.kangyuanmilk.test;

import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.App;
import com.merpyzf.kangyuanmilk.utils.LogHelper;

public class ImageViewActivity extends AppCompatActivity {

    private ImageView iv_test;
    private Button btn_up;
    private Button btn_down;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        iv_test = (ImageView) findViewById(R.id.iv_test);
        btn_up = (Button) findViewById(R.id.btn_up);
        btn_down = (Button) findViewById(R.id.btn_down);

        iv_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImageView iv = (ImageView) view;

                Drawable drawable = iv.getDrawable();

                if (drawable != null) {


                    int drawableWidth = drawable.getIntrinsicWidth();
                    int drawableHeight = drawable.getMinimumHeight();

                    LogHelper.i("drawableWidth==> " + drawableWidth + " drawableHeight==> " + drawableHeight);
                    LogHelper.i("iv.getWidth==> " + iv.getWidth() + " iv.getHeight==> " + iv.getHeight());

                    float minScale = Math.max(iv.getWidth() * 1.0f / drawableWidth, iv.getHeight() * 1.1f / drawableHeight);

                    Matrix matrix = new Matrix();
                    matrix.setScale(minScale, minScale, 0, 0);
                    iv.setImageMatrix(matrix);


                }


            }
        });


        btn_up.setOnClickListener(view -> {

            Matrix newMatrix = new Matrix(iv_test.getImageMatrix());
            newMatrix.postTranslate(0, -10);

            RectF rect = new RectF(0, 0, iv_test.getDrawable().getIntrinsicWidth(), iv_test.getDrawable().getIntrinsicHeight());
            newMatrix.mapRect(rect);

            if (rect.bottom < iv_test.getHeight()) {

                newMatrix.postTranslate(0, iv_test.getHeight() - rect.bottom);

            } else if (rect.top > 0) {

                newMatrix.postTranslate(0, -rect.top);

            }

            iv_test.setImageMatrix(newMatrix);
            App.showToast("btn_up被点击了");


        });

        btn_down.setOnClickListener(view -> {

            Matrix newMatrix = new Matrix(iv_test.getImageMatrix());
            newMatrix.postTranslate(0, 10);
            iv_test.setImageMatrix(newMatrix);
            App.showToast("btn_down 被点击了");
        });

    }
}
