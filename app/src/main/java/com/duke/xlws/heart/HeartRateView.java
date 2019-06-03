package com.duke.xlws.heart;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.icu.util.Measure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

/**
 * 创建：duke
 * 注释：心电图心率折线
 * 日期：2018/3/28.
 */


public class HeartRateView extends View {
    //画笔
    private Paint mPaint;
    //路径
    private Path mPath;
    //波线颜色
    private int mLineColor = Color.parseColor("#202020");
    //波线宽度
    private int mLineWidth = 2;
    //view宽度
    private float mWidth;
    //view高度
    private float mHeight;
    //数据源
    private short[] datas;
    //平均值
    private float avg;
    //最后一次 X坐标
    private float mLastX;
    //偏移量
    private float mOffsetX;
    //累加偏移量
    private float mOffsetXed;

    private HeartAllRateView mAllRateView;

    public HeartRateView(Context context) {
        this(context, null);
    }

    public HeartRateView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeartRateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPath = new Path();
    }


    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getWidth();
        mHeight = getHeight();

        mPath.reset();
        mPaint.reset();

        mPaint.setAntiAlias(true);
        mPaint.setColor(mLineColor);
        mPaint.setStrokeWidth(mLineWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setDither(true);
        //设置组合PathEffect 添加转角圆滑
        mPaint.setPathEffect(new CornerPathEffect(90f));

        mOffsetXed += mOffsetX;

        /*
         *  此处是累计偏移量，不是偏移量 ,范围(-x,0)
         *
         *  -x = mWidth - 100 / 50 * datas.length
         *
         *  既：当前屏幕点数宽度（默认是满屏，既屏幕宽度）-全部点数所占屏幕宽度
         *
         *  当累计偏移量大于0时，心电图会偏移出屏幕左边界，
         *
         *  当累计偏移量小于当前屏幕点数宽度减去全部点数宽度时，心电图会偏移出屏幕右边界
         *
         *  此处处理，防止偏移出界
         */
        if (mOffsetXed > 0) {
            mOffsetXed = 0;
        } else if (mOffsetXed < (mWidth - 100 / 50 * datas.length)) {
            mOffsetXed = mWidth - 100 / 50 * datas.length;
        }

        int startX = 0;
        for (int i = 0; i < datas.length; i++) {
            float x = i * (100 / 50) + mOffsetXed;
            if (x >= 0) {
                startX = i;
                mPath.moveTo(x, mHeight / 2 - (datas[i] - avg) / 81.24f * (mWidth / (mWidth / 100f) * 2));
                break;
            }
        }

        //1个网格 画50个点
        for (int i = startX; i < datas.length; i++) {
            float x = i * (100 / 50) + mOffsetXed;
            float y = mHeight / 2 - (datas[i] - avg) / 81.24f * (mWidth / (mWidth / 100f) * 2);
            if (x < mWidth) {
                if (y < 0) y = 0;
                if (y > mHeight) y = mHeight;
                mPath.lineTo(x, y);
//                Log.d("callback", "HeartRateView x->" + x + "，i->" + i + "，点的宽度->" + (i * (100 / 50)) + ",mOffsetXed->" + mOffsetXed);
            }
        }

        canvas.drawPath(mPath, mPaint);


//        绘制矩形 随心电联动
//        mPaint.reset();
//        mPaint.setAntiAlias(true);
//        mPaint.setColor(Color.parseColor("#553F51B5"));
//        mPaint.setStyle(Paint.Style.FILL);
//
//        float pro = (mWidth / (mWidth / 100f * 50f)) / (mWidth / datas.length);
//        mRectWidth = mWidth / pro;
//
//        float left = mOffsetXed / pro * -1;
//        float top = mHeight;
//        float right = mOffsetXed / pro * -1 + mRectWidth;
//        float bottom = mHeight + mRectHeight;
//        canvas.drawRect(left, top, right, bottom, mPaint);
//        Log.d("callback", "left->" + left + ",right->" + right);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://按下
//                Log.d("callback", "ACTION_DOWN->" + event.getRawX());
                break;
            case MotionEvent.ACTION_MOVE://移动
                mOffsetX = event.getRawX() - mLastX;
//                Log.d("callback", "ACTION_MOVE->" + event.getRawX() + ",移动距离->" + mOffsetX);
                invalidate();
                if (mAllRateView != null) {
                    mAllRateView.setOffset(mOffsetX);
                }

                break;
            case MotionEvent.ACTION_UP://抬起
//                Log.d("callback", "ACTION_UP->" + event.getRawX());
                break;
        }
        mLastX = x;
        return true;
    }


    public void setData(short[] datas, float avg) {
        this.datas = datas;
        this.avg = avg;
        invalidate();
    }


    public void setOffsetX(float mOffsetXed) {
        this.mOffsetX = mOffsetXed;
        invalidate();
    }

    public void setmAllRateView(HeartAllRateView mAllRateView) {
        this.mAllRateView = mAllRateView;
    }
}
