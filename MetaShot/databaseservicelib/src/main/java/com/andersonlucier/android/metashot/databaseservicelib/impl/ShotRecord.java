package com.andersonlucier.android.metashot.databaseservicelib.impl;

import com.andersonlucier.android.metashot.databaseservicelib.interfaces.shots.IShot;

/**
 * Created by SyberDeskTop on 3/12/2018.
 */

public class ShotRecord implements IShot {

    private int _id;

    private GraphRecords _graph;

    private double _barrelTemp;

    private TargetRecord _target;

    @Override
    public int id() {
        return _id;
    }
    public void setId(int id){
        _id = id;
    }

    @Override
    public GraphRecords graph() {
        return _graph;
    }

    public void setGraph(GraphRecords graph){
        _graph = graph;
    }

    @Override
    public double barrelTemp() {
        return _barrelTemp;
    }
    public void setBarrelTemp(double temp){
        _barrelTemp = temp;
    }

    @Override
    public TargetRecord target() {
        return _target;
    }
    public void setTarget(TargetRecord target) {
        _target = target;
    }
}
