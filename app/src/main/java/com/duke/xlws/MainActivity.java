package com.duke.xlws;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.duke.xlws.circle.FriendsCircleActivity;
import com.duke.xlws.dialog.DialogActivity;
import com.duke.xlws.file.FileOperationsActivity;
import com.duke.xlws.gesture.GestureActivity;
import com.duke.xlws.heart.HeartActivity;
import com.duke.xlws.line.LineActivity;
import com.duke.xlws.meituan.TakeOutActivity;
import com.duke.xlws.pager.ViewPagerActivity;
import com.duke.xlws.pdf.PDFActivity;
import com.duke.xlws.pdf.PDFWebActivity;
import com.duke.xlws.shade.ShadowActivity;
import com.duke.xlws.share.ShareActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rv;

    private List<String> list;
    private MainRecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        rv = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);


        list = new ArrayList<>();

        list.add("共享元素");
        list.add("心电图");
        list.add("阴影view");
        list.add("PDF预览");
        list.add("PDF WEB预览");
        list.add("测试LinearLayout属性");
        list.add("viewpager for gridview");
        list.add("外卖商户");
        list.add("dialog");
        list.add("朋友圈");
        list.add("文件操作");
        list.add("手势滑动");
        adapter = new MainRecyclerViewAdapter(this, list);
        adapter.setClickListener(new MainRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onRecyclerViewItemClickListener(int position, View... view) {
                switch (position) {
                    case 0://共享元素
                        skipActivity(ShareActivity.class);
                        break;
                    case 1:
                        skipActivity(HeartActivity.class);
                        break;
                    case 2:
                        skipActivity(ShadowActivity.class);
                        break;
                    case 3:
                        skipActivity(PDFActivity.class);
                        break;
                    case 4:
                        skipActivity(PDFWebActivity.class);
                        break;
                    case 5:
                        skipActivity(LineActivity.class);
                        break;
                    case 6:
                        skipActivity(ViewPagerActivity.class);
                        break;
                    case 7:
                        skipActivity(TakeOutActivity.class);
                        break;
                    case 8:
                        skipActivity(DialogActivity.class);
                        break;
                    case 9:
                        skipActivity(FriendsCircleActivity.class);
                        break;
                    case 10:
                        skipActivity(FileOperationsActivity.class);
                        break;
                    case 11:
                        skipActivity(GestureActivity.class);
                        break;

                }
            }
        });

        rv.setAdapter(adapter);
    }


    private void skipActivity(Class<?> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

}
