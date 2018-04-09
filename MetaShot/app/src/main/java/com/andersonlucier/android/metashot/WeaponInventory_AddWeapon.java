package com.andersonlucier.android.metashot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class WeaponInventory_AddWeapon extends AppCompatActivity {

    private EditText weaponNickname, weaponDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weapon_inventory_add_weapon);

        weaponNickname = findViewById(R.id.weaponNickname);
        weaponDetails = findViewById(R.id.weaponOtherDetails);


    }
    public void onClick(View view){
        switch(view.getId()){
            case R.id.addNewWeapon:
                //TODO: Add code for sending data to database
                Toast.makeText(this, "Nickname: " + weaponNickname.getText().toString() + "\n" + "Details: " + weaponDetails.getText().toString(), Toast.LENGTH_LONG).show();
                startActivity(new Intent(WeaponInventory_AddWeapon.this, WeaponInventory.class));
                break;
            case R.id.goToHome:
                startActivity(new Intent(WeaponInventory_AddWeapon.this, MainActivity.class));
                break;
        }
    }
}
