package com.andersonlucier.android.metashot;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.andersonlucier.android.metashot.databaseservicelib.DatabaseShotService;
import com.andersonlucier.android.metashot.databaseservicelib.impl.ShotRecord;

public class NewShotRecord_ManualCreate extends AppCompatActivity {

    private EditText verticalDistInput, horizontalDistInput;
    private String shootingRecordId;
    private String shootingRecordTitle;
    private DatabaseShotService dbService;
    RadioButton left, right, above, below;
    private String update, ShotRecordId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_shot_record_manual_create);

        dbService = new DatabaseShotService(this);

        verticalDistInput = findViewById(R.id.verticalDistInput);
        horizontalDistInput = findViewById(R.id.horizontalDistInput);
        left = findViewById(R.id.horizontalLeft);
        right = findViewById(R.id.horizontalRight);
        above = findViewById(R.id.verticalAbove);
        below = findViewById(R.id.verticalBelow);

        shootingRecordId = getIntent().getStringExtra("SHOOTING_RECORD_ID");
        shootingRecordTitle = getIntent().getStringExtra("SHOOTING_TITLE");
        update = getIntent().getStringExtra("UPDATE");
        if(update ==null) {
            update = "false";
        }

        ShotRecordId = getIntent().getStringExtra("SHOT_RECORD_ID");

    }
    public void onClick (View view){

        Intent intent;

        switch (view.getId()){
            case R.id.bullseye:
                /*If a shot is a bullseye, the horizontal and vertical distances from the center of the target
                * will always equal 0. This button allows the user to easily record that a bullseye was hit,
                * and all relevant data is auto-populated and saved to the database.*/
                AlertDialog.Builder bullseyeAlertDialog = new AlertDialog.Builder(NewShotRecord_ManualCreate.this);
                bullseyeAlertDialog.setTitle(R.string.bullseyeTitle);
                bullseyeAlertDialog.setMessage(R.string.bullseyeMessage);
                bullseyeAlertDialog.setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                       double horizontalDist = 0;
                       double verticalDist = 0;
                       Intent intent;

                       ShotRecord record = new ShotRecord();
                       record.setShootingRecordId(shootingRecordId);
                       record.setTargetX(horizontalDist);
                       record.setTargetY(verticalDist);
                       dbService.createShotRecord(record);

                       if(update.equals("True")) {
                           Intent returnIntent = new Intent();
                           returnIntent.putExtra("XVALUE",horizontalDist);
                           returnIntent.putExtra("YVALUE",verticalDist);
                           returnIntent.putExtra("SHOT_RECORD_ID", ShotRecordId);
                           setResult(Activity.RESULT_OK,returnIntent);
                           finish();
                       } else {
                           intent = new Intent(new Intent(NewShotRecord_ManualCreate.this, NewShotRecord.class));
                           intent.putExtra("SHOOTING_RECORD_ID", shootingRecordId);
                           intent.putExtra("SHOOTING_TITLE", shootingRecordTitle);
                           startActivity(intent);
                       }


                    }
                });
                bullseyeAlertDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                });
                bullseyeAlertDialog.show();
                break;
            /*When clicked, a shot record is created if the user input was valid.
            For a shot to be created, the user is required to input a positive value number.
            User is also required to select a radio button that corresponds to the orientation
            of the shot relative to the center of the target. Depending on the orientation of the shot
            relative to the center of the target, user input is converted to accurately reflect position
            on the relevant axis.*/
            case R.id.createShot:
                double convertHorizontalDist, convertVerticalDist;

                //Handle instance of blank input field
                if(horizontalDistInput.getText().length() == 0){
                    convertHorizontalDist = -1;
                } else {
                    convertHorizontalDist = Double.parseDouble(horizontalDistInput.getText().toString());
                }
                if(verticalDistInput.getText().length() == 0){
                    convertVerticalDist = -1;
                } else {
                    convertVerticalDist = Double.parseDouble(verticalDistInput.getText().toString());
                }

                //Validate user input
                if(convertHorizontalDist < 0 || convertVerticalDist < 0){
                    //Notify user if input is invalid
                    AlertDialog.Builder invalidDistAlertDialog = new AlertDialog.Builder(NewShotRecord_ManualCreate.this);
                    invalidDistAlertDialog.setTitle(R.string.distTitle);
                    invalidDistAlertDialog.setMessage(R.string.distMessage);
                    invalidDistAlertDialog.setPositiveButton(R.string.alertOK, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    invalidDistAlertDialog.show();
                    break;
                }
                //Validate horizontal orientation radio button is selected
                if(convertHorizontalDist > 0 && !left.isChecked() && !right.isChecked()){
                    //Notify user if no horizontal orientation radio button is selected
                    noRadioChecked("Horizontal");
                    break;
                }
                //Validate vertical orientation radio button is selected
                if(convertVerticalDist > 0 && !above.isChecked() && !below.isChecked()){
                    //Notify user if no vertical orientation radio button is selected
                    noRadioChecked("Vertical");
                    break;
                }
                //A shot to the left of the target center is considered negative on the x-axis.
                if (left.isChecked()){
                    convertHorizontalDist = convertHorizontalDist * -1;
                }
                //A shot below the target center is considered negative on the y-axis.
                if(below.isChecked()){
                    convertVerticalDist = convertVerticalDist * -1;
                }

                if(update.equals("True")) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("XVALUE",convertHorizontalDist);
                    returnIntent.putExtra("YVALUE",convertVerticalDist);
                    returnIntent.putExtra("SHOT_RECORD_ID", ShotRecordId);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                } else {

                    ShotRecord record = new ShotRecord();
                    record.setShootingRecordId(shootingRecordId);
                    record.setTargetX(convertHorizontalDist);
                    record.setTargetY(convertVerticalDist);
                    dbService.createShotRecord(record);

                    intent = new Intent(new Intent(NewShotRecord_ManualCreate.this, NewShotRecord.class));
                    intent.putExtra("SHOOTING_RECORD_ID", shootingRecordId);
                    intent.putExtra("SHOOTING_TITLE", shootingRecordTitle);
                    startActivity(intent);
                }
                break;
            //When clicked, no record is created. User is redirected to the landing page.
            case R.id.cancelShot:
                if(update.equals("True")) {
                    Intent returnIntent = new Intent();
                    setResult(Activity.RESULT_CANCELED,returnIntent);
                    finish();
                } else {
                    intent = new Intent(new Intent(NewShotRecord_ManualCreate.this, NewShotRecord.class));
                    intent.putExtra("SHOOTING_RECORD_ID", shootingRecordId);
                    intent.putExtra("SHOOTING_TITLE", shootingRecordTitle);
                    startActivity(intent);
                }
                break;
            //When clicked, user is redirected to main landing page
            case R.id.goToHome:
                startActivity(new Intent(NewShotRecord_ManualCreate.this, MainActivity.class));
                break;
        }
    }

    public void noRadioChecked(String axis) {
        String message, axisParam1, axisParam2;
        final AlertDialog.Builder noRadioAlertDialog = new AlertDialog.Builder(NewShotRecord_ManualCreate.this);
        noRadioAlertDialog.setTitle(R.string.noRadioTitle);
        switch(axis){
            case "Horizontal":
                axisParam1 = getString(R.string.horizontalLeft);
                axisParam2 = getString(R.string.horizontalRight);
                message = getString(R.string.noRadioMessage, axis, axisParam1, axisParam2);

                noRadioAlertDialog.setMessage(message);
                break;

            case "Vertical":
                axisParam1 = getString(R.string.verticalAbove);
                axisParam2 = getString(R.string.verticalBelow);
                message = getString(R.string.noRadioMessage, axis, axisParam1, axisParam2);

                noRadioAlertDialog.setMessage(message);
                break;
        }
        noRadioAlertDialog.setPositiveButton(R.string.alertOK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        noRadioAlertDialog.show();
    }
}
