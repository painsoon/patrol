package com.psn.patrol.clientactivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

import com.psn.patrol.R;
import com.psn.patrol.adapter.Weather_GridViewAdapter;
import com.psn.patrol.adapter.Weather_ListViewAdapter;
import com.psn.patrol.api.WeatherApi;
import com.psn.patrol.bean.*;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Response;

public class WeatherActivity extends Activity implements OnRefreshListener {
    private static final String TAG = "WeatherActivity";
    private static SwipeRefreshLayout mSwipeLayout;
    private String path;
    private TextView weather_temp;
    private static int i=1;
    private GridView grid_weather;
    private ArrayList<Weather_grid_Item> weather_list;
    private ListView list_more;
    private ArrayList<Weather_list_Item> weather_list_items;
    private TextView weather_wse;;
    private TextView weather_updatetime;
    private TextView weather_wd;
    private TextView weather_pm;
    private TextView weather_type;
    private TextView weather_air;
    private TextView weather_city;
    private TextView weather_time;
    private TextView weather_lowtemp;
    private TextView weather_hightemp;
    private String cityname;
    private String cityId, jsonResult;
    private SharedPreferences sp;
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_weather);
        initView();
        new Thread(runnable).start();
    }

    private void initView() {
       // cityname=sp.getString("cityname","北京");
       // cityId=sp.getString("cityId","101010100");
        grid_weather = (GridView) findViewById(R.id.weather_center_grid);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.id_swipe_ly);
        mSwipeLayout.setOnRefreshListener(WeatherActivity.this);
