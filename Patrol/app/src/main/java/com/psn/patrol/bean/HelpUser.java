package com.psn.patrol.bean;

/**
 * Created by Administrator on 2017/3/15.
 */

public class HelpUser {
    private String name;
    private String phone;

    public HelpUser() {
    }

    public HelpUser(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
