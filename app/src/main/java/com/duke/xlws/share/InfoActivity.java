package com.duke.xlws.share;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.SharedElementCallback;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.duke.xlws.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 创建：duke
 * 注释：
 * 日期：2018/3/19.
 */


public class InfoActivity extends AppCompatActivity {
    private LinearLayout layout;
    private TextView nameTv;
    private ImageView image;
    private TextView infoTv;

    private ViewPager mPager;
    private List<ImageView> photoViews;
    private List<String> mDatas;
    private int index = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        index = getIntent().getIntExtra("index", 0);
        mDatas = getIntent().getStringArrayListExtra("data");

        initView();
        initData();
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initView() {

        mPager = (ViewPager) findViewById(R.id.viewpager);

        layout = (LinearLayout) findViewById(R.id.info_layout);
        nameTv = (TextView) findViewById(R.id.info_name);
        image = (ImageView) findViewById(R.id.info_image);
        image.setAdjustViewBounds(true);
        infoTv = (TextView) findViewById(R.id.info_info);

        //这里指定了被共享的视图元素
        //ViewCompat.setTransitionName(image, "image");

        //这个可以看做个管道
        //每次进入和退出的时候都会进行调用
        //进入的时候获取到前面传来的共享元素的信息
        //退出的时候把这些信息传递给前面的activity
        //同时向sharedElements里面put view,跟对view添加transitionname作用一样
        setEnterSharedElementCallback(new SharedElementCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                names.clear();
                sharedElements.clear();
                sharedElements.put(mDatas.get(mPager.getCurrentItem()), photoViews.get(mPager.getCurrentItem()));

                StringBuilder buffer = new StringBuilder();
                buffer.append("sharedElements :");
                for (String key : sharedElements.keySet()) {
                    buffer.append("<").append(key).append(">");
                }
                Log.d("callback", "setEnterSharedElementCallback->" + buffer.toString());

            }
        });
    }


    @TargetApi(22)
    @Override
    public void supportFinishAfterTransition() {
        Intent data = new Intent();
        data.putExtra("index", mPager.getCurrentItem());
        setResult(200, data);
        super.supportFinishAfterTransition();
    }


    @Override
    public void onBackPressed() {
        Intent data = new Intent();
        data.putExtra("index", mPager.getCurrentItem());
        setResult(200, data);
        super.supportFinishAfterTransition();
    }


    private void initData() {
        photoViews = new ArrayList<>();
        for (String i : mDatas) {
            ImageView photoView = new ImageView(this);
            photoView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            photoView.setImageResource(R.mipmap.ic_rv_image);
            photoView.setAdjustViewBounds(true);
            photoView.setMaxWidth(getWindowManager().getDefaultDisplay().getWidth());
            photoViews.add(photoView);
        }
        mPager.setAdapter(new ViewPagerAdapter());
        mPager.setCurrentItem(index);
    }


    public class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return photoViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(photoViews.get(position));
            return photoViews.get(position);
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(photoViews.get(position));
        }
    }
}
