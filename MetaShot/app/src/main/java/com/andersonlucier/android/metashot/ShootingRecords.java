package com.andersonlucier.android.metashot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.andersonlucier.android.metashot.databaseservicelib.DatabaseShotService;
import com.andersonlucier.android.metashot.databaseservicelib.impl.ShootingRecord;

import java.util.ArrayList;
import java.util.List;

public class ShootingRecords extends AppCompatActivity {
    private DatabaseShotService dbService;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shooting_records);

        lv = (ListView) findViewById(R.id.shootingList);

        dbService = new DatabaseShotService(this);
        List<ShootingRecord> records = dbService.getAllShootingRecords();

        List<String> gunArrayList = new ArrayList<>();
        for (ShootingRecord gun : records){
            gunArrayList.add(gun.description());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                gunArrayList );

        lv.setAdapter(arrayAdapter);

    }
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.addWeapon:
                break;
            case R.id.goToHome:
                startActivity(new Intent(ShootingRecords.this, MainActivity.class));
                break;
        }
    }
}
