package com.andersonlucier.android.metashot;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.andersonlucier.android.metashot.databaseservicelib.DatabaseShotService;
import com.andersonlucier.android.metashot.databaseservicelib.impl.GunRecord;
import com.andersonlucier.android.metashot.databaseservicelib.impl.ShootingRecord;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NewShootingRecord extends AppCompatActivity {

    private EditText recordName, rangeDist, autoGpsLocation, weather, otherDetails;
    private int itemPosition;
    private String windFactors;
    private Double autofillTemperature;
    AppLocationService appLocationService;
    private static final int GPS_LOCATION_PERMISSION = 0;
    private static final int INTERNET_PERMISSION = 34;
    private DatabaseShotService dbService;
    private List<GunRecord> gunRecordsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbService = new DatabaseShotService(this);

        setContentView(R.layout.new_shooting_record);
        appLocationService = new AppLocationService(NewShootingRecord.this);

        recordName = findViewById(R.id.recordName);
        rangeDist = findViewById(R.id.rangeDist);
        autoGpsLocation = findViewById(R.id.gpsManual);
        weather = findViewById(R.id.weatherManual);
        otherDetails = findViewById(R.id.otherDetails);

        Spinner spinner = findViewById(R.id.weaponSelect);
        dbService = new DatabaseShotService(this);
        gunRecordsList = dbService.getAllGunRecords();
        List<String> list = new ArrayList<>();

        list.add("No weapon selected.");
        for (GunRecord gun : gunRecordsList) {
            list.add(gun.gunName());
        }

        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adp1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    itemPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                itemPosition = 0;
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {

            //When clicked, the user's current GPS position is populated as "latitude, longitude"
            case R.id.gpsAuto:
                //check if permission for ACCESS_FINE_LOCATION is granted
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    //if permission is granted, call appLocationService to get current location
                    Location gpsLocation = appLocationService.getLocation(LocationManager.GPS_PROVIDER);

                    //check if GPS is available/enabled
                    if (gpsLocation != null) {
                        getGpsLocation(gpsLocation);
                    } else {
                        //direct user to enable GPS
                        showSettingsAlert("GPS");
                    }

                } else {
                    //if ACCESS_FINE_LOCATION permission is not granted, request permission
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, GPS_LOCATION_PERMISSION);
                }
                break;

            //When clicked, the weather at the user's current location is returned as "temperature (fahrenheit), wind speed (mph) wind direction (compass point)"
            case R.id.weatherAuto:
                //check if permission for ACCESS_FINE_LOCATION is granted
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    //if permission is granted, call appLocationService to get current location
                    Location gpsLocation = appLocationService.getLocation(LocationManager.GPS_PROVIDER);

                    if (gpsLocation == null) {
                        showSettingsAlert("GPS");
                    } else {
                        //check if permission for INTERNET is granted
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {

                            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                            //check if network connectivity service is available/enabled
                            if (connectivityManager != null) {
                                //if network connectivity service is available, check if network connectivity is active and is connected/connecting
                                if (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting()) {
                                    getWeatherDetails(gpsLocation);
                                } else {
                                    //direct user to enable some type of network connection
                                    showSettingsAlert("INTERNET");
                                }
                            } else {
                                //default error message if no network connection service is enabled
                                Toast.makeText(this, "No network connection detected.", Toast
                                        .LENGTH_LONG).show();
                            }
                        } else {
                            //if INTERNET permission is not granted, request permission
                            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, INTERNET_PERMISSION);
                        }
                    }
                } else {
                    //if ACCESS_FINE_LOCATION permission is not granted, request permission
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, GPS_LOCATION_PERMISSION);
                }
                break;

            //When clicked, data from the edit text fields is sent to the database and saved as a unique shooting record
            case R.id.newShootingCreate:
                //Set default name for record if field is blank
                if (recordName.getText().length() == 0) {
                    recordName.setText(R.string.recordNameDefault);
                }

                //TODO: Handle instance of user manually entering text strings for location and weather
                //TODO: Handle instances of empty input fields
                ShootingRecord shooting = new ShootingRecord();
                shooting.setTitle(recordName.getText().toString());
                shooting.setRange(rangeDist.getText().toString());
                shooting.setLocation(autoGpsLocation.getText().toString());
                shooting.setTemp(autofillTemperature);
                shooting.setWind(windFactors);
                shooting.setDescription(otherDetails.getText().toString());
                shooting.setWeather(weather.getText().toString());

                if (itemPosition > 0) {
                    shooting.setTypeOfGun(gunRecordsList.get(itemPosition -1));
                }

                shooting.setId(dbService.createShootingRecord(shooting).Id());

                Toast.makeText(this, "Record Name: " + recordName.getText().toString() + "\n Range: " + rangeDist.getText().toString() + "\n GPS Location: " + autoGpsLocation.getText().toString() + "\n Weather: " +
                        weather.getText().toString() + "\n Other Details: " + otherDetails.getText().toString(), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(new Intent(NewShootingRecord.this, NewShotRecord.class));
                intent.putExtra("SHOOTING_RECORD_ID", shooting.Id());
                intent.putExtra("SHOOTING_TITLE", shooting.title());
                startActivity(intent);

                break;

            //When clicked, no record is created. User is redirected to the landing page.
            case R.id.newShootingCancel:
                startActivity(new Intent(NewShootingRecord.this, MainActivity.class));
                break;

            //When clicked, user is redirected to main landing page
            case R.id.goToHome:
                startActivity(new Intent(NewShootingRecord.this, MainActivity.class));
                break;
        }
    }

    //If a provider is disabled, redirect user to settings screen for enabling provider
    public void showSettingsAlert(String provider) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(NewShootingRecord.this);
        alertDialog.setTitle(provider + " SETTINGS");
        alertDialog.setMessage(provider + " is not enabled! Do you want to enable through Settings?");
        switch (provider) {
            case "GPS":
                //enable GPS
                alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        NewShootingRecord.this.startActivity(intent);
                    }
                });
                break;
            case "INTERNET":
                //enable a network connection
                alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                        NewShootingRecord.this.startActivity(intent);
                    }
                });
                break;
        }
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    //Retrieve latitude and longitude coordinates of user's current location
    public void getGpsLocation(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        //Convert latitude and longitude to a string rounded to two decimal points
        String coordLat = String.format(Locale.getDefault(), "%.2f", latitude);
        String coordLong = String.format(Locale.getDefault(), "%.2f", longitude);
        //Set text for autoGpsLocation EditText field
        autoGpsLocation.setText(String.valueOf(coordLat + ", " + coordLong));
    }

    //Retrieve weather details at user's current location
    public void getWeatherDetails(Location location) {

        String lat = Double.toString(location.getLatitude());
        String longi = Double.toString(location.getLongitude());

        String urlLatLong = "https://www.metaweather.com/api/location/search/?lattlong=" + lat + "," + longi; //API call url
        RequestQueue queue = Volley.newRequestQueue(this);

        //Retrieve woeid (needed for retrieving consolidated weather details from MetaWeather
        StringRequest woeidStringRequest = new StringRequest(Request.Method.GET, urlLatLong,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            System.out.println("Here");
                            JSONArray jsonArr = new JSONArray(response);
                            JSONObject jsonObj = jsonArr.getJSONObject(0);
                            weatherRequest(jsonObj.getString("woeid"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Lat/Long url request error");
            }
        });
        queue.add(woeidStringRequest);
    }

    //Retrieve weather details
    private void weatherRequest(String WOEID) {
        RequestQueue queue = Volley.newRequestQueue(this);

        String urlWOEID = "https://www.metaweather.com/api/location/" + WOEID; //API call url

        StringRequest weatherStringRequest = new StringRequest(Request.Method.GET, urlWOEID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        double tempFahrenheit, tempCelcius, windSpeed;
                        String temperature, roundedWindSpeed;
                        final String[] weatherDetails = new String[3];

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray json1 = new JSONArray(jsonObj.getString("consolidated_weather"));
                            JSONObject json2 = json1.getJSONObject(0);

                            //Retrieve desired variables from JSON response
                            weatherDetails[0] = json2.getString("the_temp"); //temperature in centigrade
                            weatherDetails[1] = json2.getString("wind_speed"); //wind speed in mph
                            weatherDetails[2] = json2.getString("wind_direction_compass"); //wind direction as a compass point

                            //Convert temperature to fahrenheit
                            tempCelcius = Double.parseDouble(weatherDetails[0]);
                            tempFahrenheit = ((tempCelcius * 9) / 5) + 32;
                            temperature = String.format(Locale.getDefault(), "%.2f", tempFahrenheit);

                            //Round wind speed to two decimal places
                            windSpeed = Double.parseDouble(weatherDetails[1]);
                            roundedWindSpeed = String.format(Locale.getDefault(), "%.2f", windSpeed);

                            //Raw data to be saved to database for analysis
                            autofillTemperature = tempFahrenheit;
                            windFactors = weatherDetails[1] + " " + weatherDetails[2];

                            //Set text for weather EditText field
                            weather.setText(String.valueOf(temperature + (char) 0x00B0 + ", " + roundedWindSpeed + " mph " + weatherDetails[2]));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("woeid url request error");
            }
        });
        queue.add(weatherStringRequest);
    }

    //Handle responses to permissions requests
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case GPS_LOCATION_PERMISSION:
                //Complete action if permission is granted
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Location gpsLocation = appLocationService.getLocation(LocationManager
                            .GPS_PROVIDER);
                    if (gpsLocation != null) {
                        getGpsLocation(gpsLocation);
                    } else {
                        showSettingsAlert("GPS");
                    }
                } else {
                    //Display message to user if permission is denied
                    Toast.makeText(this, "GPS permission denied. Unable to complete request.", Toast.LENGTH_LONG).show();
                }
                break;
            case INTERNET_PERMISSION:
                //Complete action if permission is granted
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Location gpsLocation = appLocationService.getLocation(LocationManager.GPS_PROVIDER);
                    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                    if (gpsLocation == null){
                        showSettingsAlert("GPS");
                    } else {
                        if (connectivityManager != null) {
                            if (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting()) {

                                getWeatherDetails(gpsLocation);
                            } else {
                                showSettingsAlert("INTERNET");
                            }
                        } else {
                            //Display message to user if permission is denied
                            Toast.makeText(this, "Internet permission denied. Unable to complete request.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                break;
        }
    }
}
