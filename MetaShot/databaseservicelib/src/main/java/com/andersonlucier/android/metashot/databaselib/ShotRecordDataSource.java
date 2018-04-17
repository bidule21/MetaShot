package com.andersonlucier.android.metashot.databaselib;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.andersonlucier.android.metashot.databaseservicelib.impl.ShotRecord;
import com.andersonlucier.android.metashot.databaseservicelib.interfaces.service.IShotService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShotRecordDataSource implements IShotService {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String dbName = "shotRecord";
    private String[] allColumns = { "id", "shootingRecordId", "shotNumber", 
            "barrelTemp", "targetX", "targetY"};
    
    public ShotRecordDataSource(Context context) {dbHelper = new MySQLiteHelper(context); }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    /**
     * Gets all the shot records for a given shooting record
     * @param id of the shooting record
     * @return List of Shot Records
     */
    @Override
    public List<ShotRecord> getAllShotsRecordsByShootingId(String id) {
        List<ShotRecord> records = new ArrayList<>();

        Cursor cursor = database.query(dbName,
                allColumns, "shootingRecordId" + " = '" + id + "'", null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            ShotRecord record = cursorToRecord(cursor);
            records.add(record);
            cursor.moveToNext();
        }
        cursor.close();
        return records;
    }

    /**
     * Gets a single shot record by a given id
     * @param id of the shot record
     * @return Shot Record
     */
    @Override
    public ShotRecord getSingleShotsRecordsById(String id) {
        Cursor cursor = database.query(dbName,
                allColumns, "id" + " = '" + id + "'", null, null, null, null);

        cursor.moveToFirst();
        ShotRecord singleRecord = cursorToRecord(cursor);
        cursor.close();
        return singleRecord;
    }

    /**
     * Creates a shot record based on the record passed in
     * @param record to be created
     * @return Shot Record
     */
    @Override
    public ShotRecord createShotRecord(ShotRecord record) {
        ContentValues values = new ContentValues();
        String idToCreate = UUID.randomUUID().toString();
        values.put("id", idToCreate);
        values.put("shootingRecordId", record.shootingRecordId());
        values.put("shotNumber", record.shotNumber());
        values.put("barrelTemp", record.barrelTemp());
        values.put("targetX", record.targetX());
        values.put("targetY", record.targetY());

        database.insert(dbName, null,
                values);
        Cursor cursor = database.query(dbName,
                allColumns, "id" + " = '" + idToCreate + "'", null,
                null, null, null);
        cursor.moveToFirst();
        ShotRecord newRecord = cursorToRecord(cursor);
        cursor.close();
        return newRecord;
    }

    /**
     * Delete a shot record by a given id
     * @param id of the record to delete
     */
    @Override
    public void deleteShotRecord(String id) {
        System.out.println("Shot Record deleted with id: " + id);
        database.delete(dbName, "id" + " = '" + id + "'", null);
    }

    /**
     * Updates a shot record based on the record being passed in
     * @param record of the updated record to be saved
     * @return Shot Record
     */
    @Override
    public ShotRecord updateShotRecord(ShotRecord record) {
        return null;
    }

    /**
     * Populates a shot Record from the database cursor
     * @param cursor current database row
     * @return Shot Record
     */
    private ShotRecord cursorToRecord(Cursor cursor) {
        ShotRecord comment = new ShotRecord();
        comment.setId(cursor.getString(0));
        comment.setShootingRecordId(cursor.getString(1));
        comment.setShotNumber(cursor.getInt(2));
        comment.setBarrelTemp(cursor.getDouble(3));
        comment.setTargetX(cursor.getDouble(4));
        comment.setTargetY(cursor.getDouble(5));
        return comment;
    }
}
