package com.andersonlucier.android.metashot.databaselib;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.andersonlucier.android.metashot.databaseservicelib.impl.MetaWear;
import com.andersonlucier.android.metashot.databaseservicelib.impl.ShotRecord;
import com.andersonlucier.android.metashot.databaseservicelib.interfaces.service.IMetaWearService;
import com.andersonlucier.android.metashot.databaseservicelib.interfaces.service.IShotService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Data source for the metawear to interact with the database
 */
public class MetaWearDataSource implements IMetaWearService {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String dbName = "metawear";
    private String[] allColumns = { "id", "mac"};

    public MetaWearDataSource(Context context) {dbHelper = new MySQLiteHelper(context); }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    /**
     * Populates a shot Record from the database cursor
     * @param cursor current database row
     * @return Shot Record
     */
    private MetaWear cursorToRecord(Cursor cursor) {
        MetaWear comment = new MetaWear();
        comment.setId(cursor.getString(0));
        comment.setMacAddress(cursor.getString(1));
        return comment;
    }

    /**
     * gets a list of metawear records in the database
     * @return List of MetaWear
     */
    @Override
    public List<MetaWear> getMetawear() {
        List<MetaWear> records = new ArrayList<>();

        Cursor cursor = database.query(dbName,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            MetaWear record = cursorToRecord(cursor);
            records.add(record);
            cursor.moveToNext();
        }
        cursor.close();

        return records;
    }

    /**
     * updates the metawear in the database
     * @param mac metawear device to update
     * @return Metawear that was updated
     */
    @Override
    public MetaWear updateMetawear(MetaWear mac) {
        ContentValues values = new ContentValues();
        String id = mac.id();
        values.put("id", id);
        values.put("mac", mac.macAddress());

        database.update(dbName, values,"id" + " = '" + id + "'", null);

        return mac;
    }

    /**
     * Creates a metawear in the database
     * @param mac metawear device to create
     * @return Metawear
     */
    @Override
    public MetaWear createMetawear(MetaWear mac) {
        ContentValues values = new ContentValues();
        String idToCreate = UUID.randomUUID().toString();
        values.put("id", idToCreate);
        values.put("mac", mac.macAddress());
        database.insert(dbName, null,
                values);

        Cursor cursor = database.query(dbName,
                allColumns, "id" + " = '" + idToCreate + "'", null,
                null, null, null);
        cursor.moveToFirst();
        MetaWear newRecord = cursorToRecord(cursor);
        cursor.close();
        return newRecord;
    }

    /**
     * Delete the metawear device by the provided id
     * @param id of the metawear record
     */
    @Override
    public void deleteMetawear(String id) {
        database.delete(dbName, "id" + " = '" + id + "'", null);
    }
}
