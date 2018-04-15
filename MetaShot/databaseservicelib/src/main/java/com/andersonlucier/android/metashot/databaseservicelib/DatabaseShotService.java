package com.andersonlucier.android.metashot.databaseservicelib;

import android.content.Context;

import com.andersonlucier.android.metashot.databaselib.GunRecordDataSource;
import com.andersonlucier.android.metashot.databaseservicelib.impl.GunRecord;
import com.andersonlucier.android.metashot.databaseservicelib.interfaces.service.IGunService;
import com.andersonlucier.android.metashot.databaseservicelib.interfaces.service.IShotService;
import com.andersonlucier.android.metashot.databaseservicelib.impl.ShootingRecord;
import com.andersonlucier.android.metashot.databaseservicelib.impl.ShotRecord;
import com.andersonlucier.android.metashot.databaselib.ShootingRecordDataSource;
import com.andersonlucier.android.metashot.databaseservicelib.interfaces.service.IShootingService;

import java.util.List;

public class DatabaseShotService implements IShotService, IShootingService, IGunService {

    private ShootingRecordDataSource srdatasource;
    private GunRecordDataSource grdatasource;

    public DatabaseShotService(Context context) {
        srdatasource = new ShootingRecordDataSource(context);
        srdatasource.open();

        grdatasource = new GunRecordDataSource(context);
        grdatasource.open();
    }

    //Shooting Record Service
    @Override
    public List<ShootingRecord> getAllShootingRecords() {
        List<ShootingRecord> returnValue = srdatasource.getAllShootingRecords();
        return returnValue;

    }

    @Override
    public ShootingRecord getSingleShootingRecord(String id) {
        ShootingRecord returnValue = srdatasource.getSingleShootingRecord(id);
        return returnValue;
    }

    @Override
    public void deleteShootingRecord(String id) {
        srdatasource.deleteShootingRecord(id);
    }

    @Override
    public ShootingRecord createShootingRecord(ShootingRecord record) {
        ShootingRecord returnValue = null;
        returnValue = srdatasource.createShootingRecord(record);
        return returnValue;
    }


    //Gun Record service
    @Override
    public List<GunRecord> getGunRecords() {
        List<GunRecord> gunList =grdatasource.getAllGunRecords();
        return gunList;
    }

    @Override
    public GunRecord createGunRecord(GunRecord record) {
        GunRecord result = grdatasource.createGunRecord(record);
        return result;
    }

    @Override
    public void deleteGunRecord(String id) {
        grdatasource.deleteGunRecord(id);
    }

    @Override
    public GunRecord getSingleGunRecord(String id) {
        GunRecord result = grdatasource.getSingleGunRecord(id);
        return result;
    }

    //Shot Records Service
    @Override
    public List<ShotRecord> getAllShotsRecordsByShootingId(String id) {
        return null;
    }

    @Override
    public ShotRecord getSingleShotsRecordsById(String id) {
        return null;
    }

    @Override
    public ShotRecord createShotRecord(ShotRecord record) {
        return null;
    }

    @Override
    public void deleteShotRecord(String id) {

    }

    @Override
    public ShotRecord updateShotRecord(ShotRecord record) {
        return null;
    }
}
