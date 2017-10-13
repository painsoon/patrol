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
import com.psn.patrol.bean.Puser;
import com.psn.patrol.view.MyCircleImageView;

import java.util.ArrayList;

/**
 * Author: shinianPan on 2017/5/2.
 * email : snow_psn@163.com
 */

public class PatrolTeskAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<Puser> pathTests;

    public PatrolTeskAdapter(Context context,ArrayList<Puser> pathTests) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_pp_layout,null);

            viewHolder.ll_guyname = (TextView) convertView.findViewById(R.id.check_guyname);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.ll_guyname.setText(pathTests.get(position).getName());

        return convertView;
    }

    private class ViewHolder{
        TextView ll_guyname;
    }
}
