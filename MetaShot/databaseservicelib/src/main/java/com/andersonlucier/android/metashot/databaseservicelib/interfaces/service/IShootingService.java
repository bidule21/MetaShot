package com.andersonlucier.android.metashot.databaseservicelib.interfaces.service;

import com.andersonlucier.android.metashot.databaseservicelib.impl.ShootingRecord;

import java.util.List;

/**
 * Service interface for the functions that will be used in the shooting record database table
 * interaction and the service being called from the application
 */

public interface IShootingService {

    List<ShootingRecord> getAllShootingRecords();

    ShootingRecord getSingleShootingRecord(String id);

    void deleteShootingRecord(String id);

    ShootingRecord createShootingRecord(ShootingRecord record);
}
