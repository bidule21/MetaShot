package com.andersonlucier.android.metashot.databaseservicelib;

import android.content.Context;

import com.andersonlucier.android.metashot.databaselib.GunRecordDataSource;
import com.andersonlucier.android.metashot.databaseservicelib.impl.GraphRecord;
import com.andersonlucier.android.metashot.databaseservicelib.impl.GraphRecords;
import com.andersonlucier.android.metashot.databaseservicelib.impl.GunRecord;
import com.andersonlucier.android.metashot.databaseservicelib.impl.TargetRecord;
import com.andersonlucier.android.metashot.databaseservicelib.interfaces.service.IGunService;
import com.andersonlucier.android.metashot.databaseservicelib.interfaces.service.IService;
import com.andersonlucier.android.metashot.databaseservicelib.impl.ShootingRecord;
import com.andersonlucier.android.metashot.databaseservicelib.impl.ShotRecord;
import com.andersonlucier.android.metashot.databaselib.ShootingRecordDataSource;
import com.andersonlucier.android.metashot.databaseservicelib.interfaces.service.IShootingService;

import java.util.ArrayList;
import java.util.List;

public class DatabaseService implements IService, IShootingService, IGunService {

    private ShootingRecordDataSource srdatasource;
    private GunRecordDataSource grdatasource;

    public DatabaseService(Context context) {
        srdatasource = new ShootingRecordDataSource(context);
        srdatasource.open();

        grdatasource = new GunRecordDataSource(context);
        grdatasource.open();
    }

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

    @Override
    public List<ShotRecord> getShotsRecordById(int id) {
        GraphRecord graphSingle = new GraphRecord();
        graphSingle.setId(1);
        graphSingle.setX(10);
        graphSingle.setY(20);
        graphSingle.setZ(30);
        GraphRecord graphSingleTwo = new GraphRecord();
        graphSingle.setId(2);
        graphSingle.setX(10);
        graphSingle.setY(20);
        graphSingle.setZ(30);
        GraphRecords graphList = new GraphRecords();
        graphList.addGraph(graphSingle);
        graphList.addGraph(graphSingleTwo);

        TargetRecord target = new TargetRecord();
        target.setId(0);
        target.setX(0);
        target.setY(0);

        ShotRecord shotOne = new ShotRecord();
        shotOne.setBarrelTemp(80);
        shotOne.setGraph(graphList);
        shotOne.setId(0);
        shotOne.setTarget(target);

        ShotRecord shotTwo = new ShotRecord();
        shotTwo.setBarrelTemp(80);
        shotTwo.setGraph(graphList);
        shotTwo.setId(1);
        shotTwo.setTarget(target);

        ShotRecord shotThree = new ShotRecord();
        shotThree.setBarrelTemp(80);
        shotThree.setGraph(graphList);
        shotThree.setId(2);
        shotThree.setTarget(target);

        List<ShotRecord> shotList = new ArrayList<>();
        shotList.add(shotOne);
        shotList.add(shotTwo);
        shotList.add(shotThree);

        return shotList;

    }



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
}
