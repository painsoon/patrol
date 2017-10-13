package com.psn.patrol.clientactivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.psn.patrol.R;
import com.psn.patrol.api.WeatherApi;
import com.psn.patrol.bean.*;
public class Weather_AddcityActivity extends Activity {
    private List<City> cityList;
    private static final String TAG = "Weather_AddcityActivity";
    private City cityData;
    private String cityId;
    private SharedPreferences sp;
    private Editor edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_addcity);
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.query_city_list);
        GridView grid_city = (GridView) findViewById(R.id.grid_city_name);
        edit = sp.edit();
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestJsonCity();
            }
        }).start();
        JSONCity();

        String[] str=new String[cityList.size()];
        for (int i = 0; i < cityList.size(); i++) {
            str[i]=cityList.get(i).getCityname();
        }
        ArrayAdapter arrayAdapter =new ArrayAdapter(Weather_AddcityActivity.this,android.R.layout.simple_list_item_1,str);
        grid_city.setAdapter(arrayAdapter);
        grid_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv= (TextView) view;
                String cityname = tv.getText().toString();
                Toast.makeText(Weather_AddcityActivity.this, "position"+cityname, Toast.LENGTH_SHORT).show();
                for (int i = 0; i < cityList.size(); i++) {
                    if(cityList.get(i).getCityname()==cityname){
                        cityId = cityList.get(i).getCityId();
                    }
                }
                Intent intent=new Intent();
                intent.putExtra("cityname",cityname);
                intent.putExtra("cityId",cityId);
                edit.putString("cityname",cityname);
                edit.putString("cityId",cityId);
                edit.commit();
                setResult(1,intent);
                finish();
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Weather_AddcityActivity.this,android.R.layout.simple_list_item_1,str);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv= (TextView) view;
                String cityname = tv.getText().toString();
                Toast.makeText(Weather_AddcityActivity.this, "position"+cityname, Toast.LENGTH_SHORT).show();
                for (int i = 0; i < cityList.size(); i++) {
                    if(cityList.get(i).getCityname()==cityname){
                        cityId = cityList.get(i).getCityId();
                    }
                }
                Intent intent=new Intent();
                intent.putExtra("cityname",cityname);
                intent.putExtra("cityId",cityId);
                edit.putString("cityname",cityname);
                edit.putString("cityId",cityId);
                edit.commit();
                setResult(1,intent);
                finish();
            }
        });
    }
    private void RequestJsonCity(){
        String httpUrl = "http://apis.baidu.com/apistore/weatherservice/citylist";
        String httpArg = "cityname=%E6%9C%9D%E9%98%B3";
        String jsonResult = WeatherApi.request(httpUrl,httpArg);
        Log.i(TAG, "RequestJsonCity: "+jsonResult);
    }
    private void JSONCity() {
        InputStream inputStream=this.getClass().getClassLoader().getResourceAsStream("assets/" + "CityCode.Json");
        InputStreamReader streamReader=new InputStreamReader(inputStream);
        BufferedReader reader=new BufferedReader(streamReader);
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            while ((line=reader.readLine()) != null){
                stringBuilder.append(line);
            }
            reader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String data = stringBuilder.toString();
        setCityData(data);//将城市数据放入list链表
    }
    private void setCityData(String data) {
        //title.setText(data);
        JSONTokener jsontoken = new JSONTokener(data);
        try {
            JSONArray jsonArray= (JSONArray) jsontoken.nextValue();
            cityList=new ArrayList<City>();
            for(int i=0;i<jsonArray.length();i++){
                JSONArray cityArray = jsonArray.getJSONObject(i).getJSONArray("市");
                for(int j=0;j<cityArray.length();j++){
                    cityData=new City();
                    cityData.setCityname(cityArray.getJSONObject(j).getString("市名"));
                    cityData.setCityId(cityArray.getJSONObject(j).getString("编码"));
                    cityList.add(cityData);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK){
            Intent intent=new Intent();
            intent.putExtra("cityname","北京");
            intent.putExtra("cityId","101011000");
            setResult(2,intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
