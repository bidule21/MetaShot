package com.andersonlucier.android.metashot.databaseservicelib.interfaces.shots;

/**
 * Created by SyberDeskTop on 3/12/2018.
 */

public interface IShot {
    int id();

    IGraph graph();

    double barrelTemp();

    ITarget target();
}
