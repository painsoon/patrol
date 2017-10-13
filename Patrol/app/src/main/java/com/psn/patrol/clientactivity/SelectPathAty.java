package com.psn.patrol.clientactivity;

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
import com.psn.patrol.bean.Node;
import com.psn.patrol.db.SqlManager;
import com.psn.patrol.db.TeskSqliteOpenHelper;
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

public class SelectPathAty extends ListActivity {

    private SqlManager sqlmanager;
    private TeskSqliteOpenHelper sqlHelper;
    private ArrayList<Node> pathTags = new ArrayList<>() ; //路线任务
    private int count;

    private SelectPathAdapter selectPathAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 无标题
        setContentView(R.layout.activity_select_path_aty);

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
        Map<String,String> map =  Readprefer.getUserID(this);
        OkHttpUtils
                .post()
                .url(HttpUrl.url_pathTest)
                .addParams("userId",map.get("nameId"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(SelectPathAty.this, "服务器似乎出问题了", Toast.LENGTH_SHORT).show();
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
                        selectPathAdapter = new SelectPathAdapter(SelectPathAty.this,pathTags);
                        setListAdapter(selectPathAdapter);
                        selectPathAdapter.notifyDataSetChanged();
                    }
                });
    }

    /*
    public void initDataAsyncHttpClient() {

        String url = HttpUrl.url_pathTest;

        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();
        Map<String,String> map =  Readprefer.getUserID(this);
        params.put("userId",map.get("nameId"));

        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String s = new String(responseBody).trim();

                try {
                    pathTags = getPath(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                selectPathAdapter = new SelectPathAdapter(SelectPathAty.this,pathTags);
                setListAdapter(selectPathAdapter);
                selectPathAdapter.notifyDataSetChanged();

                //Toast.makeText(SelectPathAty.this, "密码错误！", Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(SelectPathAty.this, "服务器似乎出问题了", Toast.LENGTH_SHORT).show();
            }
        });
    }
    */
    public ArrayList getPath(String json) throws JSONException {

        long str = System.currentTimeMillis();

//        String json = "[{\"path\":\"00001\",\"tagGuy\":\"9527\",\"count\":\"6\",\"results\":" +
//                "        [{\"tagID\":\"9f307700581201\",\"tagName\":\"A1楼\",\"startTime\":\"16:00\",\"param\":\"大门锁是否正常，墙体是否被划，电梯是否正常\"}" +
//                "        ,{\"tagID\":\"9f307300711201\",\"tagName\":\"A2楼\",\"startTime\":\"16:10\",\"param\":\"大门锁是否正常，墙体是否被划，电梯是否正常\"}" +
//                "        ,{\"tagID\":\"9f30d9003d1201\",\"tagName\":\"A3楼\",\"startTime\":\"16:20\",\"param\":\"大门锁是否正常，墙体是否被划，电梯是否正常\"}" +
//                "        ,{\"tagID\":\"9f30e100291201\",\"tagName\":\"A4楼\",\"startTime\":\"16:30\",\"param\":\"大门锁是否正常，墙体是否被划，电梯是否正常\"}" +
//                "        ,{\"tagID\":\"9f402600ad1201\",\"tagName\":\"A5楼\",\"startTime\":\"16:40\",\"param\":\"大门锁是否正常，墙体是否被划，电梯是否正常\"}" +
//                "        ,{\"tagID\":\"9f30ed000c1201\",\"tagName\":\"停车场\",\"startTime\":\"17:00\",\"param\":\"摄像头是否正常，车棚是否异常，插座是否正常\"}" +
//                "        ]}" +
//                "        ,{\"path\":\"00002\",\"tagGuy\":\"9527\",\"count\":\"6\",\"results\":" +
//                "        [{\"tagID\":\"9f307700581201\",\"tagName\":\"A1楼\",\"startTime\":\"16:00\",\"param\":\"大门锁是否正常，墙体是否被划，电梯是否正常\"}" +
//                "        ,{\"tagID\":\"9f30e100291201\",\"tagName\":\"A4楼\",\"startTime\":\"16:10\",\"param\":\"大门锁是否正常，墙体是否被划，电梯是否正常\"}" +
//                "        ,{\"tagID\":\"9f30d9003d1201\",\"tagName\":\"A3楼\",\"startTime\":\"16:20\",\"param\":\"大门锁是否正常，墙体是否被划，电梯是否正常\"}" +
//                "        ,{\"tagID\":\"9f402600ad1201\",\"tagName\":\"A5楼\",\"startTime\":\"16:30\",\"param\":\"大门锁是否正常，墙体是否被划，电梯是否正常\"}" +
//                "        ,{\"tagID\":\"9f307300711201\",\"tagName\":\"A2楼\",\"startTime\":\"16:40\",\"param\":\"大门锁是否正常，墙体是否被划，电梯是否正常\"}" +
//                "        ,{\"tagID\":\"9f30ed000c1201\",\"tagName\":\"停车场\",\"startTime\":\"17:00\",\"param\":\"摄像头是否正常，车棚是否异常，插座是否正常\"}" +
//                "        ]" +
//                "        }]";

        Log.e("psn",json);
        sqlHelper = new TeskSqliteOpenHelper(this,"tesk.db",null,1);
        sqlmanager = new SqlManager(sqlHelper);
        sqlmanager.Delete("delete from testpath");
        String sql = "insert into testpath (pathID,tagGuy,tagID,tagName,startTime,param) values (?,?,?,?,?,?)";

        JSONArray ja = new JSONArray(json);

        for(int j=0;j<ja.length();j++)
        {
            Node node = new Node();
            JSONObject jb = ja.getJSONObject(j);
            //pathID
            String ph = jb.getString("path");
            //tagGuy
            String tg = jb.getString("tagGuy");
            node.ph = ph;
            node.tg = tg;
            pathTags.add(node);
            Log.e("psn",pathTags.get(j).ph+pathTags.get(j).tg);
            count  = jb.getInt("count");
            JSONArray jsonArray = jb.getJSONArray("results");

        //    Log.e("psn",jsonArray.length()+"=======");

            String value[] = new String[count];
            for (int i = 0; i < jsonArray.length(); i++) {
                int t=0;
                value[t++] = ph;
                value[t++] = tg;

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                value[t++] = (jsonObject.getString("tagID"));
                value[t++] = (jsonObject.getString("tagName"));
                value[t++] = (jsonObject.getString("startTime"));
                value[t++] = (jsonObject.getString("params"));
                sqlmanager.Insert(sql,value);
            }

        }

        return pathTags;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(this,PatrolSituationAty.class);
        intent.putExtra("pathID",pathTags.get(position).ph);

        startActivity(intent);
        super.onListItemClick(l, v, position, id);
    }
}
