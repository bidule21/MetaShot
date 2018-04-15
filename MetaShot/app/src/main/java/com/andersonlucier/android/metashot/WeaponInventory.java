package com.andersonlucier.android.metashot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.andersonlucier.android.metashot.databaseservicelib.DatabaseShotService;
import com.andersonlucier.android.metashot.databaseservicelib.impl.GunRecord;

import java.util.ArrayList;
import java.util.List;

public class WeaponInventory extends AppCompatActivity {

    private DatabaseShotService dbService;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weapon_inventory);

        lv = (ListView) findViewById(R.id.inventoryList);

        dbService = new DatabaseShotService(this);
        List<GunRecord> records = dbService.getAllGunRecords();

        List<String> gunArrayList = new ArrayList<>();
        for (GunRecord gun : records){
            gunArrayList.add(gun.gunName());
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
                startActivity(new Intent(WeaponInventory.this, WeaponInventory_AddWeapon.class));
                break;
            case R.id.goToHome:
                startActivity(new Intent(WeaponInventory.this, MainActivity.class));
                break;
        }
    }
}
