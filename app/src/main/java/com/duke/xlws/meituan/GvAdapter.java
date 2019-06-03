package com.duke.xlws.meituan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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


public class GvAdapter extends BaseAdapter {
    private List<String> list;
    private Context ctx;

    public void setList(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    public GvAdapter(List<String> list, Context ctx) {
        this.list = list;
        this.ctx = ctx;
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

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.take_out_content_item, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.item_gv_image);
            holder.nameTv = (TextView) convertView.findViewById(R.id.item_gv_name_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.nameTv.setText(list.get(position));

        return convertView;
    }

    class ViewHolder {
        ImageView image;
        TextView nameTv;
    }
}
