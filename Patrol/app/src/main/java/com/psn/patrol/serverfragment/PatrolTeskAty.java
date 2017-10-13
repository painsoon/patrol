package com.psn.patrol.serverfragment;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.psn.patrol.R;
import com.psn.patrol.Readprefer;
import com.psn.patrol.bean.Puser;
import com.psn.patrol.util.HttpUrl;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Map;

import okhttp3.Call;

public class PatrolTeskAty extends ListActivity {

    private PatrolTeskAdapter ptadapter;

    private ArrayList<Puser> users;
    private ImageView topButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_patrol_tesk_aty);

        init();
        okHttp();
    }

    public void init(){
        topButton = (ImageView) findViewById(R.id.topButton);
        topButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        users  = new ArrayList<>();
        ptadapter = new PatrolTeskAdapter(this,users);
    }

    public void okHttp(){
        Map<String,String> map = Readprefer.getUserID(this);
        OkHttpUtils
                .post()
                .addParams("customId",map.get("customId"))
                .url(HttpUrl.url_puser)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(PatrolTeskAty.this, "服务器似乎出问题了", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        String json = null;
                        try {
                             json = URLDecoder.decode(response,"utf-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        Log.e("psn",json.toString());
                        if("-1".equals(response)){
                            Toast.makeText(PatrolTeskAty.this, "该公司尚未录入巡逻员！", Toast.LENGTH_SHORT).show();
                        }else{
                            try {
                                getData(json);
                                setListAdapter(ptadapter);
                                ptadapter.notifyDataSetChanged();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });
    }

    public void getData(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        for(int i=0;i<jsonArray.length();i++){
            Puser puser = new Puser();
            JSONObject jb = jsonArray.getJSONObject(i);
            puser.setName(jb.getString("loginName"));
            puser.setId(jb.getString("id"));
            users.add(puser);
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        Intent intent  = new Intent(PatrolTeskAty.this, BindPathAty.class);
        intent.putExtra("userId",users.get(position).getId());
        Log.e("psn",users.get(position).getId());
        startActivity(intent);
        super.onListItemClick(l, v, position, id);
    }
}
