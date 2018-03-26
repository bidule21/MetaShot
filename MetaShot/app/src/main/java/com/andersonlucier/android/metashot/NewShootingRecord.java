package com.andersonlucier.android.metashot;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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

public class NewShootingRecord extends AppCompatActivity {

    private EditText recordName;
    private EditText gpsLocation;
    private EditText weather;
    private EditText otherDetails;
    private String item, coordLat, coordLong;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private double latitude;
    private double longitude;
    public static final int GPS_LOCATION_PERMISSIONS = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_shooting_record);

        recordName = findViewById(R.id.recordName);
        gpsLocation = findViewById(R.id.gpsManual);
        weather = findViewById(R.id.weatherManual);
        otherDetails = findViewById(R.id.otherDetails);

        Spinner spinner = findViewById(R.id.weaponSelect);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.weaponSelect, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
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
            /*case R.id.autofillGpsLocation:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                    locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
                    locationListener = new LocationListener() {
                        public void onLocationChanged(Location location) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            coordLat = Double.toString(latitude);
                            coordLong = Double.toString(longitude);
                            gpsLocation.setText(String.valueOf(coordLat + ", " + coordLong));
                        }

                        public void onStatusChanged(String provider, int status, Bundle
                                extras) {
                        }

                        public void onProviderEnabled(String provider) {
                        }

                        public void onProviderDisabled(String provider) {
                        }
                    };
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, GPS_LOCATION_PERMISSIONS);
                }

                break;*/
            case R.id.newShootingCreate:
                if (recordName.getText().length() == 0) {
                    recordName.setText(R.string.recordNameDefault);
                }
                locationManager.removeUpdates(locationListener);
                //need to add code for sending data to database for storage
                Toast.makeText(this, "Record Name: " + recordName.getText().toString() + "\n" +
                        "GPS Location: " + gpsLocation.getText().toString() + "\n Weather: " +
                        weather.getText().toString() + "\n Weapon: " + item + "\n Other Details: " + otherDetails.getText().toString(), Toast.LENGTH_LONG).show();
                startActivity(new Intent(NewShootingRecord.this, NewShotRecord.class));
                break;
            case R.id.newShootingCancel:
                startActivity(new Intent(NewShootingRecord.this, MainActivity.class));
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
                        locationListener = new LocationListener() {
                            public void onLocationChanged(Location location) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                coordLat = Double.toString(latitude);
                                coordLong = Double.toString(longitude);
                                gpsLocation.setText(String.valueOf(coordLat + ", " + coordLong));
                            }

                            public void onStatusChanged(String provider, int status, Bundle
                                    extras) {
                            }

                            public void onProviderEnabled(String provider) {
                            }

                            public void onProviderDisabled(String provider) {
                            }
                        };
                    } else {
                        Toast.makeText(this, "Unable to populate GPS data due to denied permission", Toast.LENGTH_LONG).show();

                    }
                    break;
                }
        }
    }
}
