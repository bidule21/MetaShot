package com.andersonlucier.android.metashot.databaseservicelib.impl;

import com.andersonlucier.android.metashot.databaseservicelib.interfaces.shots.IShot;

/**
 * Created by SyberDeskTop on 3/12/2018.
 */

public class ShotRecord implements IShot {

    private String _id;

    private String _shootingRecordId;

    private double _barrelTemp;

    private int _shotNumber;

    private double _targetX;

    private double _targetY;

    @Override
    public String id() {
        return _id;
    }
    public void setId(String id){
        _id = id;
    }

    @Override
    public String shootingRecordId() {
        return _shootingRecordId;
    }
    public void setShootingRecordId(String _shootingRecordId) {
        this._shootingRecordId = _shootingRecordId;
    }

    @Override
    public int shotNumber() {
        return _shotNumber;
    }
    public void setShotNumber(int _shotNumber) {
        this._shotNumber = _shotNumber;
    }

    @Override
    public double barrelTemp() {
        return _barrelTemp;
    }
    public void setBarrelTemp(double temp){
        _barrelTemp = temp;
    }

    @Override
    public double targetX() {
        return _targetX;
    }
    public void setTargetX(double _targetX) {
        this._targetX = _targetX;
    }

    @Override
    public double targetY() {
        return _targetY;
    }
    public void setTargetY(double _targetY) {
        this._targetY = _targetY;
    }
}
