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
    private TextView shotNumber, barrelTemp, targetX, targetY;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_previous_shot_record);
        dbService = new DatabaseShotService(this);
        shotRecordId = getIntent().getStringExtra("SHOT_RECORD_ID");

        shotNumber = findViewById(R.id.shotNumber);
        barrelTemp = findViewById(R.id.barrelTemp);
        targetX = findViewById(R.id.targetX);
        targetY = findViewById(R.id.targetY);


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
        //get the shot record
        ShotRecord record = dbService.getSingleShotsRecordsById(shotRecordId);

        //populate the shot number
        shotNumber.setText(String.format("%s", record.shotNumber()));

        //populate the barrel temp
        barrelTemp.setText(String.format("%s", record.barrelTemp()));

        //populate the x target
        targetX.setText(String.format("%s", record.targetX()));

        //populate the y target
        targetY.setText(String.format("%s", record.targetY()));
    }

}
