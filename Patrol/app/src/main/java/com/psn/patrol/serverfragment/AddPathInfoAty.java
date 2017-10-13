package com.psn.patrol.serverfragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.psn.patrol.bean.Basespot;
import com.psn.patrol.bean.Path;
import com.psn.patrol.R;
import com.psn.patrol.bean.PathTest;
import com.psn.patrol.db.SqlManager;
import com.psn.patrol.db.TeskSqliteOpenHelper;
import com.psn.patrol.util.HttpUrl;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.MediaType;

public class AddPathInfoAty extends Activity implements View.OnClickListener{

    private EditText etPathId;
  //  private EditText etGuyId;
    private EditText etTimeId;

    private Button addTagBt;
    private Button okAddBt;
    private Path path;
    private GridView tagGridView;
    private AddTagAdapter addTagAdapter;

    private ImageView topButton;
    private SqlManager sqlmanager;
    private TeskSqliteOpenHelper sqlHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_path_info_aty);

        init();
    }
    public void init(){
        path = new Path();
        sqlHelper = new TeskSqliteOpenHelper(this,"tesk.db",null,1);
        sqlmanager = new SqlManager(sqlHelper);
        topButton = (ImageView) findViewById(R.id.topButton);
        topButton.setOnClickListener(this);

        addTagAdapter = new AddTagAdapter(this,taglist);
        etPathId = (EditText) findViewById(R.id.et_path_id);
       // etGuyId = (EditText) findViewById(R.id.et_guy_id);
        etTimeId = (EditText) findViewById(R.id.et_time_id);
        tagGridView = (GridView) findViewById(R.id.tag_noScrollgridview);
        tagGridView.setAdapter(addTagAdapter);

        etPathId.setOnClickListener(this);
      //  etGuyId.setOnClickListener(this);
        etTimeId.setOnClickListener(this);

        addTagBt = (Button) findViewById(R.id.add_tag_bt);
        okAddBt = (Button) findViewById(R.id.okadd_bt);
        addTagBt.setOnClickListener(this);
        okAddBt.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_tag_bt:

                strpathID = etPathId.getText().toString().trim();
              //  strguyid  = etGuyId.getText().toString().trim();
                strTimeID = etTimeId.getText().toString().trim();
                path.setPathName(strpathID);
                path.setPathStartTime(strTimeID);

                if(strpathID!=null&&strTimeID!=null){
                    final Basespot pathTest = new Basespot();
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddPathInfoAty.this);
                    builder.setTitle("增加检查点");
                    View view = LayoutInflater.from(AddPathInfoAty.this).inflate(R.layout.mydialog,null);
                    final EditText tagid = (EditText) view.findViewById(R.id.et_tag_id);
                    final EditText tagname = (EditText) view.findViewById(R.id.et_tag_name);
                    final EditText tagstime = (EditText) view.findViewById(R.id.et_tag_stime);
                    final EditText tagparam = (EditText) view.findViewById(R.id.et_tag_param);
                    builder.setView(view);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            strtagid = tagid.getText().toString().trim();
                            strtagname = tagname.getText().toString().trim();
                            strtagstime = tagstime.getText().toString().trim();
                            strtagparam = tagparam.getText().toString().trim();
                            pathTest.setTagId(strtagid);
                            pathTest.setTagName(strtagname);
                            pathTest.setStartTime(strtagstime);
                            pathTest.setParams(strtagparam);
                            taglist.add(pathTest);

                            addTagAdapter.notifyDataSetChanged();
                        }

                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }else {
                    Toast.makeText(AddPathInfoAty.this,"请先填写以上信息！",Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.okadd_bt:
                if(strpathID!=null&&strTimeID!=null){

//                    String sql = "insert into addpath (pathID,startTime,tagGuy) values (?,?,?)";
//                    sqlmanager.Insert(sql,new String[]{strpathID,strTimeID,strguyid});

                    path.setResults(taglist);
                    submitOkhttp();
                    Intent intent = getIntent();
                    intent.putExtra("results", strpathID+";"+strTimeID);
                    setResult(1, intent);
                    finish();


                }else {
                    Toast.makeText(AddPathInfoAty.this,"请先填写以上信息！",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.topButton:
                finish();
                break;
        }
    }


    /*
    public void submitAsyncHttpClient() {

        String result = getResults();
        String url = HttpUrl.url_addPathInfo;

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("result",result);
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String s = new String(responseBody);
                if (!s.equals("")) {
                    Toast.makeText(AddPathInfoAty.this, "一添加该条路径", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(AddPathInfoAty.this, "服务器似乎出问题了", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(AddPathInfoAty.this, "服务器似乎出问题了", Toast.LENGTH_SHORT).show();
            }
        });
    }

*/
    public void submitOkhttp(){
        String addJson =  new Gson().toJson(path).toString();
        OkHttpUtils
                .post()
                .url(HttpUrl.url_addPathInfo)
                .addParams("addPath",addJson)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("psn",e.toString()+"=="+e.getLocalizedMessage()+"===="+id);
                        Toast.makeText(AddPathInfoAty.this, "服务器似乎出问题了", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (!response.equals("")) {
                            Toast.makeText(AddPathInfoAty.this, "一添加该条路径", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Log.e("psn",response+"="+id);
                            Toast.makeText(AddPathInfoAty.this, "服务器似乎出问题了", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private String strtagid,strtagname,strtagstime,strtagparam,strpathID,strguyid,strTimeID;
    private ArrayList<Basespot> taglist = new ArrayList<>();
}
