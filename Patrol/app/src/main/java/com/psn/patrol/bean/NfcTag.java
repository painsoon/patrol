package com.psn.patrol.bean;

/**
 * Author: shinianPan on 2017/3/22.
 * email : snow_psn@163.com
 */

public class NfcTag {
    private String tagID;
    private String tagGuy; //巡检人员编号
    private String tagName; //巡检点名称
    private String tagException;
    private String tagpic;

    public NfcTag() {
    }

    public NfcTag(String tagID, String tagGuy, String tagException, String tagpic) {
        this.tagID = tagID;
        this.tagGuy = tagGuy;
        this.tagException = tagException;
        this.tagpic = tagpic;
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

    public String getTagException() {
        return tagException;
    }

    public void setTagException(String tagException) {
        this.tagException = tagException;
    }

    public String getTagpic() {
        return tagpic;
    }

    public void setTagpic(String tagpic) {
        this.tagpic = tagpic;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public String toString() {
        return "NfcTag{" +
                "tagID='" + tagID + '\'' +
                ", tagGuy='" + tagGuy + '\'' +
                ", tagException='" + tagException + '\'' +
                ", tagpic='" + tagpic + '\'' +
                '}';
    }
}
