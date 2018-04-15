package com.andersonlucier.android.metashot.databaseservicelib.interfaces.service;

import com.andersonlucier.android.metashot.databaseservicelib.impl.GunRecord;

import java.util.List;

/**
 * Created by SyberDeskTop on 4/15/2018.
 */

public interface IGunService {

    List<GunRecord> getGunRecords();

    GunRecord createGunRecord(GunRecord record);

    void deleteGunRecord(String id);

    GunRecord getSingleGunRecord(String id);
}
