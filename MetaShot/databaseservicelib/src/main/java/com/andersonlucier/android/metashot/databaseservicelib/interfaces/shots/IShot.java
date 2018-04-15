package com.andersonlucier.android.metashot.databaseservicelib.interfaces.shots;

/**
 * Created by SyberDeskTop on 3/12/2018.
 */

public interface IShot {
    String id();

    String shootingRecordId();

    int shotNumber();

    double barrelTemp();

    double targetX();

    double targetY();
}
