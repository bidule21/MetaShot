package com.andersonlucier.android.metashot.databaseservicelib.impl;

import com.andersonlucier.android.metashot.databaseservicelib.interfaces.metawear.IMetaWear;

/**
 * Created by Glenn on 4/24/2018.
 */

public class MetaWear implements IMetaWear {

    private String _id;

    private String _macAddress;

    @Override
    public String id() {
        return _id;
    }

    public void setId(String id) {
        _id = id;
    }

    @Override
    public String macAddress() {
        return _macAddress;
    }

    public void setMacAddress(String mac) {
        _macAddress = mac;
    }
}
