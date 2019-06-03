package com.duke.xlws.meituan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.duke.xlws.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建：duke
 * 注释：外卖页面
 * 版本大小：19
 * 版本名称：v2.3.0
 * 日期：2018/10/30.
 */


public class TakeOutActivity extends AppCompatActivity {
    private ListView mLv;
    private GridView mGv;

    private LvAdapter lvAdapter;
    private GvAdapter gvAdapter;
    private List<String> mLvList;
    private List<List<String>> mGvList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_out);

        initData();
    }

    private void initData() {

        mLv = (ListView) findViewById(R.id.lv);
        mGv = (GridView) findViewById(R.id.gv);

        mLvList = new ArrayList<>();
        mGvList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            mLvList.add("主食--" + i);
            List<String> mList = new ArrayList<>();
            for (int z = 0; z < 50; z++) {
                mList.add("主食-" + i + "-" + z);
            }
            mGvList.add(mList);
        }

        lvAdapter = new LvAdapter(mLvList, this);
        mLv.setAdapter(lvAdapter);

        gvAdapter = new GvAdapter(mGvList.get(0), this);
        mGv.setAdapter(gvAdapter);

        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gvAdapter.setList(mGvList.get(position));
                lvAdapter.setSelectPosition(position);
            }
        });

    }
}
