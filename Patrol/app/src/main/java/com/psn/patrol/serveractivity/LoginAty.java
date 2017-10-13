package com.psn.patrol.serveractivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.psn.patrol.R;
import com.psn.patrol.Readprefer;
import com.psn.patrol.clientactivity.ClientAty;
import com.psn.patrol.util.HttpUrl;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class LoginAty extends Activity implements View.OnClickListener{
    private EditText login_username;
    private EditText login_gs;
    private EditText login_password;
    private Button login_button;

    private String username;
    private String password;
    private String gs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 无标题
        setContentView(R.layout.activity_login_aty);
        init();

    }

    public void init() {
        login_username = (EditText) findViewById(R.id.login_username);
        login_password = (EditText) findViewById(R.id.login_password);
        login_gs  = (EditText) findViewById(R.id.gs);
        login_button = (Button) findViewById(R.id.login_button);
        login_button.setOnClickListener(this);

        login_username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (!hasFocus) {
                    username = login_username.getText().toString().trim();
                    if (username.length() < 4) {
                        Toast.makeText(LoginAty.this, "用户名不能小于4个字符", Toast.LENGTH_SHORT);
                    }
                }
            }
        });
        login_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (!hasFocus) {
                    password = login_password.getText().toString().trim();
                    if (password.length() < 4) {
                        Toast.makeText(LoginAty.this, "密码不能小于4个字符", Toast.LENGTH_SHORT);
                    }
                }
            }
        });
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

    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
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

    private boolean checkEdit(){
        gs = login_gs.getText().toString().trim();
        username = login_username.getText().toString().trim();
        password = login_password.getText().toString().trim();
        if(username.equals("")){
            Toast.makeText(LoginAty.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }else if(password.equals("")){
            Toast.makeText(LoginAty.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }else if(gs.equals("")) {
            Toast.makeText(LoginAty.this, "公司不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.login_button:
                //login();
                if(checkEdit()){
                   // Log.e("psn",gs+"==="+username+"========="+ password);
                    okHttp();
                }
                break;
        }
    }

//    private void login() {
//
//        if(1==1){
//            Intent intent = new Intent(this,ClientAty.class);
//            startActivity(intent);
//        }else{
//            Intent intent = new Intent(this,OperationAdminAty.class);
//            startActivity(intent);
//        }
//    }

    public void okHttp(){

        OkHttpUtils
                .post()
                .url(HttpUrl.url_login)
                .addParams("customName",gs)
                .addParams("username",username)
                .addParams("password",password)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(LoginAty.this, "服务器似乎出问题了", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("psn",response+"=========");
                        String ss[] = response.split(";");
                        if(ss.length>1) {
                            Log.e("psn",ss[1]+"========");
                            Readprefer.saveUserID(LoginAty.this, ss[1],ss[2]);
                        }
                        if ("3".equals(ss[0].trim()) ){
                            Intent intent = new Intent(LoginAty.this,ClientAty.class);
                            startActivity(intent);
                        } else if("1".equals(ss[0].trim())){
                            Intent intent = new Intent(LoginAty.this,OperationAdminAty.class);
                            startActivity(intent);
                        }else if("5".equals(ss[0].trim())){
                            Toast.makeText(LoginAty.this, "密码错误！", Toast.LENGTH_SHORT).show();
                        }else if("6".equals(ss[0].trim())){
                            Toast.makeText(LoginAty.this, "用户名不存在！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}


