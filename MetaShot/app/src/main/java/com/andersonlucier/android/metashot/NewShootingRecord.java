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

    private EditText recordName, autoGpsLocation, weather, otherDetails;
    private String item, windFactors;
    private Double autofillTemperature;
    AppLocationService appLocationService;
    private static final int GPS_LOCATION_PERMISSION = 0;
    private static final int INTERNET_PERMISSION = 34;
    private DatabaseShotService dbService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbService = new DatabaseShotService(this);

        setContentView(R.layout.new_shooting_record);
        appLocationService = new AppLocationService(NewShootingRecord.this);

        recordName = findViewById(R.id.recordName);
        autoGpsLocation = findViewById(R.id.gpsManual);
        weather = findViewById(R.id.weatherManual);
        otherDetails = findViewById(R.id.otherDetails);

        Spinner spinner = findViewById(R.id.weaponSelect);
        dbService = new DatabaseShotService(this);
        List<GunRecord> records = dbService.getAllGunRecords();
        List<String> list = new ArrayList<>();

        list.add("No weapon selected.");
        for (GunRecord gun : records) {
            list.add(gun.gunName());
        }

        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adp1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    item = "No weapon selected.";
                } else {
                    item = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                item = "No weapon selected.";
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.gpsAuto:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    Location gpsLocation = appLocationService.getLocation(LocationManager.GPS_PROVIDER);

                    if (gpsLocation != null) {
                        getGpsLocation(gpsLocation);
                    } else {
                        showSettingsAlert("GPS");
                    }

                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, GPS_LOCATION_PERMISSION);
                }
                break;

            case R.id.weatherAuto:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    Location gpsLocation = appLocationService.getLocation(LocationManager.GPS_PROVIDER);

                    if (gpsLocation == null) {
                        showSettingsAlert("GPS");
                    } else {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {


                            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                            if (connectivityManager != null) {
                                if (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting()) {
                                    getWeatherDetails(gpsLocation);
                                } else {
                                    showSettingsAlert("INTERNET");
                                }
                            } else {
                                Toast.makeText(this, "No network connection detected.", Toast
                                        .LENGTH_LONG).show();
                            }
                        } else {
                            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, INTERNET_PERMISSION);
                        }
                    }
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, GPS_LOCATION_PERMISSION);
                }
                break;

            case R.id.newShootingCreate:
                if (recordName.getText().length() == 0) {
                    recordName.setText(R.string.recordNameDefault);
                }

                ShootingRecord shooting = new ShootingRecord();
                shooting.setTitle(recordName.getText().toString());
                shooting.setLocation(autoGpsLocation.getText().toString());
                shooting.setTemp(autofillTemperature);
                shooting.setWind(windFactors);
                shooting.setDescription(otherDetails.getText().toString());
                shooting.setWeather(weather.getText().toString());
                dbService.createShootingRecord(shooting);

                Toast.makeText(this, "Record Name: " + recordName.getText().toString() + "\n" +
                        "GPS Location: " + autoGpsLocation.getText().toString() + "\n Weather: " +
                        weather.getText().toString() + "\n Other Details: " + otherDetails.getText().toString(), Toast.LENGTH_LONG).show();
                startActivity(new Intent(NewShootingRecord.this, NewShotRecord.class));
                break;

            case R.id.newShootingCancel:
                startActivity(new Intent(NewShootingRecord.this, MainActivity.class));
                break;

            case R.id.goToHome:
                startActivity(new Intent(NewShootingRecord.this, MainActivity.class));
                break;
        }
    }

    public void showSettingsAlert(String provider) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(NewShootingRecord.this);
        alertDialog.setTitle(provider + " SETTINGS");
        alertDialog.setMessage(provider + " is not enabled! Do you want to enable through Settings?");
        switch (provider) {
            case "GPS":
                alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        NewShootingRecord.this.startActivity(intent);
                    }
                });
                break;
            case "INTERNET":
                alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS);
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

    public void getGpsLocation(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        String coordLat = String.format(Locale.getDefault(), "%.2f", latitude);
        String coordLong = String.format(Locale.getDefault(), "%.2f", longitude);
        autoGpsLocation.setText(String.valueOf(coordLat + ", " + coordLong));
    }

    public void getWeatherDetails(Location location) {
        String lat = Double.toString(location.getLatitude());
        String longi = Double.toString(location.getLongitude());

        String urlLatLong = "https://www.metaweather.com/api/location/search/?lattlong=" + lat + "," + longi;
        RequestQueue queue = Volley.newRequestQueue(this);

        //get woeid
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

    private void weatherRequest(String WOEID) {
        RequestQueue queue = Volley.newRequestQueue(this);

        String urlWOEID = "https://www.metaweather.com/api/location/" + WOEID;

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

                            weatherDetails[0] = json2.getString("the_temp");
                            weatherDetails[1] = json2.getString("wind_speed");
                            weatherDetails[2] = json2.getString("wind_direction_compass");

                            tempCelcius = Double.parseDouble(weatherDetails[0]);
                            tempFahrenheit = ((tempCelcius * 9) / 5) + 32;
                            temperature = String.format(Locale.getDefault(), "%.2f", tempFahrenheit);

                            windSpeed = Double.parseDouble(weatherDetails[1]);
                            roundedWindSpeed = String.format(Locale.getDefault(), "%.2f", windSpeed);

                            autofillTemperature = tempFahrenheit;
                            windFactors = weatherDetails[1] + " " + weatherDetails[2];

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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case GPS_LOCATION_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Location gpsLocation = appLocationService.getLocation(LocationManager
                            .GPS_PROVIDER);
                    if (gpsLocation != null) {
                        getGpsLocation(gpsLocation);
                    } else {
                        showSettingsAlert("GPS");
                    }
                } else {
                    Toast.makeText(this, "GPS Permission denied. Unable to complete request.", Toast.LENGTH_LONG).show();
                }
                break;
            case INTERNET_PERMISSION:
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
                            Toast.makeText(this, "Internet permission denied. Unable to complete request.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                break;
        }
    }
}
