package com.andersonlucier.android.servicelib.interfaces.service;

import com.andersonlucier.android.servicelib.impl.ShotRecord;
import com.andersonlucier.android.servicelib.interfaces.shooting.IShooting;

import java.util.List;

/**
 * Created by SyberDeskTop on 3/12/2018.
 */

public interface IService {
    public IShooting getShootingRecordById();

    public List<ShotRecord> getShotsRecordById();
}
