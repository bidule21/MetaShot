package com.andersonlucier.android.servicelib.impl;

import com.andersonlucier.android.servicelib.interfaces.shooting.IGun;

/**
 * Created by SyberDeskTop on 3/12/2018.
 */

public class GunRecord implements IGun {

    private int _id;

    private String _gunName;

    private String _details;

    @Override
    public int id() {
        return _id;
    }
    public void setId(int id) {
        _id = id;
    }

    @Override
    public String details() {
        return _details;
    }
    public void setDetails(String details){
        _details = details;
    }

    @Override
    public String gunName() {
        return _gunName;
    }

    public void setGunName(String gunName) {
        _gunName = gunName;
    }
}
