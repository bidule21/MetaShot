package com.andersonlucier.android.metashot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.andersonlucier.android.servicelib.Service;
import com.andersonlucier.android.servicelib.impl.ShootingRecord;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Service service = new Service();
        ShootingRecord shooting = service.getShootingRecordById();
        Log.d("attempt", shooting.description());
    }
}
