package com.psn.patrol.clientactivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.psn.patrol.R;
import com.psn.patrol.Readprefer;
import com.psn.patrol.bean.ExeceptionInfo;
import com.psn.patrol.clientactivity.photoactivity.PhotoActivity;
import com.psn.patrol.clientactivity.photoactivity.TestPicActivity;
import com.psn.patrol.util.Bimp;
import com.psn.patrol.util.FileUtils;
import com.psn.patrol.util.HttpUrl;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class ExceptionAty extends Activity {
    private GridView noScrollgridview;
    private GridAdapter adapter;

    private EditText pathID;
    private EditText etCheckPoint;
    private EditText etCheckGuy;
    private EditText etCheckException;
    private String etCheckPointStr;
    private String etCheckGuyStr;
    private String etCheckExceptionStr;
    private String pathStr, tagID, pathId, tagGuy;
    private ImageView topButton;
    private int position;

    private List<String> list;

    private TextView activity_selectimg_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_exception_aty);

        init();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }

            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public void init() {
        Intent intent = getIntent();
        tagID = intent.getStringExtra("tagID");
        pathId = intent.getStringExtra("pathID");
        Map<String, String> map = Readprefer.getUserID(this);
        tagGuy = map.get("nameId");

        etCheckPoint = (EditText) findViewById(R.id.et_check_point);
        etCheckPoint.setText(tagID);
        etCheckGuy = (EditText) findViewById(R.id.et_check_guy);
        etCheckGuy.setText(tagGuy);
        etCheckException = (EditText) findViewById(R.id.et_exception);
        pathID = (EditText) findViewById(R.id.et_path_id);
        pathID.setText(pathId);

        list = new ArrayList<String>();
        list.add("");
        list.add("张三");
        list.add("李四");
        list.add("王二");
        list.add("麻子");

        ArrayAdapter<String> adapterItem=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,list);
        adapterItem.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        Spinner sp=(Spinner) findViewById(R.id.spinner1);
        sp.setAdapter(adapterItem);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // parent： 为控件Spinner view：显示文字的TextView position：下拉选项的位置从0开始
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              //  TextView tvResult = (TextView) findViewById(R.id.tvResult);
              //获取Spinner控件的适配器
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) parent.getAdapter();
              //  tvResult.setText(adapter.getItem(position));
            }
            //没有选中时的处理
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });



        topButton = (ImageView) findViewById(R.id.topButton);
        topButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new GridAdapter(ExceptionAty.this);
        adapter.update();
        noScrollgridview.setAdapter(adapter);
        noScrollgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == Bimp.bmp.size()) {
                    new PopupWindows(ExceptionAty.this, noScrollgridview);
                } else {
                    Intent intent = new Intent(ExceptionAty.this,
                            PhotoActivity.class);
                    intent.putExtra("ID", position);
                    startActivity(intent);
                }
            }
        });

        activity_selectimg_send = (TextView) findViewById(R.id.activity_selectimg_send);
        activity_selectimg_send.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                etCheckPointStr = etCheckPoint.getText().toString().trim();
                etCheckGuyStr = etCheckGuy.getText().toString().trim();
                etCheckExceptionStr = etCheckException.getText().toString().trim();
                pathStr = pathID.getText().toString().trim();

                List<String> list = new ArrayList<>();
                for (int i = 0; i < Bimp.drr.size(); i++) {
                    String Str = Bimp.drr.get(i).substring(
                            Bimp.drr.get(i).lastIndexOf("/") + 1,
                            Bimp.drr.get(i).lastIndexOf("."));
                    list.add(FileUtils.SDPATH+Str+".JPEG");
                }

                int len = Bimp.bmp.size();
                String []photo = new String[len];

                for(int i=0;i<len;i++){
                    try {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        //将bitmap一字节流输出 Bitmap.CompressFormat.PNG 压缩格式，100：压缩率，baos：字节流
                        Bimp.bmp.get(i).compress(Bitmap.CompressFormat.PNG, 50, baos);
                        baos.close();
                        byte[] buffer = baos.toByteArray();
                        System.out.println("图片的大小："+buffer.length);
                        //将图片的字节流数据加密成base64字符输出
                        photo[i] = Base64.encodeToString(buffer, 0, buffer.length,Base64.DEFAULT);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                FileUtils.deleteDir();

                ExeceptionInfo execeptionInfo = new ExeceptionInfo();
                execeptionInfo.setPathId(pathId);
                execeptionInfo.setCheckPoint(tagID);
                execeptionInfo.setAbnormalText(etCheckExceptionStr);
                execeptionInfo.setUserId(etCheckGuyStr);
                execeptionInfo.setAbnormalImage(photo);

                OkHttpUtils
                        .post()
                        .url(HttpUrl.url_exceptionInfo)
                        .addParams("result",new Gson().toJson(execeptionInfo).toString())
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Toast.makeText(ExceptionAty.this, "提交失败！", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Toast.makeText(ExceptionAty.this, "提交成功！", Toast.LENGTH_SHORT).show();
                                Bimp.reset();
                                finish();
                            }
                        });


                /*
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                for(int i=0;i<Bimp.bmp.size();i++){
                    try {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        //将bitmap一字节流输出 Bitmap.CompressFormat.PNG 压缩格式，100：压缩率，baos：字节流
                        Bimp.bmp.get(i).compress(Bitmap.CompressFormat.PNG, 100, baos);
                        baos.close();
                        byte[] buffer = baos.toByteArray();
                        System.out.println("图片的大小："+buffer.length);

                        //将图片的字节流数据加密成base64字符输出
                        String photo = Base64.encodeToString(buffer, 0, buffer.length,Base64.DEFAULT);
                        params.put("photo"+i, photo);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                params.put("count", Bimp.bmp.size());
                params.put("etCheckPoint",etCheckPointStr);
                params.put("etCheckGuy" ,etCheckGuyStr);
                params.put("etCheckException",etCheckExceptionStr);

                client.post(path, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Toast.makeText(ExceptionAty.this,"提交成功！",Toast.LENGTH_LONG).show();
                        finish();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(ExceptionAty.this,"提交失败！",Toast.LENGTH_LONG).show();
                    }
                });
                */

               // FileUtils.deleteDir();
            }
        });
    }


    @SuppressLint("HandlerLeak")
    public class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater; // 视图容器
        private int selectedPosition = -1;// 选中的位置
        private boolean shape;

        public boolean isShape() {
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public GridAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void update() {
            loading();
        }

        public int getCount() {
            return (Bimp.bmp.size() + 1);
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int arg0) {

            return 0;
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }

        /**
         * ListView Item设置
         */
        public View getView(int position, View convertView, ViewGroup parent) {
            final int coord = position;
            ViewHolder holder = null;
            if (convertView == null) {

                convertView = inflater.inflate(R.layout.item_published_grida,
                        parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView
                        .findViewById(R.id.item_grida_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == Bimp.bmp.size()) {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(
                        getResources(), R.drawable.icon_addpic_unfocused));
                if (position == 6) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                holder.image.setImageBitmap(Bimp.bmp.get(position));
            }

            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
        }

        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        adapter.notifyDataSetChanged();
                        break;
                }
                super.handleMessage(msg);
            }
        };

        public void loading() {
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        if (Bimp.max == Bimp.drr.size()) {
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                            break;
                        } else {
                            try {
                                String path = Bimp.drr.get(Bimp.max);
                                System.out.println(path);
                                Bitmap bm = Bimp.revitionImageSize(path);
                                Bimp.bmp.add(bm);
                                String newStr = path.substring(
                                        path.lastIndexOf("/") + 1,
                                        path.lastIndexOf("."));
                                FileUtils.saveBitmap(bm, "" + newStr);
                                Bimp.max += 1;
                                Message message = new Message();
                                message.what = 1;
                                handler.sendMessage(message);
                            } catch (IOException e) {

                                e.printStackTrace();
                            }
                        }
                    }
                }
            }).start();
        }
    }

    public String getString(String s) {
        String path = null;
        if (s == null)
            return "";
        for (int i = s.length() - 1; i > 0; i++) {
            s.charAt(i);
        }
        return path;
    }


    public class PopupWindows extends PopupWindow {

        public PopupWindows(Context mContext, View parent) {

            View view = View
                    .inflate(mContext, R.layout.item_popupwindows, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.fade_ins));
            LinearLayout ll_popup = (LinearLayout) view
                    .findViewById(R.id.ll_popup);
            ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.push_bottom_in_2));

            setWidth(ViewGroup.LayoutParams.FILL_PARENT);
            setHeight(ViewGroup.LayoutParams.FILL_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(true);
            setOutsideTouchable(true);
            setContentView(view);
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            update();

            Button bt1 = (Button) view
                    .findViewById(R.id.item_popupwindows_camera);
            Button bt2 = (Button) view
                    .findViewById(R.id.item_popupwindows_Photo);
            Button bt3 = (Button) view
                    .findViewById(R.id.item_popupwindows_cancel);
            bt1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    photo();
                    dismiss();
                }
            });
            bt2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(ExceptionAty.this,
                            TestPicActivity.class);
                    startActivity(intent);
                    dismiss();
                }
            });
            bt3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });

        }
    }

    public void photo() {

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            File dir = new File(Environment.getExternalStorageDirectory() + "/formats");
            if (!dir.exists()) {
                dir.mkdir();
            }
            File file = new File(dir, new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".jpg");
            path = file.getPath();
            startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(
                    MediaStore.EXTRA_OUTPUT, Uri.fromFile(file)), TAKE_PICTURE);
        } else {
            Log.e("psn", "sd missing");
        }
    }

    private static final int TAKE_PICTURE = 0x000000;
    private String path = "";

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {

            case TAKE_PICTURE:

                if (Bimp.drr.size() < 6 && resultCode == -1) {
                    Bimp.drr.add(path);
                }
                break;
        }
    }

    protected void onRestart() {
        adapter.update();
        super.onRestart();
    }
}
