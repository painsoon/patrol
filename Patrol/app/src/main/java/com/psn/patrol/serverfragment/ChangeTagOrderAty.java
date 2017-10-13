package com.psn.patrol.serverfragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.psn.patrol.R;
import com.psn.patrol.bean.Basespot;
import com.psn.patrol.util.HttpUrl;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;

public class ChangeTagOrderAty extends Activity {
    private RecyclerView rv;
    private RecycleViewAdapter adapter;
    private List<Basespot> list = new ArrayList<>();
    private String pathId;
    private TextView activity_selectimg_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_change_tag_order_aty);

        Intent intent = getIntent();
        pathId = intent.getStringExtra("pathID");
        initOkhttp();

        init();

    }

    public void init(){
        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));

        //为RecycleView绑定触摸事件
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                //首先回调的方法 返回int表示是否监听该方向
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;//拖拽
                //  int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;//侧滑删除
                // return makeMovementFlags(dragFlags, swipeFlags);
                return makeMovementFlags(dragFlags, 0);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //滑动事件
                Collections.swap(list, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                adapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());

                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //侧滑事件
                // list.remove(viewHolder.getAdapterPosition());
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }

            @Override
            public boolean isLongPressDragEnabled() {
                //是否可拖拽
                return true;
            }
        });
        helper.attachToRecyclerView(rv);

        ImageView topButton = (ImageView) findViewById(R.id.topButton);
        topButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        activity_selectimg_send = (TextView) findViewById(R.id.activity_selectimg_send);
        activity_selectimg_send.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                submitOkhttp();
            }
        });
    }

    public void initOkhttp(){

        OkHttpUtils
                .post()
                .url(HttpUrl.url_spotList)
                .addParams("pathId",pathId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(ChangeTagOrderAty.this, "服务器似乎出问题了", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        try {
                            list = getPath(URLDecoder.decode(response,"utf-8"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        adapter = new RecycleViewAdapter(list);
                        rv.setAdapter(adapter);
                    }
                });
    }
    private List<Basespot> getPath(String json) throws JSONException{

       // Log.e("psn",json);
        JSONArray jsonArray = new JSONArray(json);

        for(int i=0;i<jsonArray.length();i++){
            JSONObject jb = jsonArray.getJSONObject(i);
            Basespot basespot = new Basespot();
            basespot.setTagName(jb.getString("tagName"));
            basespot.setStartTime(jb.getString("createTime"));
            basespot.setTagId(jb.getString("tagId"));
            basespot.setId(jb.getString("id"));
            list.add(basespot);
        }
        return list;
    }

    public void submitOkhttp(){

        OkHttpUtils
                .post()
                .url(HttpUrl.url_updata)
                .addParams("pathId",pathId)
                .addParams("list",new Gson().toJson(list).toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(ChangeTagOrderAty.this, "服务器似乎出问题了", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        Toast.makeText(ChangeTagOrderAty.this, "路径变更成功！", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
}