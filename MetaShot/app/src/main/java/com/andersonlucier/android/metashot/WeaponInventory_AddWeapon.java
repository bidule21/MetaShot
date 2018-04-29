package com.andersonlucier.android.metashot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.andersonlucier.android.metashot.databaseservicelib.DatabaseShotService;
import com.andersonlucier.android.metashot.databaseservicelib.impl.GunRecord;

public class WeaponInventory_AddWeapon extends AppCompatActivity {

    private EditText weaponNickname, weaponDetails;
    private DatabaseShotService dbService;
    private String weaponId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weapon_inventory_add_weapon);

        weaponNickname = findViewById(R.id.weaponNickname);
        weaponDetails = findViewById(R.id.weaponOtherDetails);

        //setup database service
        dbService = new DatabaseShotService(this);

        //gets the selected weapon if there is one
        weaponId = getIntent().getStringExtra("WEAPON_ID");

        if (weaponId != null) {
            populateWeaponInventory();
        }

    }

    public void onClick(View view){
        switch(view.getId()){
            case R.id.addNewWeapon:

                //create a gun record
                GunRecord gun = new GunRecord();
                gun.setGunName(weaponNickname.getText().toString());
                gun.setDetails(weaponDetails.getText().toString());

                if(weaponId != null) {
                    //update the gun record to database
                    gun.setId(weaponId);
                    dbService.updateGunRecord(gun);
                } else {
                    //save gun record to database
                    dbService.createGunRecord(gun);
                }

                //show toast message to show that it saved
                Toast.makeText(this, "Nickname: " + weaponNickname.getText().toString() + "\n" + "Details: " + weaponDetails.getText().toString(), Toast.LENGTH_LONG).show();

                //redirect back to the weapon inventory
                startActivity(new Intent(WeaponInventory_AddWeapon.this, WeaponInventory.class));
                break;
            case R.id.cancelNewWeapon:
                startActivity(new Intent(WeaponInventory_AddWeapon.this, WeaponInventory.class));
                break;
            case R.id.goToHome:
                startActivity(new Intent(WeaponInventory_AddWeapon.this, MainActivity.class));
                break;
        }
    }

    /**
     * Populates the weapon Inventory with an existing weapon
     */
    private void populateWeaponInventory () {
        GunRecord record = dbService.getSingleGunRecord(weaponId);
        weaponNickname.setText(record.gunName());
        weaponDetails.setText(record.details());

        Button addWeapon = findViewById(R.id.addNewWeapon);
        addWeapon.setText(R.string.updateWeapon);
    }
}
