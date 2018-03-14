package com.andersonlucier.android.servicelib;

import com.andersonlucier.android.servicelib.impl.GunRecord;
import com.andersonlucier.android.servicelib.interfaces.service.IService;
import com.andersonlucier.android.servicelib.impl.ShootingRecord;
import com.andersonlucier.android.servicelib.impl.ShotRecord;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Service implements IService {

    @Override
    public List<ShootingRecord> getShootingRecords() {
        List<ShootingRecord> records = new ArrayList<>();



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
        return null;

    }

    @Override
    public void saveShootingRecord(ShootingRecord record) {

    }

    @Override
    public List<GunRecord> getGunRecords() {
        return null;
    }

    @Override
    public void saveGunRecord(GunRecord record) {

    }
}
