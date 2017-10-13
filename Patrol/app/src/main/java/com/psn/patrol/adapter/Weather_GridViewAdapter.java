package com.psn.patrol.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.psn.patrol.R;
import com.psn.patrol.bean.Weather_grid_Item;

import java.util.List;

/**
 * Created by wsk on 2016/5/10.
 */
public class Weather_GridViewAdapter extends BaseAdapter {
    Context context;
    List<Weather_grid_Item> list;
    public Weather_GridViewAdapter() {
    }

    public Weather_GridViewAdapter(Context context, List<Weather_grid_Item> list) {
        this.context = context;
        this.list = list;
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
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.grid_item_weather,null);
        TextView tv_time = (TextView) convertView.findViewById(R.id.grid_weather_time);
        TextView tv_temp = (TextView) convertView.findViewById(R.id.grid_weather_temp);
        TextView tv_state = (TextView) convertView.findViewById(R.id.grid_weather_state);
        ImageView weather_iv = (ImageView) convertView.findViewById(R.id.grid_weather_iv);
        Weather_grid_Item weather_grid_item =list.get(position);
        tv_temp.setText(weather_grid_item.getTemp());
        tv_state.setText(weather_grid_item.getState());
        tv_time.setText(weather_grid_item.getTime());
        weather_iv.setImageBitmap(weather_grid_item.getWeather());
        return convertView;
    }

}
