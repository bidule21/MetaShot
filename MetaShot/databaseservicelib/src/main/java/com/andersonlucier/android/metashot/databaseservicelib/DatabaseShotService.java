package com.andersonlucier.android.metashot.databaseservicelib;

import android.content.Context;

import com.andersonlucier.android.metashot.databaselib.GunRecordDataSource;
import com.andersonlucier.android.metashot.databaselib.MetaWearDataSource;
import com.andersonlucier.android.metashot.databaselib.ShotRecordDataSource;
import com.andersonlucier.android.metashot.databaseservicelib.impl.GunRecord;
import com.andersonlucier.android.metashot.databaseservicelib.impl.MetaWear;
import com.andersonlucier.android.metashot.databaseservicelib.interfaces.service.IGunService;
import com.andersonlucier.android.metashot.databaseservicelib.interfaces.service.IShotService;
import com.andersonlucier.android.metashot.databaseservicelib.impl.ShootingRecord;
import com.andersonlucier.android.metashot.databaseservicelib.impl.ShotRecord;
import com.andersonlucier.android.metashot.databaselib.ShootingRecordDataSource;
import com.andersonlucier.android.metashot.databaseservicelib.interfaces.service.IShootingService;

import java.util.List;

public class DatabaseShotService implements IShotService, IShootingService, IGunService {

    //Database Source Objects
    private ShootingRecordDataSource shootingDataSource;
    private GunRecordDataSource gunDataSource;
    private ShotRecordDataSource ShotDataSource;
    private MetaWearDataSource metawearDataSource;

    public DatabaseShotService(Context context) {

        //Configure the database objects and open the connection to the database
        shootingDataSource = new ShootingRecordDataSource(context);
        shootingDataSource.open();

        gunDataSource = new GunRecordDataSource(context);
        gunDataSource.open();

        ShotDataSource = new ShotRecordDataSource(context);
        ShotDataSource.open();

        metawearDataSource = new MetaWearDataSource(context);
        metawearDataSource.open();
    }

    //Shooting Records
    /**
     * Gets all the shooting records in the database
     * @return List of Shooting Records
     */
    @Override
    public List<ShootingRecord> getAllShootingRecords() {
        List<ShootingRecord> returnValue = shootingDataSource.getAllShootingRecords();
        for(ShootingRecord record : returnValue) {
            if(record.typeOfGun() != null) {
                GunRecord gunType = gunDataSource.getSingleGunRecord(record.typeOfGun().id());
                record.setTypeOfGun(gunType);
            }
        }
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
        if(returnValue.typeOfGun() != null) {
            GunRecord gunType = gunDataSource.getSingleGunRecord(returnValue.gunId());
            returnValue.setTypeOfGun(gunType);
        }
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

    @Override
    public ShootingRecord updateShootingRecord(ShootingRecord record) {
        return shootingDataSource.updateShootingRecord(record);
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
        return ShotDataSource.getAllShotsRecordsByShootingId(id);
    }

    /**
     * Gets a single shot record by a given id
     * @param id of the shot record
     * @return Shot Record
     */
    @Override
    public ShotRecord getSingleShotsRecordsById(String id) {
        return ShotDataSource.getSingleShotsRecordsById(id);
    }

    /**
     * Creates a shot record based on the record passed in
     * @param record to be created
     * @return Shot Record
     */
    @Override
    public ShotRecord createShotRecord(ShotRecord record) {
        List<ShotRecord> records = ShotDataSource.getAllShotsRecordsByShootingId(record.shootingRecordId());
        int shotNumber = 0;
        for(ShotRecord shot : records) {
            if(shot.shotNumber() > shotNumber){
                shotNumber = shot.shotNumber();
            }
        }
        shotNumber = shotNumber + 1;
        record.setShotNumber(shotNumber);

        ShotRecord result = ShotDataSource.createShotRecord(record);
        return result;
    }

    /**
     * Delete a shot record by a given id
     * @param id of the record to delete
     */
    @Override
    public void deleteShotRecord(String id) {
        ShotDataSource.deleteShotRecord(id);
    }

    /**
     * Updates a shot record based on the record being passed in
     * @param record of the updated record to be saved
     * @return Shot Record
     */
    @Override
    public ShotRecord updateShotRecord(ShotRecord record) {
        return ShotDataSource.updateShotRecord(record);
    }

    /**
     * Gets the first metawear device in the database.
     * @return Metawear device
     */
    public MetaWear getMetawear() {
        List<MetaWear> records = metawearDataSource.getMetawear();
        if (records.size() == 0) {
            MetaWear newRecord = new MetaWear();
            newRecord.setMacAddress("");
            return newRecord;
        } else {
            return records.get(0);
        }
    }

    /**
     * updates the metawear device with the device being sent in
     * @param mac metawear device object
     * @return Metawear device
     */
    public MetaWear updateMetawear(MetaWear mac) {
        return metawearDataSource.updateMetawear(mac);
    }

    /**
     * Creates the metawear device in the database
     * @param mac metawear device to create in the database
     * @return metawear device to create
     */
    public MetaWear createMetawear(MetaWear mac) {
        return metawearDataSource.createMetawear(mac);
    }

    /**
     * Delete the metawear device by the provided id
     * @param id of the metawear device to delete
     */
    public void deleteMetawear(String id) {
        metawearDataSource.deleteMetawear(id);
    }
}
