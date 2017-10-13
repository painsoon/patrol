package com.psn.patrol.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Author: shinianPan on 2017/4/12.
 * email : snow_psn@163.com
 */

public class TeskSqliteOpenHelper extends SQLiteOpenHelper {
    /*
           *   巡检路线表
           *   pathID:路线ID、检查点uid、检查点名称 tagName、startTime开始时间、param 该点参数、tagguy 巡检人员编号、标签数 count
            */
    public TeskSqliteOpenHelper(Context context, String name,
                                CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CreateDB.testpath);
        db.execSQL(CreateDB.addpath);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
