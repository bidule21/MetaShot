package com.andersonlucier.android.metashot.databaselib;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.andersonlucier.android.metashot.databaseservicelib.impl.GunRecord;
import com.andersonlucier.android.metashot.databaseservicelib.interfaces.service.IGunService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by SyberDeskTop on 4/15/2018.
 */

public class GunRecordDataSource implements IGunService {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String dbTableName = "gunRecord";
    private String[] allColumns = { "id", "name", "description"};

    public GunRecordDataSource(Context context) {dbHelper = new MySQLiteHelper(context);}

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public GunRecord createGunRecord(GunRecord record) {
        ContentValues values = new ContentValues();
        String idToCreate = UUID.randomUUID().toString();
        values.put("id", idToCreate);
        values.put("name", record.gunName());
        values.put("description", record.details());

        database.insert(dbTableName, null,
                values);

        Cursor cursor = database.query(dbTableName,
                allColumns, "id" + " = '" + idToCreate + "'", null,
                null, null, null);
        cursor.moveToFirst();
        GunRecord newRecord = cursorToRecord(cursor);
        cursor.close();
        return newRecord;
    }

    public List<GunRecord> getAllGunRecords() {
        List<GunRecord> records = new ArrayList<>();

        Cursor cursor = database.query(dbTableName,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            GunRecord record = cursorToRecord(cursor);
            records.add(record);
            cursor.moveToNext();
        }
        cursor.close();
        return records;
    }

    public GunRecord getSingleGunRecord (String id) {

        Cursor cursor = database.query(dbTableName,
                allColumns, "id" + " = '" + id + "'", null, null, null, null);

        cursor.moveToFirst();
        GunRecord singleRecord = cursorToRecord(cursor);
        cursor.close();
        return singleRecord;
    }

    public void deleteGunRecord(String id) {
        System.out.println("Gun Record deleted with id: " + id);
        database.delete(dbTableName, "id" + " = '" + id + "'", null);
    }

    private GunRecord cursorToRecord(Cursor cursor) {
        GunRecord record = new GunRecord();
        record.setId(cursor.getString(0));
        record.setGunName(cursor.getString(1));
        record.setDetails(cursor.getString(2));

        return record;
    }

}
