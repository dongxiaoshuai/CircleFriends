package com.duke.xlws.circle.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 自定义gridview，解决ListView中嵌套gridview显示不正常的问题
 * Created by JTL on 2015/5/19.
 */
public class GridViewForScrollView extends GridView {
    public GridViewForScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridViewForScrollView(Context context) {
        super(context);
    }

    public GridViewForScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
////        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
////
////            return true;  //禁止GridView滑动
////
////        }
//        return super.dispatchTouchEvent(ev);
//
//    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
