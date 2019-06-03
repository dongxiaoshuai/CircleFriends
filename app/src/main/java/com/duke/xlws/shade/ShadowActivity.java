package com.duke.xlws.shade;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.duke.xlws.R;

/**
 * 创建：duke
 * 注释：阴影
 * 版本大小：17
 * 版本名称：v2.1.4
 * 日期：2018/7/9.
 */


public class ShadowActivity extends AppCompatActivity {

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shadow);
        ShadowViewHelper.bindShadowHelper(
                new ShadowProperty()
                        .setShadowColor(Color.parseColor("#99000000"))
                        .setShadowRadius(dip2px(4))
                        .setShadowSide(ShadowProperty.LEFT)
                , findViewById(R.id.view_1)
                , Color.parseColor("#ffffff"));

    }


    public int dip2px(float dipValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
