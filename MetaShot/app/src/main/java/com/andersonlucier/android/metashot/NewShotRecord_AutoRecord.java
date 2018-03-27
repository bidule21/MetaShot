package com.andersonlucier.android.metashot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class NewShotRecord_AutoRecord extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_shot_record_auto_record);
    }
    public void onClick (View view){
        switch (view.getId()){
            case R.id.saveRecord:
                //add code for sending data to database
                startActivity(new Intent(NewShotRecord_AutoRecord.this, NewShotRecord.class));
                break;
            case R.id.cancelRecord:
                startActivity(new Intent(NewShotRecord_AutoRecord.this, NewShotRecord.class));
                break;
        }
    }
}
