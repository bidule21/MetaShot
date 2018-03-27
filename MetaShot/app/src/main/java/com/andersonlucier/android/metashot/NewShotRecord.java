package com.andersonlucier.android.metashot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class NewShotRecord extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_shot_record);
    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.autoRecord:
                startActivity(new Intent(NewShotRecord.this, NewShotRecord_AutoRecord.class));
                break;
            case R.id.manualCreate:
                startActivity(new Intent(NewShotRecord.this, MainActivity.class));
                break;
        }
    }
}
