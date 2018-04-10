package com.andersonlucier.android.metashot;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
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

public class NewShootingRecord extends AppCompatActivity {

    private EditText recordName, autoGpsLocation, weather, otherDetails;
    private String item;
    AppLocationService appLocationService;
    private static final int GPS_LOCATION_PERMISSION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_shooting_record);
        appLocationService = new AppLocationService(NewShootingRecord.this);

        recordName = findViewById(R.id.recordName);
        autoGpsLocation = findViewById(R.id.gpsManual);
        weather = findViewById(R.id.weatherManual);
        otherDetails = findViewById(R.id.otherDetails);

        Spinner spinner = findViewById(R.id.weaponSelect);
        //TODO: Add code for populating selections from database list
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
            case R.id.autofillGpsLocation:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    Location gpsLocation = appLocationService.getLocation(LocationManager
                            .GPS_PROVIDER);
                    if (gpsLocation != null) {
                        double latitude = gpsLocation.getLatitude();
                        double longitude = gpsLocation.getLongitude();
                        String coordLat = Double.toString(latitude);
                        String coordLong = Double.toString(longitude);
                        autoGpsLocation.setText(String.valueOf(coordLat + ", " + coordLong));
                    } else {
                        showSettingsAlert("GPS");
                    }
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},GPS_LOCATION_PERMISSION);
                }
                break;
            case R.id.newShootingCreate:
                if (recordName.getText().length() == 0) {
                    recordName.setText(R.string.recordNameDefault);
                }
                //TODO: Add code for sending data to database for storage
                Toast.makeText(this, "Record Name: " + recordName.getText().toString() + "\n" +
                        "GPS Location: " + autoGpsLocation.getText().toString() + "\n Weather: " +
                        weather.getText().toString() + "\n Weapon: " + item + "\n Other Details: " + otherDetails.getText().toString(), Toast.LENGTH_LONG).show();
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

    public void showSettingsAlert(String provider){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(NewShootingRecord.this);
        alertDialog.setTitle(provider + " SETTINGS");
        alertDialog.setMessage(provider + " is not enabled! Do you want to enable through Settings?");
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                NewShootingRecord.this.startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case GPS_LOCATION_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Location gpsLocation = appLocationService.getLocation(LocationManager
                            .GPS_PROVIDER);
                    if (gpsLocation != null) {
                        double latitude = gpsLocation.getLatitude();
                        double longitude = gpsLocation.getLongitude();
                        String coordLat = Double.toString(latitude);
                        String coordLong = Double.toString(longitude);
                        autoGpsLocation.setText(String.valueOf(coordLat + ", " + coordLong));
                    } else {
                        showSettingsAlert("GPS");
                    }
                }
        }
    }
}
