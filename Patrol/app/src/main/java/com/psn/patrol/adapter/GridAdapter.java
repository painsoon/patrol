package com.psn.patrol.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.psn.patrol.R;
import com.psn.patrol.bean.PathTest;
import com.psn.patrol.view.MyCircleImageView;

import java.util.ArrayList;

/**
 * Author: shinianPan on 2017/4/1.
 * email : snow_psn@163.com
 */

public class GridAdapter extends BaseAdapter {


    private Context context;
    private ArrayList<PathTest> pathTests;
    private int changId=-1;

    public GridAdapter(Context context, ArrayList<PathTest> pathTests) {
        this.context = context;
        this.pathTests = pathTests;
    }

    @Override
    public int getCount() {
        return pathTests.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RouteHodler routeHodler;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.route_view_layout
                    ,parent,false);
            routeHodler = new RouteHodler();
            routeHodler.pointView = (MyCircleImageView) convertView.findViewById(R.id.point_view);
            routeHodler.routeView = (ImageView) convertView.findViewById(R.id.route_view);
            routeHodler.tagName = (TextView) convertView.findViewById(R.id.tag_name);
            convertView.setTag(routeHodler);
        }else{
            routeHodler = (RouteHodler) convertView.getTag();
        }

        routeHodler.tagName.setText(pathTests.get(position).getTagName());

        if(position<changId){
            routeHodler.pointView.setImageResource(R.drawable.d2);
        }

        if(changId==getCount()){
            routeHodler.pointView.stopFlick();
            routeHodler.pointView.setImageResource(R.drawable.d2);
        }
        if(position==changId){
            routeHodler.pointView.startFlick();
        }else {
            routeHodler.pointView.stopFlick();
        }

        return convertView;
    }

    private class RouteHodler{
        MyCircleImageView pointView;
        ImageView routeView;
        TextView tagName;
    }

    public void setChangId(int position){
        this.changId = position;
    }

}
