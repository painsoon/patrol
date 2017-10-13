package com.psn.patrol.bean;

/**
 * Author: shinianPan on 2017/5/17.
 * email : snow_psn@163.com
 */

public class Basespot {

    private String tagId;
    private String id;
    private String tagName; //巡检点名称
    private String startTime;
    private String params;

    public Basespot() {
    }

    public Basespot(String id,String tagId, String tagName, String startTime, String params) {
        this.id = id;
        this.tagId = tagId;
        this.tagName = tagName;
        this.startTime = startTime;
        this.params = params;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "Basespot{" +
                "tagId='" + tagId + '\'' +
                ", id='" + id + '\'' +
                ", tagName='" + tagName + '\'' +
                ", startTime='" + startTime + '\'' +
                ", params='" + params + '\'' +
                '}';
    }
}
