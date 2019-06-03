package com.duke.xlws;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * 创建：duke
 * 注释：
 * 日期：2018/3/19.
 */


public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.ViewHolder> {
    private Context ctx;
    private List<String> list;
    private OnRecyclerViewItemClickListener clickListener;

    public MainRecyclerViewAdapter(Context ctx, List<String> list) {
        this.ctx = ctx;
        this.list = list;
    }

    public void setClickListener(OnRecyclerViewItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.main_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.nameTv.setText(list.get(position));

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onRecyclerViewItemClickListener(position, holder.nameTv);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        TextView nameTv;


        public ViewHolder(View itemView) {
            super(itemView);
            layout = (LinearLayout) itemView.findViewById(R.id.rv_layout);
            nameTv = (TextView) itemView.findViewById(R.id.rv_name);
        }
    }


    public interface OnRecyclerViewItemClickListener {
        void onRecyclerViewItemClickListener(int position, View... view);
    }
}
