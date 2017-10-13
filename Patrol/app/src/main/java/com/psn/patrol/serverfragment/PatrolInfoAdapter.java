package com.psn.patrol.serverfragment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.psn.patrol.R;
import com.psn.patrol.bean.PathTest;

import java.util.ArrayList;

/**
 * Author: shinianPan on 2017/5/2.
 * email : snow_psn@163.com
 */

public class PatrolInfoAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<PathTest> taglist;

    public PatrolInfoAdapter(Context context, ArrayList<PathTest> taglist) {
        this.context = context;
        this.taglist = taglist;
        Log.e("spn",taglist.toString());
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_patrolinfo_layout,parent,false);
            viewhodler = new Viewhodler();
            viewhodler.right = (ImageView) convertView.findViewById(R.id.right);
            viewhodler.pathId  = (TextView) convertView.findViewById(R.id.tagid);
            viewhodler.pathGuy  = (TextView) convertView.findViewById(R.id.guyname);
            convertView.setTag(viewhodler);
        }else{
            viewhodler = (Viewhodler) convertView.getTag();
        }

        viewhodler.pathId.setText("线路："+taglist.get(position).getPathID());
        viewhodler.pathGuy.setText(taglist.get(position).getTagGuy());
        if("1".equals(taglist.get(position).getRight())){
            viewhodler.right.setImageResource(R.drawable.dui);
        }else if("0".equals(taglist.get(position).getRight())){
            viewhodler.right.setImageResource(R.drawable.cha);
        }

        return convertView;
    }
    private class Viewhodler{
        TextView pathId;
        TextView pathGuy;
        ImageView right;
    }

}
