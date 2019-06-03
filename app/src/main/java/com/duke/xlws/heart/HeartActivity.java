package com.duke.xlws.heart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.duke.xlws.R;

/**
 * 创建：duke
 * 注释：
 * 版本大小：17
 * 版本名称：v2.1.4
 * 日期：2018/6/25.
 */


public class HeartActivity extends AppCompatActivity {
    private HeartRateView heartRateView;
    private HeartAllRateView allRateView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart);

        heartRateView = (HeartRateView) findViewById(R.id.heart_rate_view);
        allRateView = (HeartAllRateView) findViewById(R.id.heart_rate_all_view);

        heartRateView.setmAllRateView(allRateView);
        allRateView.setmRateView(heartRateView);

        heartRateView.setData(HeartUtils.parse(getResources().getString(R.string.heart_data)), HeartUtils.avg);
        allRateView.setData(HeartUtils.parse(getResources().getString(R.string.heart_data)), HeartUtils.avg);
    }
}
