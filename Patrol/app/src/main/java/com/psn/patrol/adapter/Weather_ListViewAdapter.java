package com.psn.patrol.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.psn.patrol.R;
import com.psn.patrol.bean.Weather_list_Item;

import java.util.List;

/**
 * Created by wsk on 2016/5/10.
 */
public class Weather_ListViewAdapter extends BaseAdapter {
    Context context;
    List<Weather_list_Item> list;
    public Weather_ListViewAdapter() {
    }

    public Weather_ListViewAdapter(Context context, List<Weather_list_Item> list) {
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
        convertView = layoutInflater.inflate(R.layout.list_item_weather,null);
        TextView list_weather_tv_day = (TextView) convertView.findViewById(R.id.list_weather_tv_day);
        TextView list_weather_temp1 = (TextView) convertView.findViewById(R.id.list_weather_temp1);
        TextView list_weather_temp2 = (TextView) convertView.findViewById(R.id.list_weather_temp2);
        TextView list_weather_wind = (TextView) convertView.findViewById(R.id.list_weather_wind);
        Weather_list_Item weather_list_item =list.get(position);
        list_weather_tv_day.setText(weather_list_item.getDay());
        list_weather_temp1.setText(weather_list_item.getTemp1());
        list_weather_temp2.setText(weather_list_item.getTemp2());
        list_weather_wind.setText(weather_list_item.getWind());
        return convertView;
    }

}
