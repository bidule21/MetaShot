package com.andersonlucier.android.metashot;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.andersonlucier.android.metashot.databaseservicelib.DatabaseShotService;
import com.andersonlucier.android.metashot.databaseservicelib.impl.ShootingRecord;
import com.andersonlucier.android.metashot.databaseservicelib.impl.ShotRecord;
import java.util.ArrayList;
import java.util.List;

public class ViewPreviousShootingRecords_Single extends AppCompatActivity {

    private DatabaseShotService dbService;
    private List<ShotRecord> records;
    private ShootingRecord shootingRecord;
    private String shootingRecordId;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_previous_shooting_records_single);
        dbService = new DatabaseShotService(this);
        shootingRecordId = getIntent().getStringExtra("RECORD_ID");

        //populate the shooting information and shot records
        populateShootingInformation();
        populateShotRecords();
    }

    public void onClick(View view){
        switch (view.getId()) {
            case R.id.goToHome:
                startActivity(new Intent(ViewPreviousShootingRecords_Single.this, MainActivity.class));
                break;
            case R.id.addShooting:
                //redirect the user to the new shot record layout to add more shots.
                Intent intent = new Intent(new Intent(ViewPreviousShootingRecords_Single.this, NewShotRecord.class));
                intent.putExtra("SHOOTING_RECORD_ID", shootingRecordId);
                intent.putExtra("SHOOTING_TITLE", shootingRecord.title());
                startActivity(intent);
                break;
        }
    }

    private void populateShootingInformation() {
        //gets the record from the db
        shootingRecord = dbService.getSingleShootingRecord(shootingRecordId);

        //sets the title
        TextView title = findViewById(R.id.title);
        title.setText(String.format("Title: %s", shootingRecord.title()));

        //formats and sets the date
        String pattern = "MMM dd, yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String dateformat = simpleDateFormat.format(shootingRecord.datetime());
        TextView date = findViewById(R.id.datetime);
        date.setText(String.format("Date: %s", dateformat));

        //sets the location
        TextView location = findViewById(R.id.location);
        location.setText(String.format("Location: %s", shootingRecord.location()));

        //sets the weather
        TextView weather = findViewById(R.id.weather);
        weather.setText(String.format("Weather: %s", shootingRecord.weather()));

        //sets the description
        TextView description = findViewById(R.id.description);
        description.setText(String.format("Description: %s", shootingRecord.description()));

        //sets the gun
        TextView guntype = findViewById(R.id.guntype);
        if(shootingRecord.typeOfGun() != null) {
            guntype.setText(String.format("Gun Used: %s", shootingRecord.typeOfGun().gunName()));
        } else {
            guntype.setText(String.format("Gun Used: No Weapon Selected."));
        }


    }

    private void populateShotRecords() {
        ListView lv = findViewById(R.id.previousShotRecordsList);

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
                Intent intent = new Intent(new Intent(ViewPreviousShootingRecords_Single.this, ViewPreviousShotRecord.class));
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
