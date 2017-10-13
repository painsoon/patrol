package com.psn.patrol.bean;

import java.util.Arrays;

/**
 * Author: shinianPan on 2017/5/15.
 * email : snow_psn@163.com
 */

public class ExeceptionInfo {
    private String         pathId;       // 路线id
    private String         userId;       // 员工Id
    private String         abnormalText; // 异常描述
    private String         abnormalImage[]; // 异常照片
    private String         checkPoint;      //检查点id
    private String createTime;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public ExeceptionInfo() {
    }

    public ExeceptionInfo(String pathId, String userId, String abnormalText, String[] abnormalImage, String checkPoint) {
        this.pathId = pathId;
        this.userId = userId;
        this.abnormalText = abnormalText;
        this.abnormalImage = abnormalImage;
        this.checkPoint = checkPoint;
    }

    @Override
    public String toString() {
        return "ExeceptionInfo{" +
                "pathId='" + pathId + '\'' +
                ", userId='" + userId + '\'' +
                ", abnormalText='" + abnormalText + '\'' +
                ", abnormalImage=" + Arrays.toString(abnormalImage) +
                ", checkPoint='" + checkPoint + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';

    }

    public String getPathId() {
        return pathId;
    }

    public void setPathId(String pathId) {
        this.pathId = pathId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAbnormalText() {
        return abnormalText;
    }

    public void setAbnormalText(String abnormalText) {
        this.abnormalText = abnormalText;
    }

    public String[] getAbnormalImage() {
        return abnormalImage;
    }

    public void setAbnormalImage(String[] abnormalImage) {
        this.abnormalImage = abnormalImage;
    }

    public String getCheckPoint() {
        return checkPoint;
    }

    public void setCheckPoint(String checkPoint) {
        this.checkPoint = checkPoint;
    }
}
