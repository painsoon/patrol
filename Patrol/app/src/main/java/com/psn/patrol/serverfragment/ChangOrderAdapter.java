package com.psn.patrol.serverfragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.psn.patrol.R;
import com.psn.patrol.bean.Node;
import com.psn.patrol.bean.PathTest;
import com.psn.patrol.view.MyCircleImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Author: shinianPan on 2017/5/2.
 * email : snow_psn@163.com
 */

public class ChangOrderAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<Node> pathTests;

    public ChangOrderAdapter(Context context,ArrayList<Node> pathTests) {
        this.context = context;
        this.pathTests = pathTests;
    }

    @Override
    public int getCount() {
        return pathTests.size();
    }

    @Override
    public Object getItem(int position) {
        return pathTests.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_layout,null);

            viewHolder.ll_pathName = (TextView) convertView.findViewById(R.id.text_title);
            viewHolder.ll_date = (TextView) convertView.findViewById(R.id.text_date);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        viewHolder.ll_pathName.setText("路线: "+pathTests.get(position).pathName);
        viewHolder.ll_date.setText(dateFormat.format(Long.parseLong(pathTests.get(position).tg)));

        return convertView;
    }

    private class ViewHolder{
        TextView ll_pathName;
        TextView ll_date;
    }

}
