package com.duke.xlws.circle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.duke.xlws.R;
import com.duke.xlws.circle.widget.CommentsView;
import com.duke.xlws.circle.bean.FcCommentsBean;

import java.util.List;

/**
 * 创建：duke
 * 注释：
 * 版本大小：19
 * 版本名称：v2.3.0
 * 日期：2018/11/26.
 */


public class CommentsAdapter extends BaseAdapter {
    private List<FcCommentsBean> mList;
    private Context mContext;
    private OnReplyCommentClickListener onReplyCommentClickListener;


    public void setOnReplyCommentClickListener(OnReplyCommentClickListener onReplyCommentClickListener) {
        this.onReplyCommentClickListener = onReplyCommentClickListener;
    }

    public CommentsAdapter(List<FcCommentsBean> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mList != null && !mList.isEmpty() ? mList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.friends_circle_comments_item, parent, false);
            vh.commentsView = (CommentsView) convertView.findViewById(R.id.fc_comments_view);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }


        vh.commentsView.setOnCommentsClickListener(new CommentsView.OnCommentsClickListener() {
            @Override
            public void onCommentsNameClickListener(FcCommentsBean bean, boolean isReplyName) {
                if (onReplyCommentClickListener != null) {
                    onReplyCommentClickListener.onNicknameClickListener(position, bean, isReplyName);
                }
            }

            @Override
            public void onCommentsContentClickListener(FcCommentsBean bean) {
                if (onReplyCommentClickListener != null) {
                    onReplyCommentClickListener.onReplyClickListener(position, bean);
                }
            }
        });


        vh.commentsView.setData(mList.get(position));

        return convertView;
    }

    class ViewHolder {
        CommentsView commentsView;
    }


    public interface OnReplyCommentClickListener {
        void onReplyClickListener(int position, FcCommentsBean bean);

        void onNicknameClickListener(int position, FcCommentsBean bean, boolean isReplyName);
    }
}
