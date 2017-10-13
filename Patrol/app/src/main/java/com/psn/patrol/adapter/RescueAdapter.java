package com.psn.patrol.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.psn.patrol.R;
import com.psn.patrol.bean.HelpUser;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */

public class RescueAdapter extends BaseAdapter {

    private List<HelpUser> list;
    private Context context;
    public RescueAdapter(Context context,List<HelpUser> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public HelpUser getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHodler hodler ;
        if(convertView==null){
            hodler = new ViewHodler();
            convertView = LayoutInflater.from(context).inflate(R.layout.rescue_item_layout,null);
            hodler.phoneView = (TextView) convertView.findViewById(R.id.phonenumview);
            hodler.phonenum = (TextView) convertView.findViewById(R.id.phonenum);
            convertView.setTag(hodler);
        }else {
            hodler = (ViewHodler) convertView.getTag();
        }

        hodler.phoneView.setText(list.get(position).getName());
        hodler.phonenum.setText(list.get(position).getPhone());

        return convertView;
    }

    class ViewHodler{
        TextView phoneView;
        TextView phonenum;
    }
}
