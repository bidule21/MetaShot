package com.andersonlucier.android.metashot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.andersonlucier.android.metashot.databaseservicelib.DatabaseService;
import com.andersonlucier.android.metashot.databaseservicelib.impl.GunRecord;

import java.util.List;

public class WeaponInventory extends AppCompatActivity {

    private DatabaseService dbService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weapon_inventory);

        dbService = new DatabaseService(this);
        List<GunRecord> records = dbService.getGunRecords();

        for (GunRecord gun : records){
            //heres where you add the records to the list
            System.out.println(gun.id());
            System.out.println(gun.gunName());
            System.out.println(gun.details());
        }

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
