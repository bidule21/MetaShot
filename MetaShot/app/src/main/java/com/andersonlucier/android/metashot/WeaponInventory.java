package com.andersonlucier.android.metashot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.andersonlucier.android.metashot.databaseservicelib.DatabaseShotService;
import com.andersonlucier.android.metashot.databaseservicelib.impl.GunRecord;

import java.util.ArrayList;
import java.util.List;

public class WeaponInventory extends AppCompatActivity {

    private DatabaseShotService dbService;
    private List<GunRecord> records;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weapon_inventory);
        dbService = new DatabaseShotService(this);

        populateWeaponList();

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

    /**
     * Populates the weapon list with all records from the database
     */
    private void populateWeaponList() {
        ListView lv = findViewById(R.id.inventoryList);

        //gets the records, populates a List object
        records = dbService.getAllGunRecords();
        List<String> gunArrayList = new ArrayList<>();
        for (GunRecord gun : records){
            gunArrayList.add(gun.gunName());
        }

        //creates the adapter for the weapon list
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                gunArrayList );

        lv.setAdapter(arrayAdapter);

        //lets item click function to redirect to the selected weapon
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent(new Intent(WeaponInventory.this, WeaponInventory_AddWeapon.class));
                intent.putExtra("WEAPON_ID", records.get(position).id());
                startActivity(intent);
            }
        });
    }
}
