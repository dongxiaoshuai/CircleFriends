package com.duke.xlws.circle.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.duke.xlws.R;
import com.duke.xlws.circle.bean.FcCommentsBean;

/**
 * 创建：duke
 * 注释：
 * 版本大小：19
 * 版本名称：v2.3.0
 * 日期：2018/11/27.
 */


@SuppressLint("AppCompatCustomView")
public class CommentsView extends TextView {
    private FcCommentsBean bean;
    private OnCommentsClickListener onCommentsClickListener;


    public void setOnCommentsClickListener(OnCommentsClickListener onCommentsClickListener) {
        this.onCommentsClickListener = onCommentsClickListener;
    }

    public CommentsView(Context context) {
        this(context, null);
    }

    public CommentsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommentsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setData(FcCommentsBean bean) {
        this.bean = bean;
        if (bean == null) {
            return;
        }

        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();

        stringBuilder.append(setNicknameSpan(bean, false));

        if (TextUtils.isEmpty(bean.toUserName)) {
            stringBuilder.append(":");
        } else {
            stringBuilder.append("回复");
            stringBuilder.append(setNicknameSpan(bean, true));
            stringBuilder.append(":");

        }
        stringBuilder.append(setCommentsData(bean));

        setText(stringBuilder);

        setMovementMethod(new LikeView.LocalLinkMovementMethod());
    }

    private SpannableString setNicknameSpan(final FcCommentsBean bean, final boolean isReply) {
        String txt;
        if (!isReply)
            txt = bean.fromUserName;
        else txt = bean.toUserName;
        SpannableString spannableString = new SpannableString(txt);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                if (onCommentsClickListener != null) {
                    onCommentsClickListener.onCommentsNameClickListener(bean, isReply);
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


    private SpannableString setCommentsData(final FcCommentsBean bean) {
        String comments = bean.comments;
        SpannableString spannableString = new SpannableString(comments);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                if (onCommentsClickListener != null) {
                    onCommentsClickListener.onCommentsContentClickListener(bean);
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.fc_content_color));
                ds.setUnderlineText(false);
            }
        }, 0, comments.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }


    public interface OnCommentsClickListener {
        void onCommentsNameClickListener(FcCommentsBean bean, boolean isReplyName);

        void onCommentsContentClickListener(FcCommentsBean bean);
    }
}
