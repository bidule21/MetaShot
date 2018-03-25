package com.andersonlucier.android.metashot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class WeaponInventory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weapon_inventory);
    }
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.addWeapon:
                startActivity(new Intent(WeaponInventory.this, WeaponInventory_AddWeapon.class));
                break;

        }
    }
}