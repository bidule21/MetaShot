package com.andersonlucier.android.metashot.databaseservicelib.interfaces.service;

import com.andersonlucier.android.metashot.databaseservicelib.impl.ShootingRecord;

import java.util.List;

/**
 * Created by SyberDeskTop on 4/15/2018.
 */

public interface IShootingService {

    public List<ShootingRecord> getAllShootingRecords();

    public ShootingRecord getSingleShootingRecord(String id);

    public void deleteShootingRecord(String id);

    public ShootingRecord createShootingRecord(ShootingRecord record);
}
