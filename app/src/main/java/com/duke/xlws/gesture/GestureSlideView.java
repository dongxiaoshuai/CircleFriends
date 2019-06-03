package com.duke.xlws.gesture;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.RelativeLayout;

/**
 * 创建：duke
 * 注释：手势滑动view
 * 版本大小：
 * 版本名称：
 * 日期：2019/5/9.
 */


public class GestureSlideView extends RelativeLayout {
    private final static String TAG = "callback";
    private int screenWidth;
    private int screenHeight;
    private Context ctx;

    public GestureSlideView(Context context) {
        this(context, null);
    }

    public GestureSlideView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public GestureSlideView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        screenWidth = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
        screenHeight = ((Activity) context).getWindowManager().getDefaultDisplay().getHeight();
        this.ctx = context;
    }

    float lastX, lastY;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "ACTION_DOWN");
                lastY = event.getRawY();
                lastX = event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "ACTION_MOVE");
                float mY = event.getRawY() - lastY;
                float mX = event.getRawX() - lastX;

                float x = getX() + mX;
                float y = getY() + mY;

                x = x < 0 ? 0 : x > screenWidth - getWidth() ? screenWidth - getWidth() : x;

//                y = y < 0 ? 0 : y > screenHeight - getHeight() ? screenHeight - getHeight() : y;
                y = y < dip2px(ctx, 48) ? dip2px(ctx, 48) : y;

                setX(x);
                setY(y);
                lastY = event.getRawY();
                lastX = event.getRawX();
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "ACTION_UP");
                if (getY() < screenHeight / 2) {
                    startAnim(getY(), dip2px(ctx, 48), 100);
                } else {
                    startAnim(getY(), screenHeight, 100);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG, "ACTION_CANCEL");
                break;

        }
        return true;
    }


    private void startAnim(float startY, float endY, long duration) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(startY, endY);
        valueAnimator.setDuration(duration);
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setY((Float) animation.getAnimatedValue());
            }
        });
        valueAnimator.start();
    }

    public void openView() {
        startAnim(screenHeight, dip2px(ctx, 48), 200);
    }


    public void closeView() {
        startAnim(dip2px(ctx, 48), screenHeight, 100);

    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
