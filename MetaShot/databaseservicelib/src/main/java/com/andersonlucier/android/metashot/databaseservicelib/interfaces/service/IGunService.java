package com.andersonlucier.android.metashot.databaseservicelib.interfaces.service;

import com.andersonlucier.android.metashot.databaseservicelib.impl.GunRecord;

import java.util.List;

/**
 * Service interface for the functions that will be used in the gun record database table
 * interaction and the service being called from the application
 */

public interface IGunService {

    List<GunRecord> getAllGunRecords();

    GunRecord createGunRecord(GunRecord record);

    void deleteGunRecord(String id);

    GunRecord getSingleGunRecord(String id);

    GunRecord updateGunRecord(GunRecord record);
}
