package com.andersonlucier.android.metashot;

import android.content.Intent;
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

/**
 * Created by SyberDeskTop on 4/15/2018.
 */

public class ViewShootingRecords_Single extends AppCompatActivity {

    private DatabaseShotService dbService;
    private List<ShotRecord> records;
    private String shootingRecordId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_shooting_records_single);
        dbService = new DatabaseShotService(this);
        shootingRecordId = getIntent().getStringExtra("RECORD_ID");

        populateShootingInformation();
        populateShotRecords();

    }

    private void populateShootingInformation() {
        ShootingRecord record = dbService.getSingleShootingRecord(shootingRecordId);
        TextView title = findViewById(R.id.titledatetime);
        title.setText(String.format("Title: %s %s", record.title(), record.datetime()));
        TextView location = findViewById(R.id.location);
        location.setText(String.format("Location: %s", record.location()));
        TextView weather = findViewById(R.id.weather);
        weather.setText(String.format("Weather: %s", record.weather()));
        TextView description = findViewById(R.id.description);
        description.setText(String.format("Description: %s", record.description()));

    }

    private void populateShotRecords() {
        ListView lv = findViewById(R.id.previousShotRecordsList);
        records = dbService.getAllShotsRecordsByShootingId(shootingRecordId);

        List<String> gunArrayList = new ArrayList<>();
        for (ShotRecord record : records){
            gunArrayList.add("Shot - " + record.shotNumber());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                gunArrayList );

        lv.setAdapter(arrayAdapter);

        lv.setTextFilterEnabled(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent(new Intent(ViewShootingRecords_Single.this, MainActivity.class));
                intent.putExtra("RECORD_ID", records.get(position).id());
                startActivity(intent);

            }
        });
    }

    public void onClick(View view){
        switch (view.getId()) {
            case R.id.goToHome:
                startActivity(new Intent(ViewShootingRecords_Single.this, MainActivity.class));
                break;
        }
    }
}
