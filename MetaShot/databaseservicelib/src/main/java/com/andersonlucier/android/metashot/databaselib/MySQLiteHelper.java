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

    //Database Name and Version
    private static final String DATABASE_NAME = "metashot.db";
    private static final int DATABASE_VERSION = 11;

    //static string of creating and dropping the tables
    private static String DATABASE_SHOOTING_TABLE;
    private static String DATABASE_GUN_TABLE;
    private static String DATABASE_GUN_DELETE;
    private static String DATABASE_SHOOTING_DELETE;
    private static String DATABASE_SHOT_TABLE;
    private static String DATABASE_SHOT_DELETE;
    private static String DATABASE_METAWEAR_CREATE;
    private static String DATABASE_METAWEAR_DELETE;


    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        DATABASE_SHOOTING_TABLE= context.getResources().getString(R.string.createdshootingrecord);
        DATABASE_GUN_TABLE= context.getResources().getString(R.string.createdgunrecord);
        DATABASE_GUN_DELETE = context.getResources().getString(R.string.deletegunrecord);
        DATABASE_SHOOTING_DELETE = context.getResources().getString(R.string.deleteshootingrecord);

        DATABASE_SHOT_TABLE= context.getResources().getString(R.string.createdshotrecord);
        DATABASE_SHOT_DELETE = context.getResources().getString(R.string.deleteshotrecord);

        DATABASE_METAWEAR_CREATE = context.getResources().getString(R.string.createmetawearrecord);
        DATABASE_METAWEAR_DELETE = context.getResources().getString(R.string.deletemetawearrecord);
    }

    /**
     * Creates the database tables
     * @param database to create the tables in
     */
    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.v("Database Creation", "the database creating has been started");
        database.execSQL(DATABASE_SHOOTING_TABLE);
        database.execSQL(DATABASE_GUN_TABLE);
        database.execSQL(DATABASE_SHOT_TABLE);
        database.execSQL(DATABASE_METAWEAR_CREATE);
        Log.v("Database Creation", "the database has been created");
    }

    /**
     * Deletes the database tables so that the tables can be created
     * @param db database to delete and create
     * @param oldVersion current version
     * @param newVersion new version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DATABASE_GUN_DELETE);
        db.execSQL(DATABASE_SHOOTING_DELETE);
        db.execSQL(DATABASE_SHOT_DELETE);
        db.execSQL(DATABASE_METAWEAR_DELETE);
        onCreate(db);
    }

}