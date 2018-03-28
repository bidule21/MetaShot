package com.andersonlucier.android.metashot.databaseservicelib;

import android.content.Context;

import com.andersonlucier.android.metashot.databaseservicelib.impl.GraphRecord;
import com.andersonlucier.android.metashot.databaseservicelib.impl.GraphRecords;
import com.andersonlucier.android.metashot.databaseservicelib.impl.GunRecord;
import com.andersonlucier.android.metashot.databaseservicelib.impl.TargetRecord;
import com.andersonlucier.android.metashot.databaseservicelib.interfaces.service.IService;
import com.andersonlucier.android.metashot.databaseservicelib.impl.ShootingRecord;
import com.andersonlucier.android.metashot.databaseservicelib.impl.ShotRecord;
import com.andersonlucier.android.metashot.datalib.ShootingRecordDataSource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseService implements IService {

    private ShootingRecordDataSource srdatasource;

    public DatabaseService(Context context) {
        srdatasource = new ShootingRecordDataSource(context);
        srdatasource.open();
    }

    @Override
    public List<ShootingRecord> getShootingRecords() {

        GunRecord gunType = new GunRecord();
        gunType.setGunName("winchester");
        gunType.setId(1);

        List<ShootingRecord> records = new ArrayList<>();
        ShootingRecord record = new ShootingRecord();
        Date date = new Date();
        record.setDateTime(date);
        record.setDescription("Test");
        record.setLat(10);
        record.setLon(25);
        record.setTemp(90);
        record.setTypeOfGun(gunType);
        record.setWindspeed(15);
        record.setId(1);
        record.setTitle("This is Sparta");

        ShootingRecord recordTwo = new ShootingRecord();
        recordTwo.setDateTime(date);
        recordTwo.setDescription("TestTwo");
        recordTwo.setLat(10);
        recordTwo.setLon(25);
        recordTwo.setTemp(90);
        recordTwo.setTypeOfGun(gunType);
        recordTwo.setWindspeed(15);
        recordTwo.setId(2);
        recordTwo.setTitle("This is Sparta Two");

        ShootingRecord recordThree = new ShootingRecord();
        recordThree.setDateTime(date);
        recordThree.setDescription("TestThree");
        recordThree.setLat(10);
        recordThree.setLon(25);
        recordThree.setTemp(90);
        recordThree.setTypeOfGun(gunType);
        recordThree.setWindspeed(15);
        recordThree.setId(3);
        recordThree.setTitle("This is Sparta Three");

        records.add(record);
        records.add(recordTwo);
        records.add(recordThree);

        return records;
    }

    @Override
    public ShootingRecord getShootingRecordById(int id) {
        ShootingRecord record = new ShootingRecord();
        Date date = new Date();
        GunRecord gunType = new GunRecord();
        gunType.setGunName("winchester");
        gunType.setId(1);
        record.setDateTime(date);
        record.setDescription("Test");
        record.setLat(10);
        record.setLon(25);
        record.setTemp(90);
        record.setTypeOfGun(gunType);
        record.setWindspeed(15);
        record.setId(1);
        record.setTitle("This is Sparta");
        return record;
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
    public void saveShootingRecord(ShootingRecord record) {

    }

    @Override
    public List<GunRecord> getGunRecords() {
        GunRecord gunType = new GunRecord();
        gunType.setGunName("Des");
        gunType.setId(1);
        gunType.setDetails("awesome Gun");

        GunRecord gunTypeTwo = new GunRecord();
        gunTypeTwo.setGunName("Troy");
        gunTypeTwo.setId(1);
        gunTypeTwo.setDetails("awesome Gun");

        GunRecord gunTypeThree = new GunRecord();
        gunTypeThree.setGunName("Winchester");
        gunTypeThree.setId(1);
        gunTypeThree.setDetails("awesome Gun");

        List<GunRecord> gunList = new ArrayList<>();
        gunList.add(gunType);
        gunList.add(gunTypeTwo);
        gunList.add(gunTypeThree);

        return gunList;
    }

    @Override
    public void saveGunRecord(GunRecord record) {

    }
}
