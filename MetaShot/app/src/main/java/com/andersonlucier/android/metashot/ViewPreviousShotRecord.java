package com.andersonlucier.android.metashot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.andersonlucier.android.metashot.databaseservicelib.DatabaseShotService;
import com.andersonlucier.android.metashot.databaseservicelib.impl.ShotRecord;

/**
 * Created by SyberDeskTop on 4/15/2018.
 */

public class ViewPreviousShotRecord extends AppCompatActivity {

    private DatabaseShotService dbService;
    private String shotRecordId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_previous_shot_record);
        dbService = new DatabaseShotService(this);
        shotRecordId = getIntent().getStringExtra("SHOT_RECORD_ID");

        populateShotRecord();
    }

    private void populateShotRecord() {
        ShotRecord record = dbService.getSingleShotsRecordsById(shotRecordId);
        TextView shotNumber = findViewById(R.id.shotNumber);
        shotNumber.setText(String.format("Shot Number: %s", record.shotNumber()));
        TextView barrelTemp = findViewById(R.id.barrelTemp);
        barrelTemp.setText(String.format("barrel Temp: %s", record.barrelTemp()));
        TextView targetX = findViewById(R.id.targetX);
        targetX.setText(String.format("Target-X: %s", record.targetX()));
        TextView targetY = findViewById(R.id.targetY);
        targetY.setText(String.format("target-Y: %s", record.targetY()));
    }

    public void onClick(View view){
        switch (view.getId()) {
            case R.id.goToHome:
                startActivity(new Intent(ViewPreviousShotRecord.this, MainActivity.class));
                break;
        }
    }

}
