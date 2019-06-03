package com.duke.xlws.share;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.duke.xlws.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 创建：duke
 * 注释：共享元素
 * 日期：2018/3/28.
 */


public class ShareActivity extends AppCompatActivity {
    private RecyclerView rv;

    private List<String> list;
    private RecyclerViewAdapter adapter;
    private int index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);


        initView();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initView() {
        rv = (RecyclerView) findViewById(R.id.rv);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(gridLayoutManager);


        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i + "-image");
        }
        adapter = new RecyclerViewAdapter(this, list);
        adapter.setClickListener(new RecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onRecyclerViewItemClickListener(int position, View... view) {
                skipActivity(view, position);
            }
        });
        rv.setAdapter(adapter);

        //这个可以看做个管道
        //每次进入和退出的时候都会进行调用
        //进入的时候传递共享元素的信息
        //退出的时候获取信息
        //同时向sharedElements里面put view,跟对view添加transitionname作用一样
        setExitSharedElementCallback(new SharedElementCallback() {
            @SuppressLint({"LongLogTag", "NewApi"})
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                Log.d("callback", "setExitSharedElementCallback->--------------");
                if (index == -1) return;
                names.clear();
                sharedElements.clear();
                View itemView = gridLayoutManager.findViewByPosition(index);
                ImageView imageView = (ImageView) itemView.findViewById(R.id.rv_image);
                sharedElements.put(list.get(index), imageView);
                index = -1;

                StringBuilder buffer = new StringBuilder();
                buffer.append("sharedElements :");
                for (String key : sharedElements.keySet()) {
                    buffer.append("<").append(key).append(">");
                }
                Log.d("callback", "setExitSharedElementCallback->" + buffer.toString());

            }
        });
    }


    private void skipActivity(View[] shareView, int position) {
        Intent intent = new Intent(this, InfoActivity.class);
        intent.putExtra("index", position);
        intent.putStringArrayListExtra("data", (ArrayList<String>) list);
        // 这里指定了共享的视图元素
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, shareView[0], list.get(position));

//        ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(this, shareView, "image");
//        Pair pair = new Pair(shareView[0], "image_1");
//        Pair pair1 = new Pair(shareView[1], "image_2");
//        Pair pair2 = new Pair(shareView[2], "image_3");
//        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pair, pair1, pair2);
//        ActivityCompat.startActivity(this, intent, options.toBundle());

        ActivityCompat.startActivityForResult(this, intent, 1, options.toBundle());
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        index = data.getIntExtra("index", -1);
        Log.d("callback", "onActivityReenter->" + index);
    }

}
