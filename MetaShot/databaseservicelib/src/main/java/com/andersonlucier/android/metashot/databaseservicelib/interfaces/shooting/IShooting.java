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

    String location();

    double temp();

    String wind();

    String description();

    IGun typeOfGun();

    String weather();

    List<Integer> listOfShotsId();
}
