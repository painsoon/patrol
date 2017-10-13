package com.psn.patrol.bean;

import java.util.List;

/**
 * Author: shinianPan on 2017/5/17.
 * email : snow_psn@163.com
 */

public class Path {
    private  String pathName;
    private  String pathStartTime;
    private List<Basespot> results;


    public Path() {
    }

    public Path(String pathName, String pathStartTime, List<Basespot> results) {
        this.pathName = pathName;
        this.pathStartTime = pathStartTime;
        this.results = results;
    }

    @Override
    public String toString() {
        return "Path{" +
                "pathName='" + pathName + '\'' +
                ", pathStartTime='" + pathStartTime + '\'' +
                ", results=" + results +
                '}';
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public String getPathStartTime() {
        return pathStartTime;
    }

    public void setPathStartTime(String pathStartTime) {
        this.pathStartTime = pathStartTime;
    }

    public List<Basespot> getResults() {
        return results;
    }

    public void setResults(List<Basespot> results) {
        this.results = results;
    }
}
