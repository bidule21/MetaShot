package com.andersonlucier.android.metashot;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.andersonlucier.android.metashot.databaseservicelib.DatabaseShotService;
import com.andersonlucier.android.metashot.databaseservicelib.impl.ShootingRecord;
import com.andersonlucier.android.metashot.databaseservicelib.impl.ShotRecord;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ViewPreviousShootingRecords_Single extends AppCompatActivity {

    private DatabaseShotService dbService;
    private List<ShotRecord> records;
    private ShootingRecord shootingRecord;
    private String shootingRecordId;
    private TextView title, date, range, weaponType, location, weather, description;
    private int shotCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_previous_shooting_records_single);
        dbService = new DatabaseShotService(this);
        shootingRecordId = getIntent().getStringExtra("RECORD_ID");

        title = findViewById(R.id.title);
        date = findViewById(R.id.date);
        range = findViewById(R.id.range);
        weaponType = findViewById(R.id.weaponType);
        location = findViewById(R.id.location);
        weather = findViewById(R.id.weather);
        description = findViewById(R.id.description);

        //populate the shooting information and shot records
        populateShootingInformation();
        populateShotRecords();
    }

    public void onClick(View view){
        switch (view.getId()) {
            case R.id.goToHome:
                startActivity(new Intent(ViewPreviousShootingRecords_Single.this, MainActivity.class));
                break;
            case R.id.addShooting:
                //redirect the user to the new shot record layout to add more shots.
                Intent intent = new Intent(new Intent(ViewPreviousShootingRecords_Single.this, NewShotRecord.class));
                intent.putExtra("SHOOTING_RECORD_ID", shootingRecordId);
                intent.putExtra("SHOOTING_TITLE", shootingRecord.title());
                startActivity(intent);
                break;
            /*Analyze recent shots and recommend scope adjustments. Recommendations are not
            provided until 5 shots have been taken. This includes at least 5 shots before the
            first analysis can occur and at least 5 shots since the last time the analysis
            button was pressed. lastShotAnalyzed is used as a flag to indicate what array
            index additional analyses should begin with.*/
            case R.id.analyze:
                //Verify at least 5 shots have been taken.
                if (shotCounter < 5){
                    //Notify user if number of shots is insufficient.
                    showInsufDialog();
                    break;
                } else {
                    int startIndex;
                    //If this is the first instance of the user pressing the button.
                    if (shootingRecord.lastShotAnalyzed() == 0){
                        startIndex = 0;
                    /*Analysis has been previously conducted. This analysis will begin with the shot that
                    was recorded after the last successful analysis.*/
                    } else {
                        startIndex = shootingRecord.lastShotAnalyzed();
                    }
                    //Provide scope adjustment recommendations.
                    adjustScope(startIndex, shotCounter);
                    break;
                }
        }
    }

    private void populateShootingInformation() {
        //gets the record from the db
        shootingRecord = dbService.getSingleShootingRecord(shootingRecordId);

        //sets the title
        title.setText(String.format("%s", shootingRecord.title()));

        //formats and sets the date
        String pattern = "MMM dd, yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String dateformat = simpleDateFormat.format(shootingRecord.datetime());
        date.setText(String.format("%s", dateformat));

        //sets the range
        range.setText(String.format("%s", shootingRecord.range()));

        //sets the location
        location.setText(String.format("%s", shootingRecord.location()));

        //sets the weather
        weather.setText(String.format("%s", shootingRecord.weather()));

        //sets the description
        description.setText(String.format("%s", shootingRecord.description()));

        //sets the gun
        if(shootingRecord.typeOfGun() != null) {
            weaponType.setText(String.format("%s", shootingRecord.typeOfGun().gunName()));
        } else {
            weaponType.setText(R.string.noWeapon);
        }


    }

    private void populateShotRecords() {
        ListView lv = findViewById(R.id.previousShotRecordsList);

        //gets all the shots for the shooting record
        records = dbService.getAllShotsRecordsByShootingId(shootingRecordId);

        //if records are empty, no need to setup the list
        if (records.isEmpty()) {
            return;
        }

        //populates a list of strings for the list
        List<String> gunArrayList = new ArrayList<>();
        for (ShotRecord record : records){
            gunArrayList.add("Shot - " + record.shotNumber());
            shotCounter++;
        }

        //creates the adapter and sets the adapter
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                gunArrayList );
        lv.setAdapter(arrayAdapter);

        //sets the on item click to redirect to the view previous shot record view
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                showShotRecord(position);

            }
        });
    }

    public void showShotRecord(int position){
        String shotNumber, barrelTemp, targetXY, messageX = null, messageY = null;
        dbService = new DatabaseShotService(this);
        String shotRecordId = records.get(position).id();
        String unitsX, distanceX, orientationX, unitsY, distanceY, orientationY;
        String shotNoLabel = getString(R.string.shotNumber);
        String barrelTempLabel = getString(R.string.barrelTemp);
        String targetXYLabel = getString(R.string.xyDist);

        //get the shot record
        ShotRecord shotRecord = dbService.getSingleShotsRecordsById(shotRecordId);

        //populate the shot number
        shotNumber = String.format("%s", shotRecord.shotNumber());

        //populate the barrel temp
        barrelTemp = String.format(Locale.getDefault(), "%.02f", shotRecord.barrelTemp());

        /*Populate the X and Y distance from center of target.
        A negative X value indicates a shot that was to the left of the target center.
        A positive X value indicates a shot that was to the right of the target center.
        A negative Y value indicates a shot that was below target center.
        A positive Y value indicates a shot that was above target center.
        If both X and Y equal 0, then a bullseye was hit.
        If either X or Y are equal to 0, that indicates horizontal/vertical alignment.
        */
        //shot resulted in a bullseye
        if (shotRecord.targetX() == 0 && shotRecord.targetY() == 0){
            targetXY = getString(R.string.bullseye);
            AlertDialog.Builder showShotRecordDialog = new AlertDialog.Builder(ViewPreviousShootingRecords_Single.this);
            showShotRecordDialog.setTitle(getString(R.string.showShotRecordTitle, shotNoLabel, shotNumber));
            showShotRecordDialog.setMessage(getString(R.string.showShotRecordMessage, barrelTempLabel, barrelTemp, targetXY, ""));
            showShotRecordDialog.setPositiveButton(R.string.alertOK, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            showShotRecordDialog.show();
            return;
        }

        //set display text for units
        if(shotRecord.targetX() > 0) {
            if (shotRecord.targetX() == 1) {
                unitsX = " inch";
            } else {
                unitsX = " inches";
            }
            distanceX = (String.format("%s", shotRecord.targetX())) + unitsX; //set X distance
            orientationX = " to the right."; //set X orientation
            messageX = distanceX + orientationX; //set X message
        } else if (shotRecord.targetX() < 0 ){
            //set display text for units
            if (shotRecord.targetX() == -1) {
                unitsX = " inch";
            } else {
                unitsX = " inches";
            }
            distanceX = (String.format("%s", -(shotRecord.targetX()))) + unitsX; //set X distance
            orientationX = " to the left."; //set X orientation
            messageX = distanceX + orientationX; //set X message
        } else if (shotRecord.targetX() == 0) {
            messageX = "Centered horizontally."; //set X message
        }

        if(shotRecord.targetY() > 0) {
            //set display text for units
            if (shotRecord.targetY() == 1) {
                unitsY = " inch";
            } else {
                unitsY = " inches";
            }
            distanceY = (String.format("%s", shotRecord.targetY())) + unitsY; //set Y distance
            orientationY = " above."; //set Y orientation
            messageY = distanceY + orientationY; //set Y message
        } else if (shotRecord.targetY() < 0 ){
            if (shotRecord.targetY() == -1) {
                //set display text for units
                unitsY = " inch";
            } else {
                unitsY = " inches";
            }
            distanceY = (String.format("%s", -(shotRecord.targetY()))) + unitsY; //set Y distance
            orientationY = " below."; //set Y orientation
            messageY = distanceY + orientationY; //set Y message
        } else if (shotRecord.targetY() == 0) {
            messageY = "Centered vertically."; //set Y message
        }
        //set targetXY text to include messageX and messageY
        //formatting of string done in strings.xml resource file
        targetXY = getString(R.string.showXYDist, messageX, messageY);

        AlertDialog.Builder showShotRecordDialog = new AlertDialog.Builder(ViewPreviousShootingRecords_Single.this);
        showShotRecordDialog.setTitle(getString(R.string.showShotRecordTitle, shotNoLabel, shotNumber));
        showShotRecordDialog.setMessage(getString(R.string.showShotRecordMessage, barrelTempLabel, barrelTemp, targetXYLabel, targetXY));
        showShotRecordDialog.setPositiveButton(R.string.alertOK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        showShotRecordDialog.show();

    }

    /*This function is designed to analyze recent shots recorded by the user and provide recommendations
     for adjusting the rifle scope. The function accepts an integer index and counter. If this is the first
     analysis conducted, the index will be set to 0 and all recorded shots will be analyzed.
     If an analysis has been previously conducted, the index will be set to the next shot recorded after
     the successful analysis, and all subsequent shots will be analyzed. A check is still conducted to verify
     at least 5 shots have been fired before analysis is conducted.*/

     /*Analysis will calculate a total sum of vertical and horizontal inputs from the shot records. Inputs above
     or to the right of tge target center are saved as positive values. Inputs to the left or below the target center
     are saved as negative values. The vertical and horizontal sums are averaged and rounded. These final values
     are used to recommend scope adjustments. Scope adjustments are provided as number of clicks left/right and
     up/down. One click is equivalent to one inch away from the center of the target. Clicks cannot be done
     fractionally.*/
    public void adjustScope(int index, int counter) {
        double verticalAlignment, horizontalAlignment;
        double sumVertical = 0, averageVertical, sumHorizontal = 0, averageHorizontal, averageCounter = 0;
        String verticalAdjustment, horizontalAdjustment;
        ShotRecord[] record = records.toArray(new ShotRecord[records.size()]); //Convert the shot records list to an array

        //Step through the array of shot records and obtain a total sum of vertical and horizontal inputs.
        for (int i = index; i < counter; i++) {
            sumVertical = sumVertical + record[i].targetY();
            sumHorizontal = sumHorizontal + record[i].targetX();
            averageCounter++;
        }
        //If less than 5 shots are available for analysis, notify user that more shots are needed.
        if (averageCounter < 5) {
            showInsufDialog();
        } else {
            shootingRecord.setId(shootingRecordId);
            shootingRecord.setDateTime(shootingRecord.datetime());
            shootingRecord.setLastShotAnalyzed(counter); //Update lastShotAnalyzed
            dbService.updateShootingRecord(shootingRecord);

            //Calculate average of vertical and horizontal inputs.
            averageVertical = sumVertical / averageCounter;
            averageHorizontal = sumHorizontal / averageCounter;

            //Calculate rounded values
            verticalAlignment = Math.round(averageVertical);
            horizontalAlignment = Math.round(averageHorizontal);

            //All analyzed shots were a bullseye. No scope adjustment is needed.
            if (verticalAlignment == 0 && horizontalAlignment == 0){
                showNoScopeAdjustDialog();
                return;
            }
            //Set vertical adjustment recommendation
            if (verticalAlignment < 0) {
                verticalAlignment = verticalAlignment * -1;
                verticalAdjustment = getString(R.string.adjustScopeUp, String.valueOf(verticalAlignment));

            } else {
                verticalAdjustment = getString(R.string.adjustScopeDown, String.valueOf(verticalAlignment));
            }
            //Set horizontal adjustment recommendation
            if (horizontalAlignment < 0) {
                horizontalAlignment = horizontalAlignment * -1;
                horizontalAdjustment = getString(R.string.adjustScopeRight, String.valueOf(horizontalAlignment));
            } else {
                horizontalAdjustment = getString(R.string.adjustScopeLeft, String.valueOf(horizontalAlignment));
            }
            showScopeAdjustDialog(verticalAdjustment, horizontalAdjustment); //Notify user of scope adjustment recommendations
        }
    }
    //Notify user that more shots are needed before analysis can be conducted.
    public void showInsufDialog(){
        AlertDialog.Builder insufficientShotCount = new AlertDialog.Builder(ViewPreviousShootingRecords_Single.this);
        insufficientShotCount.setTitle(R.string.insufShotCountTitle);
        insufficientShotCount.setMessage(R.string.insufShotCountMessage);
        insufficientShotCount.setPositiveButton(R.string.alertOK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        insufficientShotCount.show();
    }
    //Notify user of recommended vertical and horizontal scope adjustments.
    public void showScopeAdjustDialog( String verticalAdjustment, String horizontalAdjustment){
        AlertDialog.Builder scopeAdjustDialog = new AlertDialog.Builder(ViewPreviousShootingRecords_Single.this);
        scopeAdjustDialog.setTitle(R.string.adjustScopeTitle);
        scopeAdjustDialog.setMessage(getString(R.string.adjustScopeMessage, verticalAdjustment, horizontalAdjustment));
        scopeAdjustDialog.setPositiveButton(R.string.alertOK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        scopeAdjustDialog.show();
    }
    //Notify user no scope adjustments are needed.
    public void showNoScopeAdjustDialog(){
        AlertDialog.Builder noScopeAdjustDialog = new AlertDialog.Builder(ViewPreviousShootingRecords_Single.this);
        noScopeAdjustDialog.setTitle(R.string.noScopeAdjustTitle);
        noScopeAdjustDialog.setMessage(R.string.noScopeAdjustMessage);
        noScopeAdjustDialog.setPositiveButton(R.string.alertOK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        noScopeAdjustDialog.show();
    }
}
