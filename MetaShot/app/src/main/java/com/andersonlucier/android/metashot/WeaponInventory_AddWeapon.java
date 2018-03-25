package com.andersonlucier.android.metashot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class WeaponInventory_AddWeapon extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weapon_inventory_add_weapon);
    }
    public void onClick(View view){
        switch(view.getId()){
            case R.id.addNewWeapon:
                //add code for sending data to database
                startActivity(new Intent(WeaponInventory_AddWeapon.this, WeaponInventory.class));
                break;
        }
    }
}
