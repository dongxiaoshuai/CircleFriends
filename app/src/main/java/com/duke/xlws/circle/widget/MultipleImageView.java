package com.duke.xlws.circle.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建：duke
 * 注释：多图展示
 * 版本大小：19
 * 版本名称：v2.3.0
 * 日期：2018/11/26.
 */

public class MultipleImageView extends ViewGroup {

    private int sHeight;
    private int mSpace = 10; //间距
    private int mWidth; //可用宽度
    private int maxColumn = 3;//最多行数
    private List<Integer> images; //图片源
    private int oneWidth; //当为一行时 等比最大宽度
    private int oneHeight;
    private int moreWidth;//多行时宽度

    private OnImageClickListener onImageClickListener;
    private List<CustomImageView> imageViews;

    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;
    }

    public MultipleImageView(Context context) {
        this(context, null);
    }

    public MultipleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultipleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        sHeight = ((Activity) context).getWindowManager().getDefaultDisplay().getHeight();
        imageViews = new ArrayList<>();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"WrongConstant", "ResourceType"})
    public void setImages(final List<Integer> images) {
        this.images = images;
        this.removeAllViews();
        imageViews.clear();

        if (images == null || images.isEmpty()) {
            return;
        }

//        Log.d("callback", "mWidth->" + mWidth);
        if (mWidth == 0) {
            //为了触发onMeasure()来测量MultiImageView的最大宽度，
            // MultiImageView的宽设置为match_parent
            addView(new View(getContext()));
            return;
        }


        moreWidth = (mWidth - mSpace * 2) / maxColumn;

        oneWidth = mWidth / 3 * 2;
        /**
         * 当 图片资源为1张时  等比缩放
         *
         * 当 图片资源大于1张时  矩形九空格排列
         */
        if (images.size() == 1) {

            final CustomImageView photoView = new CustomImageView(getContext());
            photoView.setAdjustViewBounds(true);
            photoView.setMaxWidth(oneWidth);
            LayoutParams params = new LayoutParams(oneWidth, LayoutParams.WRAP_CONTENT);
            photoView.setLayoutParams(params);
            photoView.setImageResource(images.get(0));

            photoView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onImageClickListener != null) {
                        onImageClickListener.onImageClickListener(0, photoView);
                    }
                }
            });
            imageViews.add(photoView);
            addView(photoView);
        } else

            for (int i = 0; i < images.size(); i++) {
                LayoutParams params = new LayoutParams(moreWidth, moreWidth);
                final CustomImageView photoView = new CustomImageView(getContext());
                photoView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                photoView.setImageResource(images.get(i));
                photoView.setLayoutParams(params);
                final int finalI = i;
                photoView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onImageClickListener != null) {
                            onImageClickListener.onImageClickListener(finalI, photoView);
                        }
                    }
                });
                imageViews.add(photoView);
                addView(photoView);
            }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int sizeHeightMode = MeasureSpec.getMode(heightMeasureSpec);

//        Log.d("callback", "sizeWidth:" + sizeWidth + ",sizeHeight:" + sizeHeight + ",sizeHeightMode->" + sizeHeightMode);

        mWidth = sizeWidth - getPaddingLeft() - getPaddingRight();

        /**
         * 先添加子view > imageView
         *
         * 在测量子view 布局可用宽高
         */

        //先添加子view
        if (images != null && !images.isEmpty()) {
            setImages(images);
        }

        //再测量
        measureChildren(widthMeasureSpec, heightMeasureSpec);


        switch (sizeHeightMode) {
            case MeasureSpec.EXACTLY://父级为子级决定了一个确切的尺寸  具体尺寸或者match_parent
                break;
            case MeasureSpec.AT_MOST: //不超过父view赋予的尺寸 wrap_content
            case MeasureSpec.UNSPECIFIED://没有任何限制
                if (images != null || !images.isEmpty()) {

                    if (images.size() == 1) {
                        sizeHeight = getChildAt(0).getMeasuredHeight();
//                        Log.d("callback", "onMeasure : mWidth->" + mWidth + ",sizeHeight->" + sizeHeight + ",screenHeight->" + sHeight);
                        //长图截取 屏幕高度/3
                        if (sizeHeight > sHeight) {
                            sizeHeight = sHeight;

                            sizeWidth = sizeWidth / 3;
                            sizeHeight = sizeHeight / 3;
                            mWidth = sizeWidth;
//                            oneHeight = sizeHeight;

                            setImages(images);
                            //重新测量 缩小之后 子view 的 宽 高
                            measureChildren(widthMeasureSpec, heightMeasureSpec);

                        }
                    } else if (images.size() <= 3) {
                        sizeHeight = moreWidth;
                    } else if (images.size() <= 6) {
                        sizeHeight = moreWidth * 2 + mSpace;
                    } else if (images.size() <= 9) {
                        sizeHeight = moreWidth * 3 + 2 * mSpace;
                    }
                }
                break;
        }

        //保存
        setMeasuredDimension(sizeWidth, sizeHeight);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int count = getChildCount();
        if (count == 1) {
            CustomImageView view = (CustomImageView) getChildAt(0);
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
//            Log.d("callback", "onLayout :  w->" + view.getMeasuredWidth() + ",h->" + view.getMeasuredHeight());
        } else if (count == 4) {
            for (int i = 0; i < count; i++) {
                CustomImageView view = (CustomImageView) getChildAt(i);
                if (i <= 1) {
                    view.layout(i * (moreWidth + mSpace), 0, (i + 1) * moreWidth + i * mSpace, moreWidth);
                } else {
                    view.layout(i % 2 * (moreWidth + mSpace), moreWidth + mSpace, (i % 2 + 1) * moreWidth + i % 2 * mSpace, moreWidth * 2 + mSpace);
                }
            }
        } else {
            for (int i = 0; i < count; i++) {
                CustomImageView view = (CustomImageView) getChildAt(i);
//                if (i <= 2) {
//                    view.layout(i * (moreWidth + mSpace), 0, (i + 1) * moreWidth + i * mSpace, moreWidth);
//                } else if (i > 2 && i <= 5) {
//                    view.layout(i % 3 * (moreWidth + mSpace), moreWidth + mSpace, (i % 3 + 1) * moreWidth + i % 3 * mSpace, moreWidth * 2 + mSpace);
//                } else if (i > 5 && i <= 8) {
//                    view.layout(i % 3 * (moreWidth + mSpace), 2 * (moreWidth + mSpace), (i % 3 + 1) * moreWidth + i % 3 * mSpace, moreWidth * 3 + 2 * mSpace);
//                }

                view.layout(i % 3 * (moreWidth + mSpace), (i / 3) % 3 * (moreWidth + mSpace), (i % 3 + 1) * moreWidth + i % 3 * mSpace, ((i / 3) % 3 + 1) * moreWidth + (i / 3) % 3 * mSpace);

            }
        }
    }


    public interface OnImageClickListener {
        void onImageClickListener(int position, CustomImageView imageView);
    }

    public CustomImageView getShareImageView(int index) {
        Log.d("callback", "imageViews size->" + imageViews.size());
        return imageViews.get(index);
    }
}
