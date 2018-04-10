package com.andersonlucier.android.metashot.databaseservicelib.impl;

import com.andersonlucier.android.metashot.databaseservicelib.interfaces.shooting.IShooting;

import java.util.Date;
import java.util.List;

/**
 * Created by SyberDeskTop on 3/12/2018.
 */

public class ShootingRecord implements IShooting {
    private String _id;

    private String _title;

    private Date _datetime;

    private double _lat;

    private double _lon;

    private double _temp;

    private double _windspeed;

    private String _description;

    private GunRecord _typeOfGun;

    private List<Integer> _listOfShotsId;



    @Override
    public String Id() {
        return _id;
    }
    public void setId(String id) {
        _id = id;
    }

    @Override
    public String title() {
        return _title;
    }
    public void setTitle(String title) {
        _title = title;
    }

    @Override
    public Date datetime() {
        return _datetime;
    }
    public void setDateTime(Date datetime) {
        _datetime = datetime;
    }

    @Override
    public double lat() {
        return _lat;
    }
    public void setLat(double lat) {
        _lat = lat;
    }

    @Override
    public double lon() {
        return _lon;
    }
    public void setLon(double lon) {
        _lon = lon;
    }

    @Override
    public double temp() {
        return _temp;
    }
    public void setTemp(double temp) {
        _temp = temp;
    }

    @Override
    public double windspeed() {
        return _windspeed;
    }
    public void setWindspeed(double windspeed) {
        _windspeed = windspeed;
    }

    @Override
    public String description() {
        return _description;
    }
    public void setDescription(String description) {
        _description = description;
    }

    @Override
    public GunRecord typeOfGun() {
        return _typeOfGun;
    }
    public void setTypeOfGun(GunRecord gun){
        _typeOfGun = gun;
    }

    @Override
    public List<Integer> listOfShotsId() {
        return _listOfShotsId;
    }
    public void setListOfShotsId( List<Integer> ids){
        _listOfShotsId = ids;
    }
}
