package com.psn.patrol.bean;

import android.graphics.Bitmap;

/**
 * Created by wsk on 2016/5/11.
 */
public class Weather_grid_Item {
    private String temp;
    private String state;
    private Bitmap weather;
    private String time;

    public Weather_grid_Item(String temp, String state, Bitmap weather, String time) {
        this.temp = temp;
        this.state = state;
        this.weather = weather;
        this.time = time;
    }

    public Weather_grid_Item() {
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Bitmap getWeather() {
        return weather;
    }

    public void setWeather(Bitmap weather) {
        this.weather = weather;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
