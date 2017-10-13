package com.psn.patrol.serverfragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.psn.patrol.R;

import java.util.ArrayList;

/**
 * Author: shinianPan on 2017/4/27.
 * email : snow_psn@163.com
 * 增加路线
 */

public class AddPathAdapter extends BaseAdapter {

    private Context context;

    private ArrayList<String[]> list;
    public AddPathAdapter(Context context,ArrayList list){
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
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
        Hodler hodler = null;
        if(convertView == null){
            hodler = new Hodler();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_addpath_layout,null);
            hodler.ll_ID = (TextView) convertView.findViewById(R.id.pathID_item);
            convertView.setTag(hodler);
        }else{
            hodler = (Hodler) convertView.getTag();
        }

        hodler.ll_ID.setText(list.get(position)[0]);
        return convertView;
    }
    private class Hodler{
        TextView ll_ID;
    }
}
