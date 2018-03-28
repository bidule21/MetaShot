package com.andersonlucier.android.metashot.databaseservicelib.impl;

import com.andersonlucier.android.metashot.databaseservicelib.interfaces.shots.IGraphRecord;

/**
 * Created by SyberDeskTop on 3/12/2018.
 */

public class GraphRecord implements IGraphRecord {

    private int _id;

    private int _x;

    private int _y;

    private int _z;

    @Override
    public int id() {
        return _id;
    }
    public void setId(int id){
        _id = id;
    }

    @Override
    public int x() {
        return _x;
    }
    public void setX(int x) {
        _x = x;
    }

    @Override
    public int y() {
        return _y;
    }
    public void setY (int y) {
        _y = y;
    }

    @Override
    public int z() {
        return _z;
    }
    public void setZ (int z) {
        _z = z;
    }
}
