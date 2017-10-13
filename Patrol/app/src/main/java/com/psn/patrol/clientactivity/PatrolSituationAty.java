package com.psn.patrol.clientactivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.psn.patrol.R;
import com.psn.patrol.Readprefer;
import com.psn.patrol.adapter.GridAdapter;
import com.psn.patrol.bean.NfcTag;
import com.psn.patrol.bean.PathTest;
import com.psn.patrol.db.SqlManager;
import com.psn.patrol.db.TeskSqliteOpenHelper;
import com.psn.patrol.util.HttpUrl;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

import okhttp3.Call;

public class PatrolSituationAty extends Activity implements View.OnClickListener{
    // NFC parts
    private static NfcAdapter mAdapter;
    private static PendingIntent mPendingIntent;
    private NdefMessage mNdefPushMessage;

    private Button btExc;
    private Button btSubmit;

    private TextView texttagid;
    private TextView promt;
    private AlertDialog mDialog;

    private Button btStart;
    private GridView noscrollGridView;
    private GridAdapter gridAdapter;
    private int count=0;
    private int position=0;
    private Handler handler;

    private ImageView topButton;

    private SqlManager sqlmanager;
    private TeskSqliteOpenHelper sqlHelper;
    private ArrayList<NfcTag> nfcTags ; //缓存ok的id，等待上传服务器
    private ArrayList<PathTest> pathTags ; //路线任务


    private boolean flag = false; //判断是否重复扫描，
    private String temptagId,pathID,isRight = "1"; //暂存读到的tagid，

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 无标题
        setContentView(R.layout.activity_patrol_situation);

        Intent intent = getIntent();
        pathID = intent.getStringExtra("pathID");

