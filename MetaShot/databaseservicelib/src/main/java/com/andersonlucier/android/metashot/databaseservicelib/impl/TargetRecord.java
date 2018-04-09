package com.andersonlucier.android.metashot.databaseservicelib.impl;

import com.andersonlucier.android.metashot.databaseservicelib.interfaces.shots.ITarget;

/**
 * Created by SyberDeskTop on 3/12/2018.
 */

public class TargetRecord implements ITarget {

    private int _id;

    private double _x;

    private double _y;

    @Override
    public int id() {
        return _id;
    }
    public void setId( int id) {
        _id = id;
    }

    @Override
    public double x() {
        return _x;
    }
    public void setX(double x){
        _x = x;
    }

    @Override
    public double y() {
        return _y;
    }
    public void setY(double y){
        _y = y;
    }
}
