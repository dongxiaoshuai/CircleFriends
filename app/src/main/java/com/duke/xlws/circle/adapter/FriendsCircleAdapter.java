package com.duke.xlws.circle.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.duke.xlws.R;
import com.duke.xlws.circle.widget.CustomImageView;
import com.duke.xlws.circle.widget.GridViewForScrollView;
import com.duke.xlws.circle.widget.LikeView;
import com.duke.xlws.circle.widget.ListViewForScrollView;
import com.duke.xlws.circle.bean.FcBean;
import com.duke.xlws.circle.bean.FcCommentsBean;
import com.duke.xlws.circle.bean.FcLikeBean;
import com.duke.xlws.circle.widget.MultipleImageView;

import java.util.List;

/**
 * 创建：duke
 * 注释：
 * 版本大小：19
 * 版本名称：v2.3.0
 * 日期：2018/11/23.
 */


public class FriendsCircleAdapter extends BaseAdapter {
    private List<FcBean> mList;
    private Context mContext;
    private OnFriendsCircleClickListener onFriendsCircleClickListener;

    public void setOnFriendsCircleClickListener(OnFriendsCircleClickListener onFriendsCircleClickListener) {
        this.onFriendsCircleClickListener = onFriendsCircleClickListener;
    }

    public FriendsCircleAdapter(List<FcBean> mList, Context mContext) {
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

    @SuppressLint("WrongConstant")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.friends_circle_item, parent, false);
            vh = new ViewHolder();
            vh.headImage = (ImageView) convertView.findViewById(R.id.f_c_head_image_item);
            vh.nameTv = (TextView) convertView.findViewById(R.id.f_c_name_tv_item);
            vh.txtTv = (TextView) convertView.findViewById(R.id.f_c_txt_tv_item);
            vh.htmlLayout = (LinearLayout) convertView.findViewById(R.id.f_c_html_layout_item);
            vh.htmlImage = (ImageView) convertView.findViewById(R.id.f_c_html_image_item);
            vh.htmlTxtTv = (TextView) convertView.findViewById(R.id.f_c_html_tv_item);
            vh.imageGv = (MultipleImageView) convertView.findViewById(R.id.f_c_image_gv_item);
            vh.timeTv = (TextView) convertView.findViewById(R.id.f_c_time_tv_item);
            vh.commentsOrLikeImage = (ImageView) convertView.findViewById(R.id.f_c_comments_or_like_image_item);
            vh.interactiveLayout = (LinearLayout) convertView.findViewById(R.id.f_c_interactive_layout_item);
            vh.likeLayout = (LinearLayout) convertView.findViewById(R.id.f_c_like_layout_item);
            vh.likeView = (LikeView) convertView.findViewById(R.id.f_c_like_name_tv_item);
            vh.dividerLine = convertView.findViewById(R.id.f_c_divider_v_item);
            vh.commentsLv = (ListViewForScrollView) convertView.findViewById(R.id.f_c_comments_lv_item);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }


        switch (mList.get(position).fcType) {
            case 0://文字
                vh.htmlLayout.setVisibility(View.GONE);
                vh.imageGv.setVisibility(View.GONE);
                break;
            case 1://图片
            case 2://文字和图片
                vh.htmlLayout.setVisibility(View.GONE);
                vh.imageGv.setVisibility(View.VISIBLE);
//                Log.d("callback", "position->" + position + ",size->" + mList.get(position).imageUrls.size());
                setImageData(mList.get(position).imageUrls, vh.imageGv, position);
                break;
            case 3://HTML
                vh.htmlLayout.setVisibility(View.VISIBLE);
                vh.imageGv.setVisibility(View.GONE);
                vh.htmlImage.setImageResource(mList.get(position).headUrl);
                vh.htmlTxtTv.setText(mList.get(position).fcHtmlTxt);
                break;

        }

        if (!TextUtils.isEmpty(mList.get(position).fcContentTxt)) {
            vh.txtTv.setVisibility(View.VISIBLE);
            vh.txtTv.setText(mList.get(position).fcContentTxt);
        } else {
            vh.txtTv.setVisibility(View.GONE);
        }

        vh.nameTv.setText(mList.get(position).nickName);
        vh.timeTv.setText(mList.get(position).fcTime);

        boolean isLike = false;
        boolean isComments = false;
        if (mList.get(position).mLikeList != null && !mList.get(position).mLikeList.isEmpty()) {
            isLike = true;
            setLikeData(mList.get(position).mLikeList, vh.likeView, position);
        }


        if (mList.get(position).mCommentsList != null && !mList.get(position).mCommentsList.isEmpty()) {
            isComments = true;
            setComments(mList.get(position).mCommentsList, vh.commentsLv, position);
        }


        vh.dividerLine.setVisibility(isLike && isComments ? View.VISIBLE : View.GONE);
        vh.likeLayout.setVisibility(isLike ? View.VISIBLE : View.GONE);
        vh.commentsLv.setVisibility(isComments ? View.VISIBLE : View.GONE);

        vh.htmlLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onFriendsCircleClickListener != null) {
                    onFriendsCircleClickListener.onHtmlClickListener(position);
                }
            }
        });

        return convertView;
    }

    //图片
    private void setImageData(List<Integer> images, MultipleImageView gv, final int parentPosition) {
        gv.setImages(images);
        gv.setOnImageClickListener(new MultipleImageView.OnImageClickListener() {
            @Override
            public void onImageClickListener(int position, CustomImageView imageView) {
                if (onFriendsCircleClickListener != null) {
                    onFriendsCircleClickListener.onImageClickListener(parentPosition, position, imageView);
                }
            }
        });
    }

    //赞
    private void setLikeData(final List<FcLikeBean> likes, LikeView likeView, final int parentPosition) {
        likeView.setData(likes);
        likeView.setOnLikeClickListener(new LikeView.OnLikeClickListener() {
            @Override
            public void onLikeClickListener(int position) {
                if (onFriendsCircleClickListener != null) {
                    onFriendsCircleClickListener.onLikeNicknameClickListener(parentPosition, position, likes.get(position));
                }
            }
        });
    }

    //评论
    private void setComments(List<FcCommentsBean> list, ListViewForScrollView lv, final int parentPosition) {
        CommentsAdapter adapter = new CommentsAdapter(list, mContext);
        lv.setAdapter(adapter);
        adapter.setOnReplyCommentClickListener(new CommentsAdapter.OnReplyCommentClickListener() {
            @Override
            public void onReplyClickListener(int position, FcCommentsBean bean) {
                if (onFriendsCircleClickListener != null) {
                    onFriendsCircleClickListener.onCommentsContentClickListener(parentPosition, position, bean);
                }
            }

            @Override
            public void onNicknameClickListener(int position, FcCommentsBean bean, boolean isReplyName) {
                if (onFriendsCircleClickListener != null) {
                    onFriendsCircleClickListener.onCommentsNicknameClickListener(parentPosition, position, bean, isReplyName);
                }
            }
        });

    }

    class ViewHolder {

        ImageView headImage;
        TextView nameTv;
        TextView txtTv;

        LinearLayout htmlLayout;
        ImageView htmlImage;
        TextView htmlTxtTv;

        //        GridViewForScrollView imageGv;
        MultipleImageView imageGv;

        TextView timeTv;
        ImageView commentsOrLikeImage;

        LinearLayout interactiveLayout;
        LinearLayout likeLayout;
        LikeView likeView;

        View dividerLine;

        ListViewForScrollView commentsLv;

    }

    //朋友圈相关回调

    public interface OnFriendsCircleClickListener {

        void onCommentsContentClickListener(int parentPosition, int childPosition, FcCommentsBean bean);

        void onCommentsNicknameClickListener(int parentPosition, int childPosition, FcCommentsBean bean, boolean isReplyName);

        void onLikeNicknameClickListener(int parentPosition, int childPosition, FcLikeBean bean);

        void onImageClickListener(int parentPosition, int childPosition, CustomImageView imageView);

        void onHtmlClickListener(int position);
    }
}
