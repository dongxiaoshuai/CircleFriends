package com.duke.xlws.pager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.duke.xlws.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建：duke
 * 注释：
 * 版本大小：19
 * 版本名称：v2.3.0
 * 日期：2018/10/9.
 */


public class ViewPagerActivity extends AppCompatActivity {

    private CustomViewPager viewPager;
    private PagerAdapter adapter;

    private List<List<String>> lists;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        viewPager = (CustomViewPager) findViewById(R.id.viewpager);

        lists = new ArrayList<>();

        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        lists.add(list);
        List<String> list1 = new ArrayList<>();
        list1.add("");
        list1.add("");
        list1.add("");
        list1.add("");
        list1.add("");
        list1.add("");
        list1.add("");
        lists.add(list1);
        adapter = new PagerAdapter(lists, this);
        viewPager.setAdapter(adapter);
        adapter.setOnItemClickGridListener(new PagerAdapter.OnItemClickGridListener() {
            @Override
            public void onItemClickGridListener(int pager, int position) {
                Log.d("callback", "pager->" + pager + ",position->" + position);
            }
        });


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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


}
