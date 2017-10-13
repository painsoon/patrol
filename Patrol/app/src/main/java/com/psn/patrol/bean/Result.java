package com.psn.patrol.bean;

import java.util.Arrays;

/**
 * Author: shinianPan on 2017/5/19.
 * email : snow_psn@163.com
 */

public class Result {

    private String tagId;
    private String disc;
    private String isRight;
    private String photo[];

    public Result() {
    }

    public Result(String tagId, String disc, String isRight, String[] photo) {
        this.tagId = tagId;
        this.disc = disc;
        this.isRight = isRight;
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Result{" +
                "tagId='" + tagId + '\'' +
                ", disc='" + disc + '\'' +
                ", isRight='" + isRight + '\'' +
                ", photo=" + Arrays.toString(photo) +
                '}';
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getDisc() {
        return disc;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }

    public String getIsRight() {
        return isRight;
    }

    public void setIsRight(String isRight) {
        this.isRight = isRight;
    }

    public String[] getPhoto() {
        return photo;
    }

    public void setPhoto(String[] photo) {
        this.photo = photo;
    }
}
