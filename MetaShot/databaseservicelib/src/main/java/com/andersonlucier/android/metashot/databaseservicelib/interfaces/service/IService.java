package com.andersonlucier.android.metashot.databaseservicelib.interfaces.service;

import com.andersonlucier.android.metashot.databaseservicelib.impl.GunRecord;
import com.andersonlucier.android.metashot.databaseservicelib.impl.ShootingRecord;
import com.andersonlucier.android.metashot.databaseservicelib.impl.ShotRecord;

import java.util.List;

/**
 * Created by SyberDeskTop on 3/12/2018.
 */

public interface IService {


    public List<ShotRecord> getShotsRecordById(int id);



}
