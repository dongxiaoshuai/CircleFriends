package com.duke.xlws.circle.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.duke.xlws.R;
import com.duke.xlws.circle.bean.FcLikeBean;

import java.util.List;

/**
 * 创建：duke
 * 注释：
 * 版本大小：19
 * 版本名称：v2.3.0
 * 日期：2018/11/27.
 */


@SuppressLint("AppCompatCustomView")
public class LikeView extends TextView {
    private List<FcLikeBean> mList;
    private OnLikeClickListener onLikeClickListener;



    public void setOnLikeClickListener(OnLikeClickListener onLikeClickListener) {
        this.onLikeClickListener = onLikeClickListener;
    }

    public LikeView(Context context) {
        this(context, null);
    }

    public LikeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LikeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setData(List<FcLikeBean> mList) {
        this.mList = mList;
        if (mList == null || mList.isEmpty()) {
            return;
        }
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
//        stringBuilder.append(" ");
//        stringBuilder.setSpan(new ImageSpan(getContext(), R.mipmap.ic_launcher, DynamicDrawableSpan.ALIGN_BASELINE), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        for (int i = 0; i < mList.size(); i++) {

            if (i < mList.size() - 1) {
                stringBuilder.append(setTxtData(i)).append("，");
            } else {
                stringBuilder.append(setTxtData(i));
            }

        }

        setText(stringBuilder);

        setMovementMethod(new LocalLinkMovementMethod());
    }

    //设置文字数据
    private SpannableString setTxtData(final int position) {
        String txt = mList.get(position).fromUserName;
        SpannableString spannableString = new SpannableString(txt);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {

                if (onLikeClickListener != null) {
                    onLikeClickListener.onLikeClickListener(position);
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.fc_name_color));
                ds.setUnderlineText(false);
            }

        }, 0, txt.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        return spannableString;
    }


    public interface OnLikeClickListener {
        void onLikeClickListener(int position);
    }


    public static class LocalLinkMovementMethod extends LinkMovementMethod {
        static LocalLinkMovementMethod sInstance;


        public static LocalLinkMovementMethod getInstance() {
            if (sInstance == null)
                sInstance = new LocalLinkMovementMethod();

            return sInstance;
        }

        @Override
        public boolean onTouchEvent(TextView widget,
                                    Spannable buffer, MotionEvent event) {
            int action = event.getAction();

            if (action == MotionEvent.ACTION_UP ||
                    action == MotionEvent.ACTION_DOWN ||
                    action == MotionEvent.ACTION_MOVE) {
                int x = (int) event.getX();
                int y = (int) event.getY();

                x -= widget.getTotalPaddingLeft();
                y -= widget.getTotalPaddingTop();

                x += widget.getScrollX();
                y += widget.getScrollY();

                Layout layout = widget.getLayout();
                int line = layout.getLineForVertical(y);
                int off = layout.getOffsetForHorizontal(line, x);

                ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);

                if (link.length != 0) {
                    if (action == MotionEvent.ACTION_UP) {
                        link[0].onClick(widget);

                        buffer.setSpan(new BackgroundColorSpan(Color.TRANSPARENT),
                                buffer.getSpanStart(link[0]), buffer.getSpanEnd(link[0]),
                                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                        Selection.removeSelection(buffer);

                    } else if (action == MotionEvent.ACTION_DOWN) {

                        buffer.setSpan(new BackgroundColorSpan(Color.parseColor("#88999999")),
                                buffer.getSpanStart(link[0]), buffer.getSpanEnd(link[0]),
                                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                        Selection.setSelection(buffer,
                                buffer.getSpanStart(link[0]),
                                buffer.getSpanEnd(link[0]));
                    } else if (action == MotionEvent.ACTION_MOVE){

                        buffer.setSpan(new BackgroundColorSpan(Color.TRANSPARENT),
                                buffer.getSpanStart(link[0]), buffer.getSpanEnd(link[0]),
                                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                        Selection.removeSelection(buffer);
                    }

                    return true;
                } else {
                    Selection.removeSelection(buffer);
                }
            }
//        return super.onTouchEvent(widget, buffer, event);
            return false;
        }
    }

}
