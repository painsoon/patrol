package com.psn.patrol.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Administrator on 2016/4/28.
 */
public class SqlManager {

    private TeskSqliteOpenHelper phelper;

    public SqlManager(){}

    public SqlManager(TeskSqliteOpenHelper phelper){

        this.phelper = phelper ;
    }

    public void createAddPath(){
        SQLiteDatabase sb= phelper.getWritableDatabase();
        sb.execSQL(CreateDB.addpath);
    }
    public void createTestPath(){
        SQLiteDatabase sb= phelper.getWritableDatabase();
        sb.execSQL(CreateDB.testpath);
    }

    public boolean Insert(String sql,String[] str){
        SQLiteDatabase sb=phelper.getWritableDatabase();
        sb.execSQL(sql,str);
        return true;
    }

    public boolean Delete(String sql){
        SQLiteDatabase sb=phelper.getWritableDatabase();
        sb.execSQL(sql);
        return true;
    }

    public boolean Update(String str){
        SQLiteDatabase sb=phelper.getWritableDatabase();
        sb.execSQL("update person set name='psn'",new Object[]{str});
        return true;
    }

    public Cursor Select(String sql,String args[]){
        SQLiteDatabase sb=phelper.getReadableDatabase();
        Cursor cs=sb.rawQuery(sql, args);
        return cs;
    }

}
