package com.andersonlucier.android.metashot.databaseservicelib.interfaces.shooting;

import java.util.Date;
import java.util.List;

/**
 * Created by SyberDeskTop on 3/12/2018.
 */

public interface IShooting {

    String Id();

    String title();

    Date datetime();

    double lat();

    double lon();

    double temp();

    double windspeed();

    String description();

    IGun typeOfGun();

    List<Integer> listOfShotsId();
}
