package com.andersonlucier.android.metashot;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);
    }
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.newRecord:
                startActivity(new Intent(MainActivity.this, NewShootingRecord.class));
                break;
            case R.id.viewRecord:
                startActivity(new Intent(MainActivity.this, ViewPreviousShootingRecords.class));
                break;
            case R.id.weaponInventory:
                startActivity(new Intent(MainActivity.this, WeaponInventory.class));
                break;
        }
    }
}

