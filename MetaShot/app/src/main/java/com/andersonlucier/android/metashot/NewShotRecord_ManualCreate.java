package com.andersonlucier.android.metashot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.andersonlucier.android.metashot.databaseservicelib.DatabaseShotService;
import com.andersonlucier.android.metashot.databaseservicelib.impl.ShotRecord;

public class NewShotRecord_ManualCreate extends AppCompatActivity {

    private EditText verticalDistInput, horizontalDistInput;
    private String shootingRecordId;
    private String shootingRecordTitle;
    private DatabaseShotService dbService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_shot_record_manual_create);

        dbService = new DatabaseShotService(this);

        verticalDistInput = findViewById(R.id.verticalDistInput);
        horizontalDistInput = findViewById(R.id.horizontalDistInput);

        shootingRecordId = getIntent().getStringExtra("SHOOTING_RECORD_ID");
        shootingRecordTitle = getIntent().getStringExtra("SHOOTING_TITLE");
    }
    public void onClick (View view){
        Intent intent;
        switch (view.getId()){
            case R.id.createShot:

                ShotRecord record = new ShotRecord();
                record.setShootingRecordId(shootingRecordId);
                record.setTargetX(Double.parseDouble(horizontalDistInput.getText().toString()));
                record.setTargetY(Double.parseDouble(verticalDistInput.getText().toString()));
                dbService.createShotRecord(record);

                Toast.makeText(this, "Vertical Input: " + verticalDistInput.getText().toString() + "\n" + "Horizontal Input: " + horizontalDistInput.getText().toString(), Toast.LENGTH_LONG).show();
                intent = new Intent(new Intent(NewShotRecord_ManualCreate.this, NewShotRecord.class));
                intent.putExtra("SHOOTING_RECORD_ID", shootingRecordId);
                intent.putExtra("SHOOTING_TITLE", shootingRecordTitle);
                startActivity(intent);
                break;
            case R.id.cancelShot:
                intent = new Intent(new Intent(NewShotRecord_ManualCreate.this, NewShotRecord.class));
                intent.putExtra("SHOOTING_RECORD_ID", shootingRecordId);
                intent.putExtra("SHOOTING_TITLE", shootingRecordTitle);
                startActivity(intent);
                break;
            case R.id.goToHome:
                startActivity(new Intent(NewShotRecord_ManualCreate.this, MainActivity.class));
                break;
        }
    }
}
