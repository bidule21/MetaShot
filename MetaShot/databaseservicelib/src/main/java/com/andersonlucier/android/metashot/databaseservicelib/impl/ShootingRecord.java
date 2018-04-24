package com.andersonlucier.android.metashot.databaseservicelib.impl;

import com.andersonlucier.android.metashot.databaseservicelib.interfaces.shooting.IShooting;

import java.util.Date;
import java.util.List;

/**
 * Implementation object for the shooting record that will be passed between all three modules
 */

public class ShootingRecord implements IShooting {
    private String _id;

    private String _title;

    private Date _datetime;

    private String _location;

    private double _temp;

    private String _wind;

    private String _description;

    private GunRecord _typeOfGun;

    private String _weather;

    private String _range;


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
    public String location() {
        return _location;
    }
    public void setLocation(String location) {
        _location = location;
    }

    @Override
    public double temp() {
        return _temp;
    }
    public void setTemp(double temp) {
        _temp = temp;
    }

    @Override
    public String wind() {
        return _wind;
    }
    public void setWind(String windspeed) {
        _wind = windspeed;
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
    public String weather() {
        return _weather;
    }
    public void setWeather(String weather) {
        _weather = weather;
    }

    @Override
    public String gunId() {
        return _typeOfGun.id();
    }
    public void setGunId(String id) {
        _typeOfGun = new GunRecord();
        _typeOfGun.setId(id);
    }

    @Override
    public String range() {return _range;}
    public void setRange(String range) {
        _range = range;
    }
}
