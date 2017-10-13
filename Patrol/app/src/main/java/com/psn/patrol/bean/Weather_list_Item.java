package com.psn.patrol.bean;

/**
 * Created by wsk on 2016/5/11.
 */
public class Weather_list_Item {
    private String day;
    private String temp1;
    private String temp2;
    private String weather;
    private String wind;
    private String wind_scale;


    public Weather_list_Item() {
    }

    public Weather_list_Item(String day, String temp1, String temp2, String weather, String wind) {
        this.day = day;
        this.temp1 = temp1;
        this.temp2 = temp2;
        this.weather = weather;
        this.wind = wind;
    }

    public String getWind_scale() {
        return wind_scale;
    }

    public void setWind_scale(String wind_scale) {
        this.wind_scale = wind_scale;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTemp1() {
        return temp1;
    }

    public void setTemp1(String temp1) {
        this.temp1 = temp1;
    }

    public String getTemp2() {
        return temp2;
    }

    public void setTemp2(String temp2) {
        this.temp2 = temp2;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }
}
