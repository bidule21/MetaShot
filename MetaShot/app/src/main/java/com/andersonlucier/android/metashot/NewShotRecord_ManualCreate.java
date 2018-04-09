package com.andersonlucier.android.metashot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewShotRecord_ManualCreate extends AppCompatActivity {

    private EditText barrelTempInput, verticalDistInput, horizontalDistInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_shot_record_manual_create);

        barrelTempInput = findViewById(R.id.barrelTempInput);
        verticalDistInput = findViewById(R.id.verticalDistInput);
        horizontalDistInput = findViewById(R.id.horizontalDistInput);
    }
    public void onClick (View view){
        switch (view.getId()){
            case R.id.createShot:
                //TODO: Add code for sending data to database
                Toast.makeText(this, "Barrel Temp Input: " + barrelTempInput.getText().toString() + "\n" + "Vertical Input: " + verticalDistInput.getText().toString() + "\n" + "Horizontal Input: " + horizontalDistInput.getText().toString(), Toast.LENGTH_LONG).show();
                startActivity(new Intent(NewShotRecord_ManualCreate.this, NewShotRecord.class));
                break;
            case R.id.cancelShot:
                startActivity(new Intent(NewShotRecord_ManualCreate.this, NewShotRecord.class));
                break;
            case R.id.goToHome:
                startActivity(new Intent(NewShotRecord_ManualCreate.this, MainActivity.class));
                break;
        }
    }
}
