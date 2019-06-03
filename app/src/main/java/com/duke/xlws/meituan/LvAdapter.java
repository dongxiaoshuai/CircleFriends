package com.duke.xlws.meituan;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.duke.xlws.R;

import java.util.List;

/**
 * 创建：duke
 * 注释：
 * 版本大小：19
 * 版本名称：v2.3.0
 * 日期：2018/10/30.
 */


public class LvAdapter extends BaseAdapter {
    private List<String> list;
    private Context ctx;
    private int selectPosition = 0;

    public LvAdapter(List<String> list, Context ctx) {
        this.list = list;
        this.ctx = ctx;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.take_out_options_item, parent, false);
            holder = new ViewHolder();
            holder.nameTv = (TextView) convertView.findViewById(R.id.item_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position == selectPosition) {
            holder.nameTv.setBackgroundColor(Color.parseColor("#ffffff"));
        } else {
            holder.nameTv.setBackgroundColor(Color.parseColor("#efefef"));
        }
        holder.nameTv.setText(list.get(position));


        return convertView;
    }

    class ViewHolder {
        TextView nameTv;
    }
}
