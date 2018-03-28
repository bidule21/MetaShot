package com.andersonlucier.android.metashot.databaseservicelib.interfaces.service;

import com.andersonlucier.android.metashot.databaseservicelib.impl.GunRecord;
import com.andersonlucier.android.metashot.databaseservicelib.impl.ShootingRecord;
import com.andersonlucier.android.metashot.databaseservicelib.impl.ShotRecord;

import java.util.List;

/**
 * Created by SyberDeskTop on 3/12/2018.
 */

public interface IService {
    public List<ShootingRecord> getShootingRecords();

    public ShootingRecord getShootingRecordById(int id);

    public List<ShotRecord> getShotsRecordById(int id);

    public void saveShootingRecord(ShootingRecord record);

    public List<GunRecord> getGunRecords();

    public void saveGunRecord(GunRecord record);

}
