package com.duke.xlws.circle;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ShareCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.duke.xlws.R;
import com.duke.xlws.circle.adapter.ImageViewPagerAdapter;
import com.duke.xlws.circle.bean.FcBean;
import com.duke.xlws.circle.widget.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 创建：duke
 * 注释：
 * 版本大小：19
 * 版本名称：v2.3.0
 * 日期：2018/11/29.
 */


public class LookImageActivity extends AppCompatActivity {
    private ViewPager mVp;

    private ImageViewPagerAdapter mAdapter;
    private List<ImageView> images;


    private int index;
    private FcBean bean;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_look_image);


        bean = (FcBean) getIntent().getSerializableExtra("data");
        index = getIntent().getIntExtra("index", 0);

        initView();

        initData();

        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                names.clear();
                sharedElements.clear();
                sharedElements.put("" + bean.imageUrls.get(mVp.getCurrentItem()), images.get(mVp.getCurrentItem()));

                Log.d("callback", "Enter image->" + index + ",key->" + bean.imageUrls.get(index));

            }
        });

    }


    private void initView() {
        mVp = (ViewPager) findViewById(R.id.viewpager);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initData() {
        images = new ArrayList<>();
        int w = getWindowManager().getDefaultDisplay().getWidth();
        for (int i = 0; i < bean.imageUrls.size(); i++) {

            ImageView photoView = new ImageView(this);
            photoView.setAdjustViewBounds(true);
            photoView.setMaxWidth(w);
            photoView.setImageResource(bean.imageUrls.get(i));
            images.add(photoView);

        }


        mAdapter = new ImageViewPagerAdapter(images, this);
        mVp.setAdapter(mAdapter);
        mVp.setCurrentItem(index);
        mVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @TargetApi(22)
    @Override
    public void supportFinishAfterTransition() {
        Intent data = new Intent();
        data.putExtra("index", mVp.getCurrentItem());
        setResult(200, data);
        super.supportFinishAfterTransition();
    }


    @Override
    public void onBackPressed() {
        Intent data = new Intent();
        data.putExtra("index", mVp.getCurrentItem());
        setResult(200, data);
        super.supportFinishAfterTransition();
    }
}
