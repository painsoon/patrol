package com.psn.patrol.db;

/**
 * Author: shinianPan on 2017/5/4.
 * email : snow_psn@163.com
 */

public class CreateDB {
    public static final String testpath = "create table testpath(" +
            "id integer primary key autoincrement," +
            "pathID varchar(50)," +
            "tagID varchar(50)," +
            "tagName varchar(50)," +
            "startTime varchar(50)," +
            " param varchar (500)," +
            "tagGuy varchar(50) )";

    public static String addpath = "create table addpath(" +
            "id integer primary key autoincrement," +
            "pathID varchar(50)," +
            "startTime varchar(50)," +
            "tagGuy varchar(50) " +
            ")";
}
