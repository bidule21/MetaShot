package com.andersonlucier.android.metashot.databaseservicelib;

import android.content.Context;

import com.andersonlucier.android.metashot.databaselib.GunRecordDataSource;
import com.andersonlucier.android.metashot.databaselib.ShotRecordDataSource;
import com.andersonlucier.android.metashot.databaseservicelib.impl.GunRecord;
import com.andersonlucier.android.metashot.databaseservicelib.interfaces.service.IGunService;
import com.andersonlucier.android.metashot.databaseservicelib.interfaces.service.IShotService;
import com.andersonlucier.android.metashot.databaseservicelib.impl.ShootingRecord;
import com.andersonlucier.android.metashot.databaseservicelib.impl.ShotRecord;
import com.andersonlucier.android.metashot.databaselib.ShootingRecordDataSource;
import com.andersonlucier.android.metashot.databaseservicelib.interfaces.service.IShootingService;

import java.util.ArrayList;
import java.util.List;

public class DatabaseShotService implements IShotService, IShootingService, IGunService {

    //Database Source Objects
    private ShootingRecordDataSource shootingDataSource;
    private GunRecordDataSource gunDataSource;
    private ShotRecordDataSource ShotDataSource;

    public DatabaseShotService(Context context) {

        //Configure the database objects and open the connection to the database
        shootingDataSource = new ShootingRecordDataSource(context);
        shootingDataSource.open();

        gunDataSource = new GunRecordDataSource(context);
        gunDataSource.open();

        ShotDataSource = new ShotRecordDataSource(context);
        ShotDataSource.open();
    }

    //Shooting Records
    /**
     * Gets all the shooting records in the database
     * @return List of Shooting Records
     */
    @Override
    public List<ShootingRecord> getAllShootingRecords() {
        List<ShootingRecord> returnValue = shootingDataSource.getAllShootingRecords();
        return returnValue;

    }

    /**
     * Gets a single shooting record based on the id
     * @param id of the shooting record to return
     * @return Shooting Record
     */
    @Override
    public ShootingRecord getSingleShootingRecord(String id) {
        ShootingRecord returnValue = shootingDataSource.getSingleShootingRecord(id);
        return returnValue;
    }

    /**
     * Deletes the shooting record based on the id
     * @param id of record to be deleted
     */
    @Override
    public void deleteShootingRecord(String id) {
        shootingDataSource.deleteShootingRecord(id);
    }

    /**
     * Creates a new shooting record based on the shooting record passed in
     * @param record to be created
     * @return Shooting Record
     */
    @Override
    public ShootingRecord createShootingRecord(ShootingRecord record) {
        ShootingRecord returnValue = shootingDataSource.createShootingRecord(record);
        return returnValue;
    }


    //Gun Record service

    /**
     * Gets all the gun records in the database
     * @return List of Gun Records
     */
    @Override
    public List<GunRecord> getAllGunRecords() {
        List<GunRecord> gunList = gunDataSource.getAllGunRecords();
        return gunList;
    }

    /**
     * Creates a gun record based on the gun record passed in
     * @param record to be created
     * @return Gun Record
     */
    @Override
    public GunRecord createGunRecord(GunRecord record) {
        return gunDataSource.createGunRecord(record);
    }

    /**
     * Delete a gun record based on the id
     * @param id of the record to delete
     */
    @Override
    public void deleteGunRecord(String id) {
        gunDataSource.deleteGunRecord(id);
    }

    /**
     * Gets a single gun record based on the id passed in
     * @param id of the gun record
     * @return Gun Record
     */
    @Override
    public GunRecord getSingleGunRecord(String id) {
        return gunDataSource.getSingleGunRecord(id);
    }

    /**
     * updates a gun record based on the record passed in
     * @param record gun record to update
     * @return Gun Record
     */
    @Override
    public GunRecord updateGunRecord(GunRecord record) {
        return gunDataSource.updateGunRecord(record);
    }

    //Shot Records Service

    /**
     * Gets all the shot records for a given shooting record
     * @param id of the shooting record
     * @return List of Shot Records
     */
    @Override
    public List<ShotRecord> getAllShotsRecordsByShootingId(String id) {
        //TODO: Uncomment for actual database results
/*        List<ShotRecord> returnValue = ShotDataSource.getAllShotsRecordsByShootingId(id);
        return returnValue;*/

        List<ShotRecord> returnValue = new ArrayList<>();
        for (int x = 1; x <= 8; x++) {
            ShotRecord record = new ShotRecord();
            record.setId("abc");
            record.setShotNumber(x);
            record.setBarrelTemp(90);
            record.setTargetX(-3);
            record.setTargetY(2);
            record.setShootingRecordId("CDF");
            returnValue.add(record);
        }
        return returnValue;

    }

    /**
     * Gets a single shot record by a given id
     * @param id of the shot record
     * @return Shot Record
     */
    @Override
    public ShotRecord getSingleShotsRecordsById(String id) {
        //TODO: Uncomment for actual database results
        /*ShotRecord result = ShotDataSource.getSingleShotsRecordsById(id);
        return result;*/
        ShotRecord record = new ShotRecord();
        record.setId("abc");
        record.setShotNumber(1);
        record.setBarrelTemp(90);
        record.setTargetX(-3);
        record.setTargetY(2);
        record.setShootingRecordId("CDF");

        return record;

    }

    /**
     * Creates a shot record based on the record passed in
     * @param record to be created
     * @return Shot Record
     */
    @Override
    public ShotRecord createShotRecord(ShotRecord record) {
        ShotRecord result = ShotDataSource.createShotRecord(record);
        return result;
    }

    /**
     * Delete a shot record by a given id
     * @param id of the record to delete
     */
    @Override
    public void deleteShotRecord(String id) {
        //TODO: Uncomment
        //ShotDataSource.deleteShotRecord(id);
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
}
