package com.andersonlucier.android.metashot.databaselib;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;

import com.andersonlucier.android.metashot.databaseservicelib.impl.ShootingRecord;
import com.andersonlucier.android.metashot.databaseservicelib.interfaces.service.IShootingService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ShootingRecordDataSource implements IShootingService {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String dbTableName = "shootingRecord";
    private String[] allColumns = { "id",
            "title", "datetime", "location", "temp", "wind", "description", "gunTypeId", "weather", "range" };

    public ShootingRecordDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    /**
     * Creates a new shooting record based on the shooting record passed in
     * @param record to be created
     * @return Shooting Record
     */
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
            values.put("gunTypeId", record.gunId());
        }
        values.put("weather", record.weather());
        values.put("range", record.range());

        database.insert(dbTableName, null,
                values);
        Cursor cursor = database.query(dbTableName,
                allColumns, "id" + " = '" + idToCreate + "'", null,
                null, null, null);
        cursor.moveToFirst();
        ShootingRecord newRecord = cursorToRecord(cursor);
        cursor.close();
        return newRecord;
    }

    /**
     * Gets all the shooting records in the database
     * @return List of Shooting Records
     */
    public List<ShootingRecord> getAllShootingRecords () {
        List<ShootingRecord> records = new ArrayList<ShootingRecord> ();

        Cursor cursor = database.query(dbTableName,
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

    /**
     * Gets a single shooting record based on the id
     * @param id of the shooting record to return
     * @return Shooting Record
     */
    public ShootingRecord getSingleShootingRecord (String id) {

        Cursor cursor = database.query(dbTableName,
                allColumns, "id" + " = '" + id + "'", null, null, null, null);

        cursor.moveToFirst();
        ShootingRecord singleRecord = cursorToRecord(cursor);
        cursor.close();
        return singleRecord;
    }

    /**
     * Deletes the shooting record based on the id
     * @param id of record to be deleted
     */
    public void deleteShootingRecord(String id) {
        System.out.println("Shooting Record deleted with id: " + id);
        database.delete(dbTableName, "id" + " = '" + id + "'", null);
    }

    /**
     * Populates a Shooting Record from the database cursor
     * @param cursor current database row
     * @return Shooting Record
     */
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
        if(cursor.getString(7) != null) {
            comment.setGunId(cursor.getString(7));
        }
        comment.setWeather(cursor.getString(8));
        comment.setRange(cursor.getString(9));
        return comment;
    }

}
