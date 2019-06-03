package com.duke.xlws.pager;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.duke.xlws.R;

import java.util.List;

/**
 * 创建：duke
 * 注释：
 * 版本大小：19
 * 版本名称：v2.3.0
 * 日期：2018/10/9.
 */


public class PagerAdapter extends android.support.v4.view.PagerAdapter {
    private List<List<String>> list;
    private Context ctx;
    private OnItemClickGridListener onItemClickGridListener;


    public void setOnItemClickGridListener(OnItemClickGridListener onItemClickGridListener) {
        this.onItemClickGridListener = onItemClickGridListener;
    }

    public PagerAdapter(List<List<String>> list, Context ctx) {
        this.list = list;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.pager_layout, null);
        CustomGridView gridView = (CustomGridView) view.findViewById(R.id.pager_grid);
        GridViewAdapter adapter = new GridViewAdapter(list.get(position), ctx);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                if (onItemClickGridListener != null) {
                    onItemClickGridListener.onItemClickGridListener(position, pos);
                }
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    public interface OnItemClickGridListener {
        void onItemClickGridListener(int pager, int position);
    }

}
