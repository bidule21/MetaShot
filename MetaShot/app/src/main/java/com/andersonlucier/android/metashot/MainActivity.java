package com.andersonlucier.android.metashot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.util.Log;

import com.andersonlucier.android.servicelib.Service;
import com.andersonlucier.android.servicelib.impl.ShootingRecord;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);

        Service service = new Service();
        ShootingRecord shooting = service.getShootingRecordById(1);
        Log.d("attempt", shooting.description());
    }
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.newRecord:
                startActivity(new Intent(MainActivity.this, NewShootingRecord.class));
                break;
            case R.id.viewRecord:
                break;
            case R.id.weaponInventory:
                startActivity(new Intent(MainActivity.this, WeaponInventory.class));
                break;
        }
    }
}

