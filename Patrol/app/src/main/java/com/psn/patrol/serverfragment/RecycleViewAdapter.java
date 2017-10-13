package com.psn.patrol.serverfragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.psn.patrol.R;
import com.psn.patrol.bean.Basespot;

import java.text.SimpleDateFormat;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {

    private List<Basespot> list;

    public RecycleViewAdapter(List<Basespot> list) {
        this.list = list;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_username, tv_time, tv_message;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_username = (TextView) itemView.findViewById(R.id.tv_username);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_message = (TextView) itemView.findViewById(R.id.tv_message);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Basespot msg = list.get(position);
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        holder.tv_username.setText("巡检点名称："+msg.getTagName());
        holder.tv_time.setText(dateformat.format(Long.parseLong(msg.getStartTime())));
        holder.tv_message.setText("标签id"+msg.getTagId());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
