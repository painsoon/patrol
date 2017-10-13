package com.psn.patrol.bean;

import java.io.Serializable;

/**
 * Created by wsk on 2016/5/8.
 */
public class Weather implements Serializable {
    private String city;//城市
    private String cityid;//城市ID
    private String curTemp;//温度
    private String aqi;//pm
    private String date;//日期
    private String week;//星期
    private String fengxiang;//风向
    private String fengli;//风力
    private String hightemp;//最高温度
    private String lowtemp;//最低温度
    private String index;//指标列表
    private String type; //天气状态
    public Weather(String city, String cityid, String curTemp, String aqi, String date, String week, String fengxiang, String fengli, String hightemp, String lowtemp, String index, String type) {
        this.city = city;
        this.cityid = cityid;
        this.curTemp = curTemp;
        this.aqi = aqi;
        this.date = date;
        this.week = week;
        this.fengxiang = fengxiang;
        this.fengli = fengli;
        this.hightemp = hightemp;
        this.lowtemp = lowtemp;
        this.index = index;
        this.type = type;
    }


    public Weather() {
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getCurTemp() {
        return curTemp;
    }

    public void setCurTemp(String curTemp) {
        this.curTemp = curTemp;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getFengxiang() {
        return fengxiang;
    }

    public void setFengxiang(String fengxiang) {
        this.fengxiang = fengxiang;
    }

    public String getFengli() {
        return fengli;
    }

    public void setFengli(String fengli) {
        this.fengli = fengli;
    }

    public String getHightemp() {
        return hightemp;
    }

    public void setHightemp(String hightemp) {
        this.hightemp = hightemp;
    }

    public String getLowtemp() {
        return lowtemp;
    }

    public void setLowtemp(String lowtemp) {
        this.lowtemp = lowtemp;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
