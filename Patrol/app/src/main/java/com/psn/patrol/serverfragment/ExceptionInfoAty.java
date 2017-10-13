package com.psn.patrol.serverfragment;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.psn.patrol.R;
import com.psn.patrol.bean.ExeceptionInfo;
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

public class ExceptionInfoAty extends ListActivity{

    private ArrayList<ExeceptionInfo> questions ;
    private ExceptionInfoAdapter exceptionInfoAdapter;

    private ImageView topButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_exception_info_aty);

        topButton = (ImageView) findViewById(R.id.topButton);
        topButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        okHttp();
    }

    public ArrayList getNews(String json) throws JSONException {

        questions = new ArrayList<>();

//        String json = "[{\"path\":\"00001\",\"tagGuy\":\"9527\",\"exception\":\"神马点：大门锁坏了\",\"count\":\"0\",\"photo\":\"\",\"time\":\"18:00\"}" +
//                "        ,{\"path\":\"00002\",\"tagGuy\":\"9527\",\"exception\":\"神马点：大门锁坏了，停车场:插座被破坏\",\"count\":\"0\",\"photo\":\"\",\"time\":\"18:00\"}" +
//                "        ,{\"path\":\"00002\",\"tagGuy\":\"9527\",\"exception\":\"神马点：大门锁坏了，停车场插座被破坏\",\"count\":\"0\",\"photo\":\"\",\"time\":\"18:00\"}"+
//                "       ]";

      //  Log.e("psn",json);
        JSONArray jsonArray = new JSONArray(json);

        for (int i = 0; i < jsonArray.length(); i++) {
            ExeceptionInfo question = new ExeceptionInfo();
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            question.setPathId((String) jsonObject.get("pathId"));
            question.setCheckPoint((String) jsonObject.get("checkPoint"));
            question.setCreateTime(jsonObject.get("createTime").toString());
            question.setUserId(jsonObject.getString("userId"));
            question.setAbnormalText(jsonObject.getString("abnormalText"));

        //    int count = Integer.parseInt(jsonObject.getString("count"));

            JSONArray ja =  jsonObject.getJSONArray("abnormalImage");

            String[] photo = new String[ ja.length()];
            for (int j = 0; j < ja.length(); j++) {
                photo[j] = ja.get(j).toString();
            }
            question.setAbnormalImage(photo);

            questions.add(question);
         //   Log.e("psn",questions.toString());
        }
        return questions;
    }

    public void okHttp(){
        OkHttpUtils.post()
                .url(HttpUrl.url_ExceptionInfo)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(ExceptionInfoAty.this, "服务器似乎出问题了", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            try {
                                getNews(URLDecoder.decode(response,"utf-8"));
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            exceptionInfoAdapter = new ExceptionInfoAdapter(ExceptionInfoAty.this,questions);
                            setListAdapter(exceptionInfoAdapter);
                            exceptionInfoAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

//    @Override
//    protected void onListItemClick(ListView l, View v, int position, long id) {
//
//        Intent intent = new Intent(this, MyInfoAnswerAty.class);
//
//        intent.putExtra("position", position);
//
//        super.onListItemClick(l, v, position, id);
//    }
}
