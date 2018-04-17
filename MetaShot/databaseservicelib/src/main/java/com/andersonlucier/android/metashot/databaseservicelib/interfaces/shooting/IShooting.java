package com.andersonlucier.android.metashot.databaseservicelib.interfaces.shooting;

import java.util.Date;
import java.util.List;

/**
 * Interface object for the shooting record
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

}
