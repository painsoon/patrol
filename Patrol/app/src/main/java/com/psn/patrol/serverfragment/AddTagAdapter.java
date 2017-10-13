package com.psn.patrol.serverfragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.psn.patrol.R;
import com.psn.patrol.bean.Basespot;
import com.psn.patrol.bean.PathTest;
import com.psn.patrol.view.MyCircleImageView;

import java.util.ArrayList;

/**
 * Author: shinianPan on 2017/5/2.
 * email : snow_psn@163.com
 */

public class AddTagAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<Basespot> taglist;

    public AddTagAdapter(Context context, ArrayList<Basespot> taglist) {
        this.context = context;
        this.taglist = taglist;
    }

    @Override
    public int getCount() {
        return taglist.size();
    }

    @Override
    public Object getItem(int position) {
        return taglist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Viewhodler viewhodler = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_addtag_layout,parent,false);
            viewhodler = new Viewhodler();
            viewhodler.pointView = (MyCircleImageView) convertView.findViewById(R.id.point_tagview);
            viewhodler.tagName  = (TextView) convertView.findViewById(R.id.point_tagname);
            convertView.setTag(viewhodler);
        }else{
            viewhodler = (Viewhodler) convertView.getTag();
        }
        viewhodler.tagName.setText(taglist.get(position).getTagName());

        return convertView;
    }
    private class Viewhodler{
        MyCircleImageView pointView;
        TextView tagName;
    }

}
