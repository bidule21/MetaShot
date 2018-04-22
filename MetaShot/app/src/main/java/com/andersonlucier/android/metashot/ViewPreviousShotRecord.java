package com.andersonlucier.android.metashot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.andersonlucier.android.metashot.databaseservicelib.DatabaseShotService;
import com.andersonlucier.android.metashot.databaseservicelib.impl.ShotRecord;

public class ViewPreviousShotRecord extends AppCompatActivity {

    private DatabaseShotService dbService;
    private String shotRecordId;
    private TextView shotNumber, barrelTemp, targetXY;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_previous_shot_record);
        dbService = new DatabaseShotService(this);
        shotRecordId = getIntent().getStringExtra("SHOT_RECORD_ID");

        shotNumber = findViewById(R.id.shotNumber);
        barrelTemp = findViewById(R.id.barrelTemp);
        targetXY = findViewById(R.id.targetXY);


        //populate the shot record
        populateShotRecord();
    }

    public void onClick(View view){
        switch (view.getId()) {
            case R.id.goToHome:
                startActivity(new Intent(ViewPreviousShotRecord.this, MainActivity.class));
                break;
        }
    }

    private void populateShotRecord() {

        String unitsX, distanceX, orientationX, unitsY, distanceY, orientationY;
        String messageX = null;
        String  messageY = null;
        //get the shot record
        ShotRecord record = dbService.getSingleShotsRecordsById(shotRecordId);

        //populate the shot number
        shotNumber.setText(String.format("%s", record.shotNumber()));

        //populate the barrel temp
        barrelTemp.setText(String.format("%s", record.barrelTemp()));

        /*Populate the X and Y distance from center of target.
        A negative X value indicates a shot that was to the left of the target center.
        A positive X value indicates a shot that was to the right of the target center.
        A negative Y value indicates a shot that was below target center.
        A positive Y value indicates a shot that was above target center.
        If both X and Y equal 0, then a bullseye was hit.
        If either X or Y are equal to 0, that indicates horizontal/vertical alignment.
        */
        //shot resulted in a bullseye
        if (record.targetX() == 0 && record.targetY() == 0){
            targetXY.setText(R.string.bullseye);
            return;
        }

        //set display text for units
        if(record.targetX() > 0) {
            if (record.targetX() == 1) {
                unitsX = " inch";
            } else {
                unitsX = " inches";
            }
            distanceX = (String.format("%s", record.targetX())) + unitsX; //set X distance
            orientationX = " to the right."; //set X orientation
            messageX = distanceX + orientationX; //set X message
        } else if (record.targetX() < 0 ){
            //set display text for units
            if (record.targetX() == -1) {
                unitsX = " inch";
            } else {
                unitsX = " inches";
            }
            distanceX = (String.format("%s", -(record.targetX()))) + unitsX; //set X distance
            orientationX = " to the left."; //set X orientation
            messageX = distanceX + orientationX; //set X message
        } else if (record.targetX() == 0) {
         messageX = "Centered horizontally."; //set X message
        }

        if(record.targetY() > 0) {
            //set display text for units
            if (record.targetY() == 1) {
                unitsY = " inch";
            } else {
                unitsY = " inches";
            }
            distanceY = (String.format("%s", record.targetY())) + unitsY; //set Y distance
            orientationY = " above."; //set Y orientation
            messageY = distanceY + orientationY; //set Y message
        } else if (record.targetY() < 0 ){
            if (record.targetY() == -1) {
                //set display text for units
                unitsY = " inch";
            } else {
                unitsY = " inches";
            }
            distanceY = (String.format("%s", -(record.targetY()))) + unitsY; //set Y distance
            orientationY = " below."; //set Y orientation
            messageY = distanceY + orientationY; //set Y message
        } else if (record.targetY() == 0) {
            messageY = "Centered vertically."; //set Y message
        }
        //set targetXY text to include messageX and messageY
        //formatting of string done in strings.xml resource file
        targetXY.setText(getString(R.string.showXYDist, messageX, messageY));
    }

}
