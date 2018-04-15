package com.andersonlucier.android.metashot.databaselib;

/**
 * Created by SyberDeskTop on 3/26/2018.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.andersonlucier.android.metashot.databaseservicelib.R;

public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "metashot.db";
    private static final int DATABASE_VERSION = 6;

    private static String DATABASE_SHOOTING_TABLE;
    private static String DATABASE_GUN_TABLE;
    private static String DATABASE_GUN_DELETE;
    private static String DATABASE_SHOOTING_DELETE;

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        DATABASE_SHOOTING_TABLE= context.getResources().getString(R.string.createdshootingrecord);
        DATABASE_GUN_TABLE= context.getResources().getString(R.string.createdgunrecord);
        DATABASE_GUN_DELETE = context.getResources().getString(R.string.deletegunrecord);
        DATABASE_SHOOTING_DELETE = context.getResources().getString(R.string.deleteshootingrecord);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.v("Database Created", "the database has been started");
        database.execSQL(DATABASE_SHOOTING_TABLE);
        database.execSQL(DATABASE_GUN_TABLE);
        Log.v("Database Created", "the database has been created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DATABASE_GUN_DELETE);
        db.execSQL(DATABASE_SHOOTING_DELETE);
        onCreate(db);
    }

}