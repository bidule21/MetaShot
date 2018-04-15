package com.andersonlucier.android.metashot;

import android.content.Intent;
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

public class ViewShootingRecords extends AppCompatActivity {
    private DatabaseShotService dbService;
    private ListView lv;
    private List<ShootingRecord> records;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_shooting_records);

        lv = (ListView) findViewById(R.id.shootingList);

        dbService = new DatabaseShotService(this);
        records = dbService.getAllShootingRecords();

        List<String> gunArrayList = new ArrayList<>();
        for (ShootingRecord record : records){
            gunArrayList.add(record.title() + " - " + record.datetime());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                gunArrayList );

        lv.setAdapter(arrayAdapter);

        lv.setTextFilterEnabled(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,int position, long id) {
                Intent intent = new Intent(new Intent(ViewShootingRecords.this, ViewShootingRecords_Single.class));
                intent.putExtra("RECORD_ID", records.get(position).Id());
                startActivity(intent);

            }
        });


    }
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.goToHome:
                startActivity(new Intent(ViewShootingRecords.this, MainActivity.class));
                break;
        }
    }
}
