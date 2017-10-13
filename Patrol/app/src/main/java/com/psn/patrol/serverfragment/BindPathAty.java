package com.psn.patrol.serverfragment;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.psn.patrol.R;
import com.psn.patrol.Readprefer;
import com.psn.patrol.bean.BindPath;
import com.psn.patrol.util.HttpUrl;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

public class BindPathAty extends ListActivity {

    private ListView mListView;
    private List<BindPath> models;
    private BindPathAdapter mMyAdapter;
    //监听来源
    public boolean mIsFromItem = false;

    private String userId;

    private Button okBind;
    private TextView tishi;
    private ImageView topButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bind_path);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");

        Log.e("psn",userId+"======");

        okHttp();
        initView();


    }

    public void initView(){

        topButton = (ImageView) findViewById(R.id.topButton);
        topButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tishi = (TextView) findViewById(R.id.tishi);
        okBind = (Button) findViewById(R.id.okBind);
        okBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitOkhttp();
                finish();
            }
        });
    }

    public void submitOkhttp(){
        String  str = mMyAdapter.getCheck();
        Log.e("psn",str+"========"+userId);
        OkHttpUtils
                .post()
                .url(HttpUrl.url_Assignpath)
                .addParams("ids",str)
                .addParams("userId",userId)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response, int id) throws Exception {
                        return null;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(BindPathAty.this, "服务器似乎出问题了", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(Object response, int id) {
                        Toast.makeText(BindPathAty.this, "任务已分配！", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void okHttp() {

        Map<String, String> map = Readprefer.getUserID(this);
        OkHttpUtils
                .post()
                .addParams("customId", map.get("customId"))
                .url(HttpUrl.url_pathall)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(BindPathAty.this, "服务器似乎出问题了", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        String json = null;
                        try {
                            json = URLDecoder.decode(response, "utf-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        Log.e("psn", json.toString());

                        try {
                            getData(json);
                            if(lists.size()==0){
                                tishi.setText("没找到可分配路线。。。");
                            }
                            mMyAdapter = new BindPathAdapter(BindPathAty.this, lists, new AllCheckListener() {
                                @Override
                                public void onCheckedChanged(boolean b) {
//                                    //根据不同的情况对maincheckbox做处理
//                                    if (!b && !mMainCkb.isChecked()) {
//                                        return;
//                                    } else if (!b && mMainCkb.isChecked()) {
//                                        mIsFromItem = true;
//                                        mMainCkb.setChecked(false);
//                                    } else if (b) {
//                                        mIsFromItem = true;
//                                        mMainCkb.setChecked(true);
//                                    }
                                }
                            });
                            setListAdapter(mMyAdapter);
                            mMyAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }
    //对item导致maincheckbox改变做监听
    interface AllCheckListener {
        void onCheckedChanged(boolean b);
    }
    public void getData(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            BindPath bindPath = new BindPath();
            JSONObject jb = jsonArray.getJSONObject(i);
            bindPath.setPathId(jb.getString("id"));
            bindPath.setCheck(false);
            lists.add(bindPath);
        }
    }

    private List<BindPath> lists = new ArrayList<>();
    private String pathId;

}
