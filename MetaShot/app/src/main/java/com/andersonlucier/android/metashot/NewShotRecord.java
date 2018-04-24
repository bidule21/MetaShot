package com.andersonlucier.android.metashot;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.andersonlucier.android.metashot.databaseservicelib.DatabaseShotService;
import com.andersonlucier.android.metashot.databaseservicelib.impl.ShotRecord;

import java.util.ArrayList;
import java.util.List;

public class NewShotRecord extends AppCompatActivity {

    private String shootingRecordId;
    private String shootingRecordTitle;
    private DatabaseShotService dbService;
    private AlertDialog.Builder builder;
    private List<ShotRecord> records;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_shot_record);

        shootingRecordId = getIntent().getStringExtra("SHOOTING_RECORD_ID");
        shootingRecordTitle = getIntent().getStringExtra("SHOOTING_TITLE");

        dbService = new DatabaseShotService(this);

        populateShotRecords();

        TextView shootingRecordName = findViewById(R.id.shootingRecordName);
        shootingRecordName.setText(shootingRecordTitle);
    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.autoRecord:
                startActivity(new Intent(NewShotRecord.this, NewShotRecord_AutoRecord.class));
                break;
            case R.id.manualCreate:

                Intent intent = new Intent(new Intent(NewShotRecord.this, NewShotRecord_ManualCreate.class));
                intent.putExtra("SHOOTING_RECORD_ID", shootingRecordId);
                intent.putExtra("SHOOTING_TITLE", shootingRecordTitle);
                startActivity(intent);

                break;
            case R.id.goToHome:
                startActivity(new Intent(NewShotRecord.this, MainActivity.class));
                break;
        }
    }

    private void populateShotRecords() {
        ListView lv = findViewById(R.id.shotRecords);

        //gets all the shots for the shooting record
        records = dbService.getAllShotsRecordsByShootingId(shootingRecordId);

        //if records are empty, no need to setup the list
        if (records.isEmpty()) {
            return;
        }

        builder = new AlertDialog.Builder(this);

        //populates a list of strings for the list
        List<String> gunArrayList = new ArrayList<>();
        for (ShotRecord record : records){
            gunArrayList.add("Shot - " + record.shotNumber());
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

        //sets the long click to allow a user to delete a shot record
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           final int position, long id) {

                //sets the builder popup
                builder.setTitle("Delete");
                builder.setMessage("Would you like to delete this shot?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        //finds the object title
                        String item = arrayAdapter.getItem(position);
                        String[] parts = item.split(" ");

                        //finds the id of the object based on shot number
                        for(ShotRecord r: records) {
                            if(r.shotNumber() == Integer.parseInt(parts[2])) {
                                //remove the object from the db
                                dbService.deleteShotRecord(r.id());
                                break;
                            }
                        }
                        //remove the object from the Adapter
                        arrayAdapter.remove(item);
                        arrayAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });

                //show the alert dialog
                AlertDialog alert = builder.create();
                alert.show();
                return false;
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
        barrelTemp = String.format("%s", shotRecord.barrelTemp());

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
            AlertDialog.Builder showShotRecordDialog = new AlertDialog.Builder(NewShotRecord.this);
            showShotRecordDialog.setTitle(getString(R.string.showShotRecordTitle, shotNoLabel, shotNumber));
            showShotRecordDialog.setMessage(targetXY);
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

        AlertDialog.Builder showShotRecordDialog = new AlertDialog.Builder(NewShotRecord.this);
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
}
