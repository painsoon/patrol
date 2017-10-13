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
import com.psn.patrol.bean.Node;
import com.psn.patrol.clientactivity.PatrolSituationAty;
import com.psn.patrol.util.HttpUrl;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import okhttp3.Call;

public class ChangeOrderAty extends ListActivity {
    private ArrayList<Node> pathTags = new ArrayList<>() ; //路线任务
    private ChangOrderAdapter selectPathAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 无标题
        setContentView(R.layout.activity_change_order_aty);

        ImageView topButton = (ImageView) findViewById(R.id.topButton);
        topButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initOkhttp();
    }

    public void initOkhttp(){
        OkHttpUtils
                .post()
                .url(HttpUrl.url_pathOrder)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(ChangeOrderAty.this, "服务器似乎出问题了", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            try {
                                pathTags = getPath(URLDecoder.decode(response,"utf-8"));
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        selectPathAdapter = new ChangOrderAdapter(ChangeOrderAty.this,pathTags);
                        setListAdapter(selectPathAdapter);
                        selectPathAdapter.notifyDataSetChanged();
                    }
                });
    }
    public ArrayList getPath(String json) throws JSONException {

        Log.e("psn",json);

        JSONArray ja = new JSONArray(json);

        for(int j=0;j<ja.length();j++)
        {
            Node node = new Node();
            JSONObject jb = ja.getJSONObject(j);
            //pathID
            node.ph = jb.getString("id");
            node.tg = jb.getString("createTime");
            node.pathName = jb.getString("pathName");
            Log.e("psn",node.ph+"=="+node.tg);

            pathTags.add(node);
        }

        return pathTags;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(this,ChangeTagOrderAty.class);
        intent.putExtra("pathID",pathTags.get(position).ph);

        startActivity(intent);
        super.onListItemClick(l, v, position, id);
    }

}
