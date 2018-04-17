package com.andersonlucier.android.metashot.databaseservicelib.interfaces.service;

import com.andersonlucier.android.metashot.databaseservicelib.impl.ShotRecord;

import java.util.List;

/**
 * Service interface for the functions that will be used in the shot record database table
 * interaction and the service being called from the application
 */

public interface IShotService {

     List<ShotRecord> getAllShotsRecordsByShootingId(String id);

     ShotRecord getSingleShotsRecordsById(String id);

     ShotRecord createShotRecord(ShotRecord record);

     void deleteShotRecord(String id);

    ShotRecord updateShotRecord(ShotRecord record);

}
