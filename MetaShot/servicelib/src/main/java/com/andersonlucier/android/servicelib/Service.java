package com.andersonlucier.android.servicelib;

import com.andersonlucier.android.servicelib.interfaces.service.IService;
import com.andersonlucier.android.servicelib.impl.ShootingRecord;
import com.andersonlucier.android.servicelib.impl.ShotRecord;

import java.util.List;

public class Service implements IService {

    @Override
    public ShootingRecord getShootingRecordById() {
        ShootingRecord record = new ShootingRecord();
        record.setDescription("Test");
        return record;
    }

    @Override
    public List<ShotRecord> getShotsRecordById() {
        return null;
    }
}
