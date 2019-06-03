package com.duke.xlws.heart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 创建：duke
 * 注释：心电图网格背景
 * 日期：2018/3/28.
 */


public class HeartGridView extends View {
    //画笔
    private Paint mPaint;
    //网格线的颜色
    private int mLineColor = Color.parseColor("#cdcdcd");
    private int mSmallLineColor = Color.parseColor("#efefef");
    //网格背景颜色
    private int mBackgroundColor = Color.parseColor("#ffffff");
    //大网格横向线的数量
    private int mWidthLineCount;
    //大网格竖向线的数量
    private int mHeightLineCount;
    //大网格线的间距
    private float mGridWidth = 100;
    //小网格横向线的数量
    private int mSmallWidthLineCount;
    //小网格竖向线的数量
    private int mSmallHeightLineCount;
    //小网格线的间距
    private float mSmallGridWidth = 20;
    //view宽度
    private float mWidth;
    //view高度
    private float mHeight;

    public HeartGridView(Context context) {
        this(context, null);
    }

    public HeartGridView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeartGridView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(mBackgroundColor);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getWidth();
        mHeight = getHeight();

        mWidthLineCount = (int) Math.ceil(mHeight / mGridWidth);

        mHeightLineCount = (int) Math.ceil(mWidth / mGridWidth);

        mSmallWidthLineCount = (int) Math.ceil(mHeight / mSmallGridWidth);

        mSmallHeightLineCount = (int) Math.ceil(mWidth / mSmallGridWidth);

        //绘制小网格
        mPaint.setColor(mSmallLineColor);
        //绘制横线
        for (int k = 0; k < mSmallWidthLineCount; k++) {
            canvas.drawLine(0, k * mSmallGridWidth, mWidth, k * mSmallGridWidth, mPaint);
        }
        //绘制竖线
        for (int x = 0; x < mSmallHeightLineCount; x++) {
            canvas.drawLine(x * mSmallGridWidth, 0, x * mSmallGridWidth, mHeight, mPaint);
        }

        //绘制大网格
        mPaint.setColor(mLineColor);
        //绘制横线
        for (int i = 0; i < mWidthLineCount; i++) {
            canvas.drawLine(0, mGridWidth * i, mWidth, mGridWidth * i, mPaint);
        }

        //绘制竖线
        for (int i = 0; i < mHeightLineCount; i++) {
            canvas.drawLine(i * mGridWidth, 0, i * mGridWidth, mHeight, mPaint);
        }

    }
}
