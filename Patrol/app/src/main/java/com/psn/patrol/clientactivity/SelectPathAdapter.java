package com.psn.patrol.clientactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.psn.patrol.R;
import com.psn.patrol.bean.Node;

import java.util.ArrayList;

/**
 * Author: shinianPan on 2017/4/25.
 * email : snow_psn@163.com
 */

public class SelectPathAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Node> pathTests;

    public SelectPathAdapter(Context context,ArrayList<Node> pathTests) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_selectpath_layout,null);

            viewHolder.ll_pathName = (TextView) convertView.findViewById(R.id.path_name);
            viewHolder.ll_guyname = (TextView) convertView.findViewById(R.id.check_guyname);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.ll_pathName.setText("路线: "+pathTests.get(position).ph);
        viewHolder.ll_guyname.setText(pathTests.get(position).tg);

        return convertView;
    }

    private class ViewHolder{
        TextView ll_pathName;
        TextView ll_guyname;
    }
}
