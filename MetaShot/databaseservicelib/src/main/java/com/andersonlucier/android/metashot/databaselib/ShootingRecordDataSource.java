package com.andersonlucier.android.metashot.databaselib;

/**
 * Created by SyberDeskTop on 3/27/2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;

import com.andersonlucier.android.metashot.databaseservicelib.impl.ShootingRecord;
import com.andersonlucier.android.metashot.databaseservicelib.interfaces.shooting.IShooting;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

public class ShootingRecordDataSource {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { "id",
            "title", "datetime", "lat", "lon", "temp", "windSpeed", "description", "gunTypeId" };

    public ShootingRecordDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public ShootingRecord createShootingRecord(ShootingRecord record) {
        ContentValues values = new ContentValues();
        String idToCreate = UUID.randomUUID().toString();
        values.put("id", idToCreate);
        values.put("title", record.title());
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        values.put("datetime", dateFormat.format(date));
        values.put("lat", record.lat());
        values.put("lon", record.lon());
        values.put("temp", record.temp());
        values.put("windSpeed", record.windspeed());
        values.put("description", record.description());
        if(record.typeOfGun() != null) {
            values.put("gunTypeId", record.typeOfGun().id());
        }

        database.insert("shootingRecord", null,
                values);
        Cursor cursor = database.query("shootingRecord",
                allColumns, "id" + " = '" + idToCreate + "'", null,
                null, null, null);
        cursor.moveToFirst();
        ShootingRecord newRecord = cursorToRecord(cursor);
        cursor.close();
        return newRecord;
    }

    private ShootingRecord cursorToRecord(Cursor cursor) {
        ShootingRecord comment = new ShootingRecord();
        comment.setId(cursor.getString(0));
        comment.setTitle(cursor.getString(1));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try {
            Date date = dateFormat.parse(cursor.getString(2));
            comment.setDateTime(date);
        } catch (ParseException e) {
            comment.setDateTime(null);
        }

        comment.setLat(cursor.getDouble(3));
        comment.setLon(cursor.getDouble(4));
        comment.setTemp(cursor.getDouble(5));
        comment.setWindspeed(cursor.getDouble(6));
        comment.setDescription(cursor.getString(7));
        //comment.setTypeOfGun(cursor.getString(8));
        return comment;
    }

}
