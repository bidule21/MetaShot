package com.andersonlucier.android.metashot;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.andersonlucier.android.metashot.databaseservicelib.DatabaseShotService;
import com.andersonlucier.android.metashot.databaseservicelib.impl.ShotRecord;

public class NewShotRecord_ManualCreate extends AppCompatActivity {

    private EditText verticalDistInput, horizontalDistInput;
    private String shootingRecordId;
    private String shootingRecordTitle;
    private DatabaseShotService dbService;
    RadioButton left, right, above, below;

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
    }
    public void onClick (View view){

        Intent intent;

        switch (view.getId()){
            case R.id.bullseye:

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

                       intent = new Intent(new Intent(NewShotRecord_ManualCreate.this, NewShotRecord.class));
                       intent.putExtra("SHOOTING_RECORD_ID", shootingRecordId);
                       intent.putExtra("SHOOTING_TITLE", shootingRecordTitle);
                       startActivity(intent);
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

            case R.id.createShot:

                double convertHorizontalDist = Double.parseDouble(horizontalDistInput.getText().toString());
                double convertVerticalDist = Double.parseDouble(verticalDistInput.getText().toString());

                if(convertHorizontalDist < 0 || convertVerticalDist < 0){
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

                if(convertHorizontalDist > 0 && !left.isChecked() && !right.isChecked()){
                    noRadioChecked("Horizontal");
                    break;
                }

                if(convertVerticalDist > 0 && !above.isChecked() && !below.isChecked()){
                    noRadioChecked("Vertical");
                    break;
                }

                if (left.isChecked()){
                    convertHorizontalDist = convertHorizontalDist * -1;
                }

                if(below.isChecked()){
                    convertVerticalDist = convertVerticalDist * -1;
                }

                ShotRecord record = new ShotRecord();
                record.setShootingRecordId(shootingRecordId);
                record.setTargetX(convertHorizontalDist);
                record.setTargetY(convertVerticalDist);
                dbService.createShotRecord(record);

                Toast.makeText(this, "Vertical Input: " + String.valueOf(convertVerticalDist) + "\n" + "Horizontal Input: " + String.valueOf(convertHorizontalDist), Toast.LENGTH_LONG).show();
                intent = new Intent(new Intent(NewShotRecord_ManualCreate.this, NewShotRecord.class));
                intent.putExtra("SHOOTING_RECORD_ID", shootingRecordId);
                intent.putExtra("SHOOTING_TITLE", shootingRecordTitle);
                startActivity(intent);
                break;

            case R.id.cancelShot:
                intent = new Intent(new Intent(NewShotRecord_ManualCreate.this, NewShotRecord.class));
                intent.putExtra("SHOOTING_RECORD_ID", shootingRecordId);
                intent.putExtra("SHOOTING_TITLE", shootingRecordTitle);
                startActivity(intent);
                break;

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
