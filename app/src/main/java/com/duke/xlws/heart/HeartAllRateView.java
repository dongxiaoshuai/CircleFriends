package com.duke.xlws.heart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 创建：duke
 * 注释：全部心电图心率折线
 * 版本大小：12
 * 版本名称：v2.1.3
 * 日期：2018/icon_head/31.
 */


public class HeartAllRateView extends View {
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
    //平均值/中间值
    private float avg;
    //心电偏移量
    private float mOffsetXed;
    //矩形偏移量
    private float mOffsetX;
    //最后一次偏移X
    private float mLastX;
    //矩形宽度
    private float mRectWidth;
    //矩形高度
    private float mRectHeight = 40;
    //心电view 和心电view联动
    private HeartRateView mRateView;
    /**
     * 和心电view的比例
     * <p>
     * <p>
     * 屏幕宽度绘制全部点数       屏幕的宽度
     * --------------   =  -----------------
     * pro                每网格50点时屏幕点数
     */
    private float pro = 0;

    public HeartAllRateView(Context context) {
        this(context, null);
    }

    public HeartAllRateView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeartAllRateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPath = new Path();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getWidth();
        mHeight = getHeight();
        mRectHeight = mHeight;
        mPath.reset();
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mLineColor);
        mPaint.setStrokeWidth(mLineWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setDither(true);
        //设置组合PathEffect 添加转角圆滑
        mPaint.setPathEffect(new CornerPathEffect(90f));

        pro = (mWidth / (mWidth / 100f * 50f)) / (mWidth / datas.length);
        mRectWidth = mWidth / pro;

        mOffsetXed += mOffsetX;


        /*
         *  此处是累计偏移量，不是偏移量 ,范围(x,0)，x<0
         *
         *  x = -mWidth + mRectWidth
         *
         *  既：当前屏幕宽度+矩形宽度
         *
         *  当累计偏移量大于0时，矩形会偏移出屏幕左边界，
         *
         *  当累计偏移量小于负的当前屏幕宽度减去矩形宽度时，矩形会偏移出屏幕右边界
         *
         *  此处处理，防止偏移出界
         */

        if (mOffsetXed > 0) {
            mOffsetXed = 0;
        } else if (mOffsetXed < -mWidth + mRectWidth) {
            mOffsetXed = -mWidth + mRectWidth;
        }

        for (int i = 0; i < datas.length; i++) {
            float y = mHeight / 2f - (datas[i] - avg) / 81.24f * (mWidth / (mWidth / 100f) * 2f);
            float x = i * mWidth / datas.length;
            y = y / 3;
            y = y / 2 + mHeight / 2;

            if (y < 0) y = 0;
            if (y > mHeight) y = mHeight;

            if (i == 0) {
                mPath.moveTo(x, y);
            } else {
                mPath.lineTo(x, y);
            }
        }

        canvas.drawPath(mPath, mPaint);


        //绘制矩形 随心电联动
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#5538b4b8"));
        mPaint.setStyle(Paint.Style.FILL);


        float left = mOffsetXed * -1;
        float top = mHeight / 2 - mRectHeight / 2;
        float right = mOffsetXed * -1 + mRectWidth;
        float bottom = mHeight / 2 + mRectHeight / 2;
        canvas.drawRect(left, top, right, bottom, mPaint);

//        Log.d("callback", "left->" + left + ",right->" + right + ",mOffsetXed->" + mOffsetXed + ",mOffsetX->" + mOffsetX);

    }


    public void setOffset(float mOffsetX) {
        this.mOffsetX = mOffsetX / pro;
        invalidate();
    }

    public void setmRateView(HeartRateView mRateView) {
        this.mRateView = mRateView;
    }

    public void setData(short[] datas, float avg) {
        this.datas = datas;
        this.avg = avg;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://按下
                break;
            case MotionEvent.ACTION_MOVE://移动
                mOffsetX = (event.getRawX() - mLastX) * -1;
                invalidate();
                if (mRateView != null) {
                    mRateView.setOffsetX(mOffsetX * pro);
                }
                break;
            case MotionEvent.ACTION_UP://抬起
                break;
        }
        mLastX = x;
        return true;

    }
}
