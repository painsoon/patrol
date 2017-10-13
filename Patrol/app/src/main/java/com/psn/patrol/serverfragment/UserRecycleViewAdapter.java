package com.psn.patrol.serverfragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.psn.patrol.R;
import com.psn.patrol.Readprefer;
import com.psn.patrol.bean.Basespot;
import com.psn.patrol.bean.User;
import com.psn.patrol.util.HttpUrl;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class UserRecycleViewAdapter extends RecyclerView.Adapter<UserRecycleViewAdapter.MyViewHolder> implements RecycleItemTouchHelper.ItemTouchHelperCallback{

    private List<User> list ;
    private Context context;
    public UserRecycleViewAdapter(List<User> list) {
        this.list = list;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_username;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_username = (TextView) itemView.findViewById(R.id.check_guyname);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_pp_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        User msg = list.get(position);
        holder.tv_username.setText(msg.getName());
    }
    @Override
    public void onItemDelete(int position) {
        okHttp(position);
        list.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
        Collections.swap(list,fromPosition,toPosition);//交换数据
        notifyItemMoved(fromPosition,toPosition);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public void okHttp(int position){
        Log.e("psn",list.get(position).getId());
        Map<String,String> map = Readprefer.getUserID(context);
        String custom = map.get("customId");
        OkHttpUtils.get()
                .url(HttpUrl.url_userdelete)
                .addParams("id",list.get(position).getId())
                .addParams("customId",custom)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(context, "服务器似乎出问题了", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Toast.makeText(context, "员工删除成功！", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
