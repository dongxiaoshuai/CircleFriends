package com.duke.xlws.circle.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * 创建：duke
 * 注释：
 * 版本大小：19
 * 版本名称：v2.3.0
 * 日期：2018/11/27.
 */


@SuppressLint("AppCompatCustomView")
public class CustomImageView extends ImageView {
    public CustomImageView(Context context) {
        super(context);
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setColorFilter(Color.parseColor("#88999999"));
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                setColorFilter(Color.TRANSPARENT);
                break;
        }

        return super.onTouchEvent(event);
    }
}
