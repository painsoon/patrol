package com.psn.patrol.bean;

import java.io.Serializable;

/**
 * Created by wsk on 2016/5/12.
 */
public class City implements Serializable {
    String Cityname;
    String CityId;

    public City() {
    }
    public City(String cityname, String cityId) {
        Cityname = cityname;
        CityId = cityId;
    }

    public String getCityname() {
        return Cityname;
    }

    public void setCityname(String cityname) {
        Cityname = cityname;
    }

    public String getCityId() {
        return CityId;
    }

    public void setCityId(String cityId) {
        CityId = cityId;
    }
}
