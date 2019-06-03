package com.duke.xlws.circle;

import android.app.Activity;
import android.app.SharedElementCallback;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.duke.xlws.R;
import com.duke.xlws.circle.adapter.FriendsCircleAdapter;
import com.duke.xlws.circle.bean.FcBean;
import com.duke.xlws.circle.bean.FcCommentsBean;
import com.duke.xlws.circle.bean.FcLikeBean;
import com.duke.xlws.circle.widget.CustomImageView;
import com.duke.xlws.circle.widget.MultipleImageView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 创建：duke
 * 注释：朋友圈
 * 版本大小：19
 * 版本名称：v2.3.0
 * 日期：2018/10/22.
 */


public class FriendsCircleActivity extends AppCompatActivity {

    private ListView mLv;
    private FriendsCircleAdapter mAdapter;
    private List<FcBean> mList;

    private int index = -1;
    private int parentIndex;
    private FcBean bean;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_circle);

        initView();
        initData();


    }


    private void initView() {
        mLv = (ListView) findViewById(R.id.lv);
        mLv.addHeaderView(getHeadView());
    }


    private View getHeadView() {
        View view = LayoutInflater.from(this).inflate(R.layout.friends_circle_head_view, null);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initData() {

        mList = new ArrayList<>();
        mAdapter = new FriendsCircleAdapter(mList, this);
        mLv.setAdapter(mAdapter);
        mAdapter.setOnFriendsCircleClickListener(new FriendsCircleAdapter.OnFriendsCircleClickListener() {
            @Override
            public void onCommentsContentClickListener(int parentPosition, int childPosition, FcCommentsBean bean) {
                Log.d("callback", "回复评论->" + bean.comments);
            }

            @Override
            public void onCommentsNicknameClickListener(int parentPosition, int childPosition, FcCommentsBean bean, boolean isReplyName) {
                Log.d("callback", "点击评论人->" + (isReplyName ? bean.toUserName : bean.fromUserName));

            }

            @Override
            public void onLikeNicknameClickListener(int parentPosition, int childPosition, FcLikeBean bean) {
                Log.d("callback", "点击点赞人->" + bean.fromUserName);

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onImageClickListener(int parentPosition, int childPosition, CustomImageView imageView) {
                Log.d("callback", "点击图片->parentPosition:" + parentPosition + ",childPosition:" + childPosition + ",view->" + imageView.toString());
                parentIndex = parentPosition;
                bean = mList.get(parentIndex);
                skipActivity(LookImageActivity.class, childPosition, bean, imageView);

            }

            @Override
            public void onHtmlClickListener(int position) {
                Log.d("callback", "点击html->" + position);
            }
        });


        setExitSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                if (index == -1) return;
                names.clear();
                sharedElements.clear();
                CustomImageView view = (CustomImageView) getItemView(parentIndex);
                sharedElements.put("" + bean.imageUrls.get(index), view);


                Log.d("callback", "Exit item->" + parentIndex + ",image->" + index + ",key->" + bean.imageUrls.get(index)
                        + ",view->" + (view == null ? null : view));
                index = -1;

            }
        });

        setData();
    }


    private void skipActivity(Class<?> activity, int index, FcBean data, CustomImageView view) {
        Intent intent = new Intent(this, activity);
        intent.putExtra("index", index);
        intent.putExtra("data", data);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, "" + data.imageUrls.get(index));
            ActivityCompat.startActivity(this, intent, compat.toBundle());
        } else
            startActivity(intent);
    }


    private void setData() {
        String content = "今天的我就不想吃饭怎么滴了吧，哼哼哼今天的我就不想吃饭怎么滴了吧，" +
                "哼哼哼今天的我就不想吃饭怎么滴了吧";
        String html = "这是分享的一个HTML网页测试地址";
        String comments = "我就说你呢";
        long time = 1000 * 60 * 60 * 24;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            FcBean fcBean = new FcBean();
            if (i < 9)
                fcBean.fcType = i == 1 ? 1 : 2;
            else if (i == 9) fcBean.fcType = 3;
            else fcBean.fcType = 0;
            // 0：文字   1：图片   2:文字和图片   3:HTML
            switch (fcBean.fcType) {
                case 0://文字
                    fcBean.fcContentTxt = content + ",i->" + i;
                    break;
                case 1://图片
                case 2://文字和图片
                    fcBean.fcContentTxt = content + ",i->" + i;
                    List<Integer> images = new ArrayList<>();
                    if (i == 0) {
                        images.add(R.mipmap.ic_ct);
                    } else if (i == 1) {
                        images.add(R.mipmap.ic_test);
                    } else if (i == 2) {
                        images.add(R.mipmap.ic_rv_image);
                    } else
                        for (int m = i + 1; m > 0; m--) {
                            if (m % 2 == 0)
                                images.add(R.mipmap.ic_test);
                            else images.add(R.mipmap.ic_rv_image);
                        }
                    fcBean.imageUrls = images;
                    break;
                case 3://HTML
                    fcBean.fcHtmlImage = R.mipmap.ic_rv_image;
                    fcBean.fcHtmlTxt = html;
                    break;
            }


            fcBean.fcTime = getBeforeDate(new Date(System.currentTimeMillis() - (i * time)));
            fcBean.fcId = "fc" + i;
            fcBean.headUrl = R.mipmap.icon_head;
            fcBean.nickName = "董小帅";
            fcBean.userId = "" + i;

            List<FcLikeBean> fcLikeBeanList = new ArrayList<>();

            for (int l = 0; l < 5; l++) {
                FcLikeBean likeBean = new FcLikeBean();
                likeBean.fcId = fcBean.fcId;
                likeBean.fcLikeId = "like" + i + l;
                likeBean.fromUserId = "" + l;
                likeBean.fromUserName = "爱谁谁->" + l;
                likeBean.toLikeUserId = fcBean.userId;
                likeBean.toLikeUserName = fcBean.nickName;
                fcLikeBeanList.add(likeBean);
            }
            if (i > 0)
                fcBean.mLikeList = fcLikeBeanList;


            List<FcCommentsBean> commentsBeanList = new ArrayList<>();
            builder.append(comments);

            for (int c = 0; c < 5; c++) {

                FcCommentsBean commentsBean = new FcCommentsBean();
                commentsBean.fcId = fcBean.fcId;
                commentsBean.commentsId = "c" + i + c;
                commentsBean.comments = builder.toString();
                commentsBean.fromUserId = "" + i;
                commentsBean.fromUserName = "董小帅";
                if (c > 1) {
                    commentsBean.toUserId = "c" + c;
                    commentsBean.toUserName = "胖小二";
                }

                commentsBeanList.add(commentsBean);
            }
            if (i > 1)
                fcBean.mCommentsList = commentsBeanList;

            mList.add(fcBean);
        }

        mAdapter.notifyDataSetChanged();
    }


    private static final long MINUTE = 1000 * 60;   //分
    private static final long HOUR = 60 * MINUTE; //时
    private static final long DAY = 24 * HOUR; //日
    private static final long MONTH = DAY * 30; //月
    private static final long YEAR = MONTH * 12; //年

    //时间前
    public static String getBeforeDate(Date date) {
        long time = date.getTime();
        long curTime = new Date().getTime();
        long t = curTime - time;
        long minute = t / MINUTE;
        long hour = t / HOUR;
        long day = t / DAY;
        long month = t / MONTH;
        long year = t / YEAR;
        if (year > 0) {
            return year + "年前";
        } else if (month > 0) {
            return month + "月前";
        } else if (day > 0) {
            return day + "天前";
        } else if (hour > 0) {
            return hour + "小时前";
        } else if (minute > 0) {
            return minute + "分钟前";
        } else {
            return "1分钟前";
        }

    }


    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        index = data.getIntExtra("index", -1);
//        Log.d("callback", "onActivityReenter->" + index);

    }


    /**
     * 第一种方法 更新对应view的内容
     *
     * @param position 要更新的位置
     */
    private View getItemView(int position) {
        /**第一个可见的位置**/
        int firstVisiblePosition = mLv.getFirstVisiblePosition();
        /**最后一个可见的位置**/
        int lastVisiblePosition = mLv.getLastVisiblePosition();

        position += 1;
        Log.d("callback", "firstVisiblePosition->" + firstVisiblePosition + ",lastVisiblePosition->" + lastVisiblePosition);
        /**在看见范围内才更新，不可见的滑动后自动会调用getView方法更新**/
        if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
            /**获取指定位置view对象**/
            View view = mLv.getChildAt(position - firstVisiblePosition);
            MultipleImageView multipleImageView = (MultipleImageView) view.findViewById(R.id.f_c_image_gv_item);
//            CustomImageView imageView = (CustomImageView) multipleImageView.getChildAt(index);
            CustomImageView imageView = multipleImageView.getShareImageView(index);
            return imageView;
        }
        return null;
    }

}
