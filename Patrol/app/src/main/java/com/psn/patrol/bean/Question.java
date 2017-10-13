package com.psn.patrol.bean;

import java.io.Serializable;

/**
 * Author: shinianPan on 2017/3/27.
 * email : snow_psn@163.com
 */

public class Question implements Serializable{
    private String pathID;
    private String tagID;
    private String tagGuy; //巡检人员编号
    private String param;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String photo[];

    public Question() {
    }

    public String getPathID() {
        return pathID;
    }

    public void setPathID(String pathID) {
        this.pathID = pathID;
    }

    public String getTagID() {
        return tagID;
    }

    public void setTagID(String tagID) {
        this.tagID = tagID;
    }

    public String getTagGuy() {
        return tagGuy;
    }

    public void setTagGuy(String tagGuy) {
        this.tagGuy = tagGuy;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String[] getPhoto() {
        return photo;
    }

    public void setPhoto(String[] photo) {
        this.photo = photo;
    }
}
