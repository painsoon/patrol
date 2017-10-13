package com.psn.patrol;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shinian pan on 2016/6/10.
 */
public class Readprefer {
    // 保存号码和登录密码,到data.xml文件中
    public static boolean saveUserInfo(Context context,
             String number,String password ){

        SharedPreferences sp = context.getSharedPreferences("data",
                Context.MODE_PRIVATE);
        Editor edit = sp.edit();
        edit.putString("userName", number);
        edit.putString("pwd", password);
        edit.commit();
        return true;
    }
    // 从data.xml文件中获取存储的号码和密码
    public static Map<String,String> getUserInfo(Context context){
        SharedPreferences sp = context.getSharedPreferences("data",
                Context.MODE_PRIVATE);
        String number = sp.getString("userName", null);
        String password = sp.getString("pwd", null);
        Map<String,String> userMap = new HashMap<>();
        userMap.put("number", number);
        userMap.put("password", password);
        return userMap;
    }
    // 保存用户ID,到data.xml文件中
    public static boolean saveUserID(Context context,
                                       String number ,String gsName){

        SharedPreferences sp = context.getSharedPreferences("data",
                Context.MODE_PRIVATE);
        Editor edit = sp.edit();
        edit.putString("nameId", number);
        edit.putString("customId",gsName);
        edit.commit();
        return true;
    }
    // 从data.xml文件中获取存储的userID
    public static Map<String,String> getUserID(Context context){
        SharedPreferences sp = context.getSharedPreferences("data",
                Context.MODE_PRIVATE);
        String number = sp.getString("nameId", null);
        String gsName =  sp.getString("customId",null);
        Map<String,String> userMap = new HashMap<>();
        userMap.put("nameId", number);
        userMap.put("customId",gsName);
        return userMap;
    }
}
