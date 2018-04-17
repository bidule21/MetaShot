package com.andersonlucier.android.metashot.databaseservicelib.impl;

import com.andersonlucier.android.metashot.databaseservicelib.interfaces.shooting.IGun;

/**
 * Implementation object for the gun record that will be passed between all three modules
 */

public class GunRecord implements IGun {

    private String _id;

    private String _gunName;

    private String _details;

    @Override
    public String id() {
        return _id;
    }
    public void setId(String id) {
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
