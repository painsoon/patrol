package com.psn.patrol.bean;

/**
 * Author: shinianPan on 2017/5/18.
 * email : snow_psn@163.com
 */

public class Puser {
    private String name;
    private String id;
    private String  pathid;

    public Puser() {
    }

    public Puser(String name, String id, String pathid) {
        this.name = name;
        this.id = id;
        this.pathid = pathid;
    }

    @Override
    public String toString() {
        return "Puser{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", pathid='" + pathid + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPathid() {
        return pathid;
    }

    public void setPathid(String pathid) {
        this.pathid = pathid;
    }
}
