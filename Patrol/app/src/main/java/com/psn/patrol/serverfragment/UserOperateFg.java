package com.psn.patrol.serverfragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.psn.patrol.R;
import com.psn.patrol.Readprefer;
import com.psn.patrol.bean.User;
import com.psn.patrol.util.HttpUrl;
import com.zhy.http.okhttp.OkHttpUtils;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class UserOperateFg extends Fragment {
    private RecyclerView rv;
    private List<User> list = new ArrayList<>();
    private UserRecycleViewAdapter adapter;
    private ImageView addUser;
    private Map<String,String> map ;
    public UserOperateFg() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_user_operate_fg, container, false);
        rv = (RecyclerView) view.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
        addUser = (ImageView) view.findViewById(R.id.add_user);
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("增加巡逻员");
                View view = LayoutInflater.from(getContext()).inflate(R.layout.adduserdialog,null);
                final EditText name = (EditText) view.findViewById(R.id.et_name);
                final EditText age = (EditText) view.findViewById(R.id.et_age);
                final EditText sex = (EditText) view.findViewById(R.id.et_sex);
                final EditText phone = (EditText) view.findViewById(R.id.et_phone);
                final EditText email = (EditText) view.findViewById(R.id.et_email);

                builder.setView(view);

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        User user = new User();
                        user.setName(name.getText().toString().trim());
                        user.setAge(age.getText().toString().trim());
                        user.setSex(sex.getText().toString().trim());
                        user.setPhone(phone.getText().toString().trim());
                        user.setEmail(email.getText().toString().trim());
                        user.setCustomId(map.get("customId").toString().trim());
                        list.add(user);
                        addokhttp(user);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

        initOkhttp();
        return view;
    }

    public void addokhttp(User user){
        String userStr = new Gson().toJson(user).toString();
        Log.e("psn",userStr);
        OkHttpUtils
                .post()
                .url(HttpUrl.url_add_bya)
                .addParams("user",userStr)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getActivity(), "服务器似乎出问题了", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Toast.makeText(getActivity(), "添加成功！", Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();
                    }
                });
    }


    public void initOkhttp(){

        map = Readprefer.getUserID(getContext());
        String custom = map.get("customId");
        Log.e("psn",custom);

        OkHttpUtils
                .post()
                .url(HttpUrl.url_userlist)
                .addParams("customId",custom)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getActivity(), "服务器似乎出问题了", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        try {
                            list = getPath(URLDecoder.decode(response,"utf-8"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        adapter = new UserRecycleViewAdapter(list);
                        rv.setAdapter(adapter);
                        ItemTouchHelper.Callback callback = new RecycleItemTouchHelper(adapter);
                        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
                        itemTouchHelper.attachToRecyclerView(rv);
                    }
                });
    }

    private List<User>getPath(String json) throws JSONException{

        Log.e("psn",json);
        JSONArray jsonArray = new JSONArray(json);

        list.clear();
        for(int i=0;i<jsonArray.length();i++){
            JSONObject jb = jsonArray.getJSONObject(i);
            User user = new User();
            user.setName(jb.getString("loginName"));
            user.setId(jb.getString("id"));
            list.add(user);
        }
        return list;
    }

}
