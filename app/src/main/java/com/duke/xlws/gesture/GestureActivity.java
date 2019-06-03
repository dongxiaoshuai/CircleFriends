package com.duke.xlws.gesture;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.duke.xlws.R;

/**
 * 创建：duke
 * 注释：
 * 版本大小：
 * 版本名称：
 * 日期：2019/5/9.
 */


public class GestureActivity extends AppCompatActivity {
    private GestureSlideView mView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture);

        mView = (GestureSlideView) findViewById(R.id.slide_view);
    }

    public void onOpenSlide(View view) {
        mView.openView();
    }
}
