package com.andersonlucier.android.metashot;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.andersonlucier.android.metashot.databaseservicelib.DatabaseShotService;
import com.andersonlucier.android.metashot.databaseservicelib.impl.ShotRecord;

import java.util.ArrayList;
import java.util.List;

public class NewShotRecord extends AppCompatActivity {

    private String shootingRecordId;
    private String shootingRecordTitle;
    private DatabaseShotService dbService;
    private AlertDialog.Builder builder;
    private List<ShotRecord> records;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_shot_record);

        shootingRecordId = getIntent().getStringExtra("SHOOTING_RECORD_ID");
        shootingRecordTitle = getIntent().getStringExtra("SHOOTING_TITLE");

        dbService = new DatabaseShotService(this);

        populateShotRecords();

        TextView shootingRecordName = findViewById(R.id.shootingRecordName);
        shootingRecordName.setText(shootingRecordTitle);
    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.autoRecord:
                startActivity(new Intent(NewShotRecord.this, NewShotRecord_AutoRecord.class));
                break;
            case R.id.manualCreate:

                Intent intent = new Intent(new Intent(NewShotRecord.this, NewShotRecord_ManualCreate.class));
                intent.putExtra("SHOOTING_RECORD_ID", shootingRecordId);
                intent.putExtra("SHOOTING_TITLE", shootingRecordTitle);
                startActivity(intent);

                break;
            case R.id.goToHome:
                startActivity(new Intent(NewShotRecord.this, MainActivity.class));
                break;
        }
    }

    private void populateShotRecords() {
        ListView lv = findViewById(R.id.shotRecords);

        //gets all the shots for the shooting record
        records = dbService.getAllShotsRecordsByShootingId(shootingRecordId);

        //if records are empty, no need to setup the list
        if (records.isEmpty()) {
            return;
        }

        builder = new AlertDialog.Builder(this);

        //populates a list of strings for the list
        List<String> gunArrayList = new ArrayList<>();
        for (ShotRecord record : records){
            gunArrayList.add("Shot - " + record.shotNumber());
        }

        //creates the adapter and sets the adapter
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                gunArrayList );
        lv.setAdapter(arrayAdapter);

        //sets the on item click to redirect to the view previous shot record view
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent(new Intent(NewShotRecord.this, ViewPreviousShotRecord.class));
                intent.putExtra("SHOT_RECORD_ID", records.get(position).id());
                startActivity(intent);

            }
        });

        //sets the long click to allow a user to delete a shot record
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           final int position, long id) {

                //sets the builder popup
                builder.setTitle("Delete");
                builder.setMessage("Would you like to delete this shot?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        //finds the object title
                        String item = arrayAdapter.getItem(position);
                        String[] parts = item.split(" ");

                        //finds the id of the object based on shot number
                        for(ShotRecord r: records) {
                            if(r.shotNumber() == Integer.parseInt(parts[2])) {
                                //remove the object from the db
                                dbService.deleteShotRecord(r.id());
                                break;
                            }
                        }
                        //remove the object from the Adapter
                        arrayAdapter.remove(item);
                        arrayAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });

                //show the alert dialog
                AlertDialog alert = builder.create();
                alert.show();
                return false;
            }
        });
    }
}
