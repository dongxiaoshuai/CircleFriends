package com.duke.xlws.line;

import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.duke.xlws.R;
import com.duke.xlws.circle.widget.MultipleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建：duke
 * 注释：
 * 版本大小：19
 * 版本名称：v2.3.0
 * 日期：2018/7/30.
 */


public class LineActivity extends AppCompatActivity {
    private MultipleImageView imageView;
    private List<Integer> mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line);
        imageView = (MultipleImageView) findViewById(R.id.multipleImageView);
        mList = new ArrayList<>();
        mList.add(R.mipmap.ic_rv_image);
        mList.add(R.mipmap.ic_rv_image);
        mList.add(R.mipmap.ic_rv_image);
        mList.add(R.mipmap.ic_rv_image);
//        mList.add(R.mipmap.ic_rv_image);
//        mList.add(R.mipmap.ic_rv_image);
//        mList.add(R.mipmap.ic_rv_image);
//        mList.add(R.mipmap.ic_rv_image);
//        mList.add(R.mipmap.ic_rv_image);
//        mList.add(R.mipmap.ic_rv_image);
//        mList.add(R.mipmap.ic_rv_image);
//        mList.add(R.mipmap.ic_rv_image);

        imageView.setImages(mList);

        System.out.println(new B().getValue());

    }

    public void onBack(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LineActivity.this.finish();
            }
        }).start();


    }


    static class A {
        protected int value;

        public A(int v) {// 5
            setValue(v);
        }

        public void setValue(int value) {
            this.value = value; // 10  22
        }

        public int getValue() {
            try {
                value++; //11
                return value; //11
            } catch (Exception e) {
                System.out.println(e.toString());
            } finally {
                this.setValue(value);  // 22
                System.out.println(value); // 22
            }
            return value;
        }
    }

    static class B extends A {
        public B() {
            super(5);
            setValue(getValue() - 3); // 8
        }

        public void setValue(int value) {
            super.setValue(2 * value); // 10  22
        }
    }
}
