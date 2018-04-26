package com.andersonlucier.android.metashot.databaseservicelib.interfaces.service;

import com.andersonlucier.android.metashot.databaseservicelib.impl.MetaWear;

import java.util.List;

/**
 * Created by Glenn on 4/24/2018.
 */

public interface IMetaWearService {

    List<MetaWear> getMetawear();

    MetaWear updateMetawear(MetaWear mac);

    MetaWear createMetawear(MetaWear mac);

    void deleteMetawear(String id);

}