        getPath();
        init();

    }

    public ArrayList getPath() {
        pathTags = new ArrayList<>();
        sqlHelper = new TeskSqliteOpenHelper(this,"tesk.db",null,1);
        sqlmanager = new SqlManager(sqlHelper);
        String sql = "select * from testpath where pathID = ? ";
        Log.e("psn","==="+pathID);
        String[] value  = new String[]{pathID};
        Cursor cursor = sqlmanager.Select(sql,value);
        while(cursor.moveToNext()){
            PathTest pathTest = new PathTest();
            pathTest.setPathID(cursor.getString(cursor.getColumnIndex("pathID")));
            pathTest.setTagID(cursor.getString(cursor.getColumnIndex("tagID")));
            pathTest.setTagGuy(cursor.getString(cursor.getColumnIndex("tagGuy")));
            pathTest.setTagName(cursor.getString(cursor.getColumnIndex("tagName")));
            pathTest.setStartTime(cursor.getString(cursor.getColumnIndex("startTime")));
            pathTest.setParam(cursor.getString(cursor.getColumnIndex("param")));

            pathTags.add(pathTest);
        }
        cursor.close();
        return pathTags;
    }

    public void init(){
        topButton = (ImageView) findViewById(R.id.topButton);
        topButton.setOnClickListener(this);
        count = pathTags.size();
        btStart = (Button) findViewById(R.id.bt_start);
        btStart.setOnClickListener(this);
        noscrollGridView = (GridView) findViewById(R.id.noScrollgridview);

        Log.e("psn",pathTags.size()+"======"+pathTags.get(0).getTagName());
        gridAdapter = new GridAdapter(this,pathTags);
        noscrollGridView.setAdapter(gridAdapter);
        gridAdapter.notifyDataSetChanged();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 1:
                        gridAdapter.notifyDataSetChanged();
                        break;
                }
                super.handleMessage(msg);
            }
        };

        texttagid = (TextView) findViewById(R.id.tagidtext);
        btExc = (Button) findViewById(R.id.bt_exception);
       // btSubmit = (Button) findViewById(R.id.bt_submitexp);
       // btSubmit.setOnClickListener(this);
        btExc.setOnClickListener(this);

        mDialog = new AlertDialog.Builder(this).setNeutralButton("Ok", null)
                .create();
        mAdapter = NfcAdapter.getDefaultAdapter(this);

        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        mNdefPushMessage = new NdefMessage(new NdefRecord[]{newTextRecord("",
                Locale.ENGLISH, true)});

        try {
            rfid_scanresult(getIntent());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdapter == null) {

            showMessage(R.string.error, R.string.no_nfc);
            return;
        }
        if (!mAdapter.isEnabled()) {
            showWirelessSettingsDialog();
            //promt.setText("请在系统设置中先启用NFC功能！");
            return;
        }

        if (mAdapter != null) {
            //隐式启动
            mAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
            mAdapter.enableForegroundNdefPush(this, mNdefPushMessage);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAdapter != null) {
            //隐式启动
            mAdapter.disableForegroundDispatch(this);
            mAdapter.disableForegroundNdefPush(this);
        }
    }

    private NdefRecord newTextRecord(String text, Locale locale,
                                     boolean encodeInUtf8) {
        byte[] langBytes = locale.getLanguage().getBytes(
                Charset.forName("US-ASCII"));

        Charset utfEncoding = encodeInUtf8 ? Charset.forName("UTF-8") : Charset
                .forName("UTF-16");
        byte[] textBytes = text.getBytes(utfEncoding);

        int utfBit = encodeInUtf8 ? 0 : (1 << 7);
        char status = (char) (utfBit + langBytes.length);

        byte[] data = new byte[1 + langBytes.length + textBytes.length];
        data[0] = (byte) status;
        System.arraycopy(langBytes, 0, data, 1, langBytes.length);
        System.arraycopy(textBytes, 0, data, 1 + langBytes.length,
                textBytes.length);

        return new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT,
                new byte[0], data);
    }

    private void showWirelessSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.nfc_disabled);
        builder.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(
                                Settings.ACTION_WIRELESS_SETTINGS);
                        startActivity(intent);
                    }
                });
        builder.setNegativeButton(android.R.string.cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
        builder.create().show();
        return;
    }

    private void showMessage(int title, int message) {
        mDialog.setTitle(title);
        mDialog.setMessage(getText(message));
        mDialog.show();
    }


    public void rfid_scanresult(Intent intent) throws IOException {

        String action = intent.getAction();
        if (flag&&(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TAG_DISCOVERED.equals(action))) {

            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            if(tag!=null){
                byte[] tagid = tag.getId();
                temptagId = bytesToHexString(tagid);

                if (temptagId.equals(pathTags.get(position).getTagID())) {
                    if (pathTags.get(position).getRight().equals("-1"))
                        pathTags.get(position).setRight("1");
                    gridAdapter.setChangId(++position);
                    Message message = new Message();
                    message.what = 1;
                    if (position < count)
                        texttagid.setText("TagID=:" + temptagId + "\n" + pathTags.get(position).getParam());
                    else {
                        flag = false;
                        btStart.setText("巡检完成！");
                        btStart.setEnabled(false);
                        new AlertDialog.Builder(this)
                                .setMessage("本次巡检任务已完成，请上传结果！")
                                .setPositiveButton("确定",
                                        new DialogInterface.OnClickListener(){
                                            public void onClick(DialogInterface dialoginterface, int i){
                                                //按钮事件

                                                submitOkhttp();

                                            }
                                        })
                                .setCancelable(false)
                                .show();
                    }
                    handler.sendMessage(message);
                }else{

                    new AlertDialog.Builder(this)
                            .setTitle("警告")
                            .setMessage("您没有检查该店的权限\n请先到"+pathTags.get(position).getTagName()+"点巡检")
                            .setPositiveButton("确定",
                                    new DialogInterface.OnClickListener(){
                                        public void onClick(DialogInterface dialoginterface, int i){
                                            dialoginterface.dismiss();
                                        }
                                    })
                            .show();

                }

                Log.e("psn",temptagId);
            }
        }
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    @Override
    public void onNewIntent(Intent intent) {
        setIntent(intent);
        try {
            rfid_scanresult(intent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_start:
                flag = true;
                gridAdapter.setChangId(position);
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
                texttagid.setText("TagID=:" + temptagId+"\n"+pathTags.get(position).getParam());
                break;
            case R.id.bt_exception:
                isRight = "0";
                Intent intent = new Intent(PatrolSituationAty.this,ExceptionAty.class);
                pathTags.get(position).setRight("0");
                intent.putExtra("tagID",pathTags.get(position).getTagID());
                intent.putExtra("pathID",pathID);
                startActivity(intent);
                break;
//            case R.id.bt_submitexp:
//                submitOkhttp();
//                break;
            case R.id.topButton:
                finish();
                break;

        }
    }

    public String getResults(){
        StringBuilder rjson = new StringBuilder("[");
        for(int i=0;i<pathTags.size();i++){
            rjson.append("{");
            rjson.append("pathID:"+pathTags.get(i).getPathID()+",");
            rjson.append("tagID:"+pathTags.get(i).getTagID()+",");
            rjson.append("tagGuy:"+pathTags.get(i).getTagGuy()+",");
            rjson.append("isRight:"+pathTags.get(i).getRight());
            if(i==pathTags.size()-1){
                rjson.append("}");
            }else{
                rjson.append("},");
            }
        }
        rjson.append("]");
        return rjson.toString();
    }

    /*
    public void submitAsyncHttpClient() {

        String result = getResults();

        String url = HttpUrl.url_uploadPatrolInfo;

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("result",result);
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String s = new String(responseBody);
                if (!s.equals("")) {
                    Toast.makeText(PatrolSituationAty.this, "已完成本次巡检任务", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(PatrolSituationAty.this, "服务器似乎出问题了", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(PatrolSituationAty.this, "服务器似乎出问题了", Toast.LENGTH_SHORT).show();
            }
        });
    }
    */
    public void submitOkhttp(){
     //   String result = getResults();
        Map<String,String> map =  Readprefer.getUserID(this);
        OkHttpUtils
                .post()
                .url(HttpUrl.url_submitResult)
                .addParams("pathID",pathID)
                .addParams("isRight",isRight)
                .addParams("tagGuy",map.get("nameId"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(PatrolSituationAty.this, "服务器似乎出问题了", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        new AlertDialog.Builder(PatrolSituationAty.this)
                                .setTitle("提示")
                                .setMessage("巡检结果已提交!")
                                .setPositiveButton("确定",
                                        new DialogInterface.OnClickListener(){
                                            public void onClick(DialogInterface dialoginterface, int i){
                                                finish();
                                                dialoginterface.dismiss();

                                            }
                                        })
                                .show();

                    }
                });
    }
}
