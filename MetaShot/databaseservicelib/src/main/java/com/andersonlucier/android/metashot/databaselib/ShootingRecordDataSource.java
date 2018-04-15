package com.andersonlucier.android.metashot.databaselib;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;

import com.andersonlucier.android.metashot.databaseservicelib.impl.ShootingRecord;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ShootingRecordDataSource {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { "id",
            "title", "datetime", "location", "temp", "wind", "description", "gunTypeId", "weather" };

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
        values.put("location", record.location());
        values.put("temp", record.temp());
        values.put("wind", record.wind());
        values.put("description", record.description());
        if(record.typeOfGun() != null) {
            values.put("gunTypeId", record.typeOfGun().id());
        }
        values.put("weather", record.weather());

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

    public List<ShootingRecord> getAllShootingRecords () {
        List<ShootingRecord> records = new ArrayList<ShootingRecord> ();

        Cursor cursor = database.query("shootingRecord",
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            ShootingRecord record = cursorToRecord(cursor);
            records.add(record);
            cursor.moveToNext();
        }
        cursor.close();
        return records;
    }

    public ShootingRecord getSingleShootingRecord (String id) {

        Cursor cursor = database.query("shootingRecord",
                allColumns, "id" + " = '" + id + "'", null, null, null, null);

        cursor.moveToFirst();
        ShootingRecord singleRecord = cursorToRecord(cursor);
        cursor.close();
        return singleRecord;
    }

    public void deleteShootingRecord(String id) {
        System.out.println("Shooting Record deleted with id: " + id);
        database.delete("shootingRecord", "id" + " = '" + id + "'", null);
    }

    private ShootingRecord cursorToRecord(Cursor cursor) {
        ShootingRecord comment = new ShootingRecord();
        comment.setId(cursor.getString(0));
        comment.setTitle(cursor.getString(1));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            Date date = dateFormat.parse(cursor.getString(2));
            comment.setDateTime(date);
        } catch (ParseException e) {
            comment.setDateTime(null);
        }

        comment.setLocation(cursor.getString(3));
        comment.setTemp(cursor.getDouble(4));
        comment.setWind(cursor.getString(5));
        comment.setDescription(cursor.getString(6));
        //comment.setTypeOfGun(cursor.getString(7));
        comment.setWeather(cursor.getString(8));
        return comment;
    }

}
