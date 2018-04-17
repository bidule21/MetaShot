package com.andersonlucier.android.metashot.databaseservicelib.interfaces.shots;

/**
 * Interface object for the shot record
 */

public interface IShot {
    String id();

    String shootingRecordId();

    int shotNumber();

    double barrelTemp();

    double targetX();

    double targetY();
}
