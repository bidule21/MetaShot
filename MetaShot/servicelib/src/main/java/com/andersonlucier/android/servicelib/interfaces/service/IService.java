package com.andersonlucier.android.servicelib.interfaces.service;

import com.andersonlucier.android.servicelib.impl.GunRecord;
import com.andersonlucier.android.servicelib.impl.ShootingRecord;
import com.andersonlucier.android.servicelib.impl.ShotRecord;
import com.andersonlucier.android.servicelib.interfaces.shooting.IShooting;

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
