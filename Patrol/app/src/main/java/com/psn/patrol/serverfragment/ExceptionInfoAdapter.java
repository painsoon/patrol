package com.psn.patrol.serverfragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.psn.patrol.R;
import com.psn.patrol.bean.ExeceptionInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Author: shinianPan on 2017/5/2.
 * email : snow_psn@163.com
 */

public class ExceptionInfoAdapter extends BaseAdapter{

    private Context context;

    private GridViewAdapter adapter;
    private String photo[];

    //信息集合
    private ArrayList<ExeceptionInfo> newsList;

    public ExceptionInfoAdapter(Context context, ArrayList<ExeceptionInfo> newsList){
        this.context = context;
        this.newsList = newsList;
    }
    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public ExeceptionInfo getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ReviewHolder holder = null;
        if (convertView == null) {
            holder = new ReviewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.ll_news, null);

            holder.ll_owner = (TextView) convertView.findViewById(R.id.ll_owner);
            holder.ll_context = (TextView) convertView.findViewById(R.id.ll_context);
            holder.ll_date = (TextView) convertView.findViewById(R.id.ll_date);
            holder.ll_tagID = (TextView) convertView.findViewById(R.id.ll_tagID);
            holder.noScrollgridview = (GridView) convertView.findViewById(R.id.noScrollgridview);
            convertView.setTag(holder);

        }else{
            holder = (ReviewHolder) convertView.getTag();
        }

        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        holder.ll_owner.setText("路线："+newsList.get(position).getPathId());
        holder.ll_tagID.setText("检查点："+newsList.get(position).getCheckPoint());
        holder.ll_context.setText("异常描述："+newsList.get(position).getAbnormalText());
        holder.ll_date.setText(dateformat.format(Long.parseLong(newsList.get(position).getCreateTime())));
        photo = newsList.get(position).getAbnormalImage();
        adapter = new GridViewAdapter();
        holder.noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        holder.noScrollgridview.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return convertView;
    }

    class ReviewHolder {
        TextView ll_owner;
        TextView ll_context;
        TextView ll_tagID;
        TextView ll_date;
        GridView noScrollgridview;
    }
    private class GridViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return photo.length;
        }

        @Override
        public Object getItem(int position) {
            return photo[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView==null){
                convertView = LayoutInflater.from(context)
                        .inflate(R.layout.item_published_grida, parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView
                        .findViewById(R.id.item_grida_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            byte[] bytes = Base64.decode(photo[position], Base64.DEFAULT);
            Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            holder.image.setImageBitmap(bm);

            return convertView;
        }
        private class ViewHolder {
            public ImageView image;
        }
    }
}