//        mSwipeLayout.setColorScheme();
        list_more = (ListView) findViewById(R.id.list_more);
        weather_wse = (TextView) findViewById(R.id.weather_wse);//风度
        weather_temp = (TextView) findViewById(R.id.weather_temp);//温度
        weather_type = (TextView) findViewById(R.id.weather_type);//小雨
        weather_pm = (TextView) findViewById(R.id.weather_pm);//湿度
        weather_air = (TextView) findViewById(R.id.weather_air);//空气优
        weather_wd = (TextView) findViewById(R.id.weather_wd);//东南风
        weather_updatetime = (TextView) findViewById(R.id.weather_updatetime);//time
        weather_city = (TextView) findViewById(R.id.weather_city);
        weather_time = (TextView) findViewById(R.id.weather_time);
        weather_lowtemp = (TextView) findViewById(R.id.weather_lowtemp);
        weather_hightemp = (TextView) findViewById(R.id.weather_hightemp);
        ImageView weather_add_city = (ImageView) findViewById(R.id.weather_add_city);
        weather_add_city.setOnClickListener(new MyOnclick());
    }
    public  class MyOnclick implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(WeatherActivity.this,Weather_AddcityActivity.class);
            startActivityForResult(intent,1);
        }
    }
    private void setListView(){
        if(weather_list_items!=null) {
            Weather_ListViewAdapter weather_listViewAdapter = new Weather_ListViewAdapter(getApplicationContext(), weather_list_items);
            list_more.setAdapter(weather_listViewAdapter);
            ListAdapter adapter = list_more.getAdapter();
            int totalHeight = 0;
            if (list_more == null) {
                return;
            }
            int len = adapter.getCount();
            for (int i = 0; i < len; i++) {
                View listviewItem = adapter.getView(i, null, list_more);
                listviewItem.measure(0, 0);
                totalHeight += listviewItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams layoutParams = list_more.getLayoutParams();
            layoutParams.height = totalHeight + (list_more.getDividerHeight() * (adapter.getCount() - 1));
            list_more.setLayoutParams(layoutParams);
        }else {
            Toast.makeText(WeatherActivity.this, "数据获取失败", Toast.LENGTH_LONG).show();
            Log.i(TAG, "setListView: "+"数据获取失败");
        }
    }
    private void setGridView() {
        int size = weather_list.size();
        int length =100;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int gridViewWidth =(int)(size*(length+4)*density);
        int itemWidth =(int)(length*density);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridViewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        grid_weather.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        grid_weather.setColumnWidth(itemWidth); // 设置列表项宽
        grid_weather.setHorizontalSpacing(5); // 设置列表项水平间距
        grid_weather.setStretchMode(GridView.NO_STRETCH);
        grid_weather.setNumColumns(size); // 设置列数量=列表集合数

        Weather_GridViewAdapter adapter = new Weather_GridViewAdapter(getApplicationContext(),
                weather_list);
        grid_weather.setAdapter(adapter);
    }
    private void setGridViewDate(){
        weather_list = new ArrayList<>();
        Weather_grid_Item weather_grid_item =new Weather_grid_Item();
        weather_grid_item.setTime("9:00");
        weather_grid_item.setWeather(BitmapFactory.decodeResource(getResources(),R.drawable.weather));
        weather_grid_item.setTemp("27");
        weather_grid_item.setState("优");
        weather_list.add(weather_grid_item);
        weather_grid_item =new Weather_grid_Item();
        weather_grid_item.setTime("10:00");
        weather_grid_item.setWeather(BitmapFactory.decodeResource(getResources(),R.drawable.weather));
        weather_grid_item.setTemp("27");
        weather_grid_item.setState("优");
        weather_grid_item =new Weather_grid_Item();
        weather_list.add(weather_grid_item);
        weather_grid_item.setTime("11:00");
        weather_grid_item.setWeather(BitmapFactory.decodeResource(getResources(),R.drawable.weather));
        weather_grid_item.setTemp("27");
        weather_grid_item.setState("优");
        weather_grid_item =new Weather_grid_Item();
        weather_list.add(weather_grid_item);
        weather_grid_item.setTime("12:00");
        weather_grid_item.setWeather(BitmapFactory.decodeResource(getResources(),R.drawable.weather));
        weather_grid_item.setTemp("27");
        weather_grid_item.setState("优");
        weather_grid_item =new Weather_grid_Item();
        weather_list.add(weather_grid_item);
        weather_grid_item.setTime("13:00");
        weather_grid_item.setWeather(BitmapFactory.decodeResource(getResources(),R.drawable.weather));
        weather_grid_item.setTemp("27");
        weather_grid_item.setState("优");
        weather_list.add(weather_grid_item);
    }

    @Override
    public void onRefresh() {
        // TODO Auto-generated method stub
        new Thread(runnable).start();
        Log.i(TAG, "onRefresh: "+"刷新中");
        handler.sendEmptyMessageDelayed(2, 2000);
    }
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Log.i(TAG, "后台获取数据中。。。");
            WeatherApi weatherApi = new WeatherApi();
            String httpUrl = "https://api.seniverse.com/v3/weather/daily.json?key=jamuv6ub7wkj9sye";
            String httpArg = "&location="+"beijing"+"&language=zh-Hans&unit=c&start=0&days=5";
            OkHttpUtils
                    .get()
                    .url(httpUrl+httpArg)
                    .build()
                    .execute(new StringCallback() {
                                 @Override
                                 public void onError(Call call, Exception e, int id) {
                                     Toast.makeText(WeatherActivity.this, "服务器似乎出问题了", Toast.LENGTH_SHORT).show();
                                 }

                                 @Override
                                 public void onResponse(String response, int id) {
                                    jsonResult = response;
                                 }
                             } );

            Log.e(TAG,jsonResult);
            if(jsonResult!=null) {
                weather_list_items = new ArrayList<>();

                try {
                    JSONObject jsonObject = new JSONObject(jsonResult);
                    JSONArray results = jsonObject.getJSONArray("results");
                    JSONObject result = results.getJSONObject(0);
                    JSONObject location = result.getJSONObject("location");
                    String city = location.getString("name");

                    JSONArray daily = result.getJSONArray("daily");
                    JSONArray last_update = jsonObject.getJSONArray("last_update");

                    for (int i = 0; i < daily.length(); i++) {
                        JSONObject one = (JSONObject) daily.get(i);
                        String date1 = one.getString("date");
                        String fengxiang = one.getString("wind_direction");
                        String hightemp = one.getString("high");
                        String lowtemp = one.getString("low");
                        String type = one.getString("text_day");
                        String wind_scale = one.getString("wind_scale");
                      //  String week = one.getString("week");
                        Weather_list_Item weather_list_item = new Weather_list_Item();
                       weather_list_item.setDay(date1);
                        weather_list_item.setTemp1(hightemp);
                        weather_list_item.setTemp2(lowtemp+"/");
                        weather_list_item.setWeather(type);
                        weather_list_item.setWind(fengxiang);
                        weather_list_item.setWind_scale(wind_scale);
                        weather_list_items.add(weather_list_item);
                    }
                    String date = weather_list_items.get(0).getDay();
                    String fengxiang = weather_list_items.get(0).getWind();
                    String fengli = weather_list_items.get(0).getWind_scale();
                    String hightemp = weather_list_items.get(0).getTemp1();
                    String lowtemp = weather_list_items.get(0).getTemp2();
                    String type = weather_list_items.get(0).getWeather();
                    Weather weather = new Weather();
                    weather.setCity(city);
                    weather.setDate(date);
                    weather.setFengxiang(fengxiang);
                    weather.setFengli(fengli);
                    weather.setType(type);
                    weather.setLowtemp(lowtemp);
                    weather.setHightemp(hightemp);
                    Message msg = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data",weather);
                    msg.setData(bundle);
                    msg.what=1;
                    handler.sendMessage(msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                try {
                    Thread.sleep(3000);
                    new Thread(runnable).start();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            /*StreamTools streamTools = new StreamTools();
            try {
                byte[] fromInternet = streamTools.getDataFromInternet(path);
                JSONTokener jsontoken = new JSONTokener(new String(fromInternet));
                JSONObject weatherinfo = (JSONObject) jsontoken.nextValue();
                JSONObject jsonObject = weatherinfo.getJSONObject("weatherinfo");
                if(jsonObject!=null) {
                    String temp = jsonObject.getString("temp");
                    String city = jsonObject.getString("city");
                    String cityid = jsonObject.getString("cityid");
                    String WD = jsonObject.getString("WD");
                    String SD = jsonObject.getString("SD");
                    String WS = jsonObject.getString("WS");
                    String time = jsonObject.getString("time");
                    String isRadar = jsonObject.getString("isRadar");
                    String Radar = jsonObject.getString("Radar");
                    Weather weather = new Weather();
                    weather.setTemp(temp);
                    weather.setCity(city);
                    weather.setCityid(cityid);
                    weather.setWd(WD);
                    weather.setSd(SD);
                    weather.setWs(WS);
                    weather.setTime(time);
                    weather.setIsRadar(isRadar);
                    weather.setRadar(Radar);
                    Message msg = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data",weather);
                    msg.setData(bundle);
                    msg.what=1;
                    handler.sendMessage(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }*/
        }
    };
    private Handler handler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            setGridViewDate();
            setGridView();
            switch (msg.what)
            {
                case 1:
                    if (msg != null) {
                        Bundle bundle = msg.getData();
                        Weather weather = (Weather) bundle.getSerializable("data");
                        weather_wse.setText("风力强度："+weather.getFengli());
                        weather_temp.setText(weather.getCurTemp());
                        // weather_type.setText();
                        //  weather_pm.setText("湿度"+weather.getSd());
                        weather_wd.setText(weather.getFengxiang());
                        weather_type.setText(weather.getType());
                        weather_lowtemp.setText(weather.getLowtemp()+"/");
                        weather_hightemp.setText(weather.getHightemp());
                        if (weather.getAqi().equals("")||weather.getAqi()==null){
                            weather_pm.setText("PM:无");
                        }else {
                            weather_pm.setText("PM:"+weather.getAqi());
                        }
                        weather_city.setText(weather.getCity());
                        weather_time.setText(weather.getDate()+"  "+weather.getWeek());
                        setListView();
                    }
                    break;
                case 2:
                    mSwipeLayout.setRefreshing(false);
                    break;
                default:
                    break;
            }

        };
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(!data.getStringExtra("cityname").equals("")) {
                cityname = data.getStringExtra("cityname");
                cityId = data.getStringExtra("cityId");
                Log.i(TAG, "onActivityResult: ");
                new Thread(runnable).start();
            }
        }
    }
   /*  private class SynTask extends AsyncTask<String, Void, String> {


         @Override
         protected String doInBackground(String... params) {
             WeatherApi weatherApi = new WeatherApi();
             String httpUrl1 = "http://apis.baidu.com/heweather/weather/free";
             String httpArg1 = "city=beijing";
             String jsonResult1 = weatherApi.request(httpUrl1, httpArg1);
             return jsonResult1;
         }

         @Override
         // 任务执行返回结果后执行，可以与主界面线程进行数据交互。
         protected void onPostExecute(String result) {
             // TODO Auto-generated method stub
             Log.i(TAG, "onPostExecute: "+result);
         }
     }*/

}
