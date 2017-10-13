package com.psn.patrol.serverfragment;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.psn.patrol.R;
import com.psn.patrol.bean.PathTest;
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

public class PatrolInfoAty extends ListActivity implements View.OnClickListener {

    private ArrayList<PathTest> pathTags = new ArrayList<>();

    private Button searchBydate;
    private ImageView topButton;
    private TextView chackDate, pathName;

    private PatrolInfoAdapter patrolInfoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_patrol_info_aty);

        init();

        okHttp();
    }

    public void init() {
        searchBydate = (Button) findViewById(R.id.searchBydate);
        chackDate = (TextView) findViewById(R.id.chackDate);
       // pathName = (TextView) findViewById(R.id.path_name);

        topButton = (ImageView) findViewById(R.id.topButton);
        topButton.setOnClickListener(this);
        searchBydate.setOnClickListener(this);
        chackDate.setOnClickListener(this);
      //  pathName.setOnClickListener(this);

    }

    public ArrayList<PathTest> getData(String json) throws JSONException {
//        StringBuilder rjson = new StringBuilder("[");
//        for(int i=0;i<pathTags.size();i++){
//            rjson.append("{");
//            rjson.append("pathID:"+pathTags.get(i).getPathID()+",");
//            rjson.append("tagID:"+pathTags.get(i).getTagID()+",");
//            rjson.append("tagGuy:"+pathTags.get(i).getTagGuy()+",");
//            rjson.append("isRight:"+pathTags.get(i).getRight());
//            if(i==pathTags.size()-1){
//                rjson.append("}");
//            }else{
//                rjson.append("},");
//            }
//        }
//        rjson.append("]");
//        return rjson.toString();


//        String json = "[{\"path\":\"00001\",\"tagGuy\":\"9527\",\"results\":" +
//                "        [{\"tagID\":\"9f307700581201\",\"tagName\":\"A1楼\",\"isRight\":\"1\"}" +
//                "        ,{\"tagID\":\"9f307300711201\",\"tagName\":\"A2楼\",\"isRight\":\"1\"}" +
//                "        ,{\"tagID\":\"9f30d9003d1201\",\"tagName\":\"A3楼\",\"isRight\":\"1\"}" +
//                "        ,{\"tagID\":\"9f30e100291201\",\"tagName\":\"A4楼\",\"isRight\":\"0\"}" +
//                "        ,{\"tagID\":\"9f402600ad1201\",\"tagName\":\"A5楼\",\"isRight\":\"0\"}" +
//                "        ,{\"tagID\":\"9f30ed000c1201\",\"tagName\":\"停车场\",\"isRight\":\"0\"}" +
//                "        ]" +
//                       "}" +
//                     "]" ;
        //Log.e("psn", "==========" + json);

        JSONArray ja = new JSONArray(json);
        for (int j = 0; j < ja.length(); j++) {

            JSONObject jb = ja.getJSONObject(j);

            PathTest pt = new PathTest();
            pt.setPathID(jb.getString("pathId"));
            pt.setTagGuy(jb.getString("userId"));
            pt.setRight(jb.getString("isRight").trim());
            pathTags.add(pt);

        }
        return pathTags;
    }

    public void okHttp() {
        OkHttpUtils
                .post()
                .url(HttpUrl.url_PathInfo)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(PatrolInfoAty.this, "服务器似乎出问题了", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        try {
                            pathTags = getData(URLDecoder.decode(response, "utf-8"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        patrolInfoAdapter = new PatrolInfoAdapter(PatrolInfoAty.this, pathTags);
                        setListAdapter(patrolInfoAdapter);
                        patrolInfoAdapter.notifyDataSetChanged();

                    }

                });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.searchBydate:

                break;
//            case R.id.path_name:
//
//                break;
            case R.id.chackDate:

                break;

            case R.id.topButton:
                finish();
                break;
        }

    }
}
