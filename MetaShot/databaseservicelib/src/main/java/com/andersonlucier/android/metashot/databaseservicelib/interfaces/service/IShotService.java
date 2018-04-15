package com.andersonlucier.android.metashot.databaseservicelib.interfaces.service;

import com.andersonlucier.android.metashot.databaseservicelib.impl.GunRecord;
import com.andersonlucier.android.metashot.databaseservicelib.impl.ShootingRecord;
import com.andersonlucier.android.metashot.databaseservicelib.impl.ShotRecord;

import java.util.List;

/**
 * Created by SyberDeskTop on 3/12/2018.
 */

public interface IShotService {

     List<ShotRecord> getAllShotsRecordsByShootingId(String id);

     ShotRecord getSingleShotsRecordsById(String id);

     ShotRecord createShotRecord(ShotRecord record);

     void deleteShotRecord(String id);

    ShotRecord updateShotRecord(ShotRecord record);

}
