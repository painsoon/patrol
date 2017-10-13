package com.psn.patrol.serverfragment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.psn.patrol.bean.BindPath;

import java.util.List;
import com.psn.patrol.R;
/**
 * Author: shinianPan on 2017/5/22.
 * email : snow_psn@163.com
 */

public class BindPathAdapter extends BaseAdapter{

    private List<BindPath> data;
    private Context context;
    private BindPathAty.AllCheckListener allCheckListener;
    private StringBuilder strtemp = new StringBuilder();

    public BindPathAdapter(Context context,List<BindPath> data,  BindPathAty.AllCheckListener allCheckListener) {
        this.data = data;
        this.context = context;
        this.allCheckListener = allCheckListener;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHoder hd;
        if (view == null) {
            hd = new ViewHoder();
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.item_list, null);
            hd.textView = (TextView) view.findViewById(R.id.text_title);
            hd.checkBox = (CheckBox) view.findViewById(R.id.ckb);
            view.setTag(hd);
        }
        BindPath mModel = data.get(i);
        hd = (ViewHoder) view.getTag();
        hd.textView.setText("路线："+mModel.getPathId());

        Log.e("myadapter", mModel.getPathId() + "------" + mModel.isCheck());
        final ViewHoder hdFinal = hd;
        hd.checkBox.setChecked(mModel.isCheck());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = hdFinal.checkBox;
                if (checkBox.isChecked()) {
                    checkBox.setChecked(false);
                    data.get(i).setCheck(false);
                } else {
                    Log.e("psn","被选中"+i);
                    strtemp.append(data.get(i).getPathId()+",");
                    checkBox.setChecked(true);
                    data.get(i).setCheck(true);
                }
                //监听每个item，若所有checkbox都为选中状态则更改main的全选checkbox状态
                for (BindPath model : data) {
                    if (!model.isCheck()) {
                        allCheckListener.onCheckedChanged(false);
                        return;
                    }
                }
                allCheckListener.onCheckedChanged(true);


            }
        });


        return view;
    }

    public String getCheck(){

        return strtemp.toString();
    }
    class ViewHoder {
        TextView textView;
        CheckBox checkBox;
    }
}
