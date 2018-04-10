package com.andersonlucier.android.metashot.databaselib;

/**
 * Created by SyberDeskTop on 3/26/2018.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "metashot.db";
    private static final int DATABASE_VERSION = 2;

    private static String DATABASE_CREATE;
    private static String DATABASE_DELETE;

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        DATABASE_CREATE = context.getResources().getString(com.andersonlucier.android.metashot.databaseservicelib.R.string.createdatabase);
        DATABASE_DELETE = context.getResources().getString(com.andersonlucier.android.metashot.databaseservicelib.R.string.deletedatabase);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.v("Database Created", "the database has been started");
        database.execSQL(DATABASE_CREATE);
        Log.v("Database Created", "the database has been created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DATABASE_DELETE);
        onCreate(db);
    }

}