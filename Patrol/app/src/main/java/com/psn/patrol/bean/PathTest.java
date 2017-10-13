package com.psn.patrol.bean;

/**
 * Author: shinianPan on 2017/4/14.
 * email : snow_psn@163.com
 */

public class PathTest {

    private String pathID;
    private String tagID;
    private String tagGuy; //巡检人员编号
    private String tagName; //巡检点名称
    private String startTime;
    private String param;
    private String right="-1";

    public PathTest() {
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public PathTest(String pathID, String tagID, String tagGuy, String tagName, String startTime, String param) {
        this.pathID = pathID;
        this.tagID = tagID;
        this.tagGuy = tagGuy;
        this.tagName = tagName;
        this.startTime = startTime;
        this.param = param;
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

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return "PathTest{" +
                "pathID='" + pathID + '\'' +
                ", tagID='" + tagID + '\'' +
                ", tagGuy='" + tagGuy + '\'' +
                ", tagName='" + tagName + '\'' +
                ", startTime='" + startTime + '\'' +
                ", param='" + param + '\'' +
                '}';
    }
    public String[] toArray() {
        String str[] = new String[6];
        str[0] = pathID;
        str[1] = tagID;
        str[2] = tagGuy;
        str[3] = tagName;
        str[4] = startTime;
        str[5] = param;
        return str;
    }
}
