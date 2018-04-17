package com.andersonlucier.android.metashot;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.andersonlucier.android.metashot.databaseservicelib.DatabaseShotService;
import com.andersonlucier.android.metashot.databaseservicelib.impl.ShootingRecord;
import java.util.ArrayList;
import java.util.List;

public class ViewPreviousShootingRecords extends AppCompatActivity {
    private DatabaseShotService dbService;
    private List<ShootingRecord> records;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_previous_shooting_records);
        dbService = new DatabaseShotService(this);

        populateShootingRecordsList();

    }
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.goToHome:
                startActivity(new Intent(ViewPreviousShootingRecords.this, MainActivity.class));
                break;
        }
    }

    /**
     * Populate the shooting records list
     */
    private void populateShootingRecordsList() {
        //gets the list to populate
        ListView lv = findViewById(R.id.shootingList);

        //query the database for all shooting records
        records = dbService.getAllShootingRecords();

        //date time formatting
        String pattern = "MMM dd, yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        //creates a list of shooting records as strings
        List<String> shootingArrayList = new ArrayList<>();
        for (ShootingRecord record : records){
            String date = simpleDateFormat.format(record.datetime());
            shootingArrayList.add(record.title() + " - " + date);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                shootingArrayList );

        lv.setAdapter(arrayAdapter);

        //sets the on item click function to redirect to the shooting record
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,int position, long id) {
                Intent intent = new Intent(new Intent(ViewPreviousShootingRecords.this, ViewPreviousShootingRecords_Single.class));
                intent.putExtra("RECORD_ID", records.get(position).Id());
                startActivity(intent);
            }
        });
    }
}
