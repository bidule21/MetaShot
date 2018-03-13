package com.andersonlucier.android.servicelib.interfaces.shooting;

import java.util.Date;
import java.util.List;

/**
 * Created by SyberDeskTop on 3/12/2018.
 */

public interface IShooting {

    int Id();

    Date datetime();

    double lat();

    double lon();

    double temp();

    double windspeed();

    String description();

    IGun typeOfGun();

    List<Integer> listOfShotsId();
}
