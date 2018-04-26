package com.andersonlucier.android.metashot;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.andersonlucier.android.metashot.databaseservicelib.DatabaseShotService;
import com.andersonlucier.android.metashot.databaseservicelib.impl.MetaWear;
import com.andersonlucier.android.metashot.databaseservicelib.impl.ShotRecord;
import com.mbientlab.metawear.Data;
import com.mbientlab.metawear.MetaWearBoard;
import com.mbientlab.metawear.Route;
import com.mbientlab.metawear.Subscriber;
import com.mbientlab.metawear.android.BtleService;
import com.mbientlab.metawear.builder.RouteBuilder;
import com.mbientlab.metawear.builder.RouteComponent;
import com.mbientlab.metawear.builder.filter.Comparison;
import com.mbientlab.metawear.builder.filter.ThresholdOutput;
import com.mbientlab.metawear.builder.function.Function1;
import com.mbientlab.metawear.data.Acceleration;
import com.mbientlab.metawear.module.Accelerometer;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import bolts.Continuation;
import bolts.Task;

public class NewShotRecord_AutoRecord extends AppCompatActivity implements ServiceConnection {

    private BtleService.LocalBinder serviceBinder;
    private MetaWearBoard board;
    private Accelerometer accelerometer;
    private DatabaseShotService dbService;
    private EditText macAddress;
    private MetaWear dbMetaWearObject;
    private String shootingRecordId, shootingRecordTitle;
    private List<String> shotArrayList = new ArrayList<>();
    private List<ShotRecord> shotRecordList = new ArrayList<>();
    private ListView lv;
    private Button start, stop, connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_shot_record_auto_record);

        shootingRecordId = getIntent().getStringExtra("SHOOTING_RECORD_ID");
        shootingRecordTitle = getIntent().getStringExtra("SHOOTING_TITLE");

        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        connect = findViewById(R.id.connect);

        start.setBackgroundTintList(getResources().getColorStateList(R.color.darkGray));
        stop.setBackgroundTintList(getResources().getColorStateList(R.color.darkGray));

        lv = findViewById(R.id.shotRecordsList);

        dbService = new DatabaseShotService(this);
        dbMetaWearObject = dbService.getMetawear();

        macAddress = findViewById(R.id.metawearMac);
        macAddress.setText(dbMetaWearObject.macAddress());

    }
    public void onClickConnect(View view) {
        if(!dbMetaWearObject.macAddress().equals(macAddress.getText().toString()) ) {
            if(dbMetaWearObject.id() == null) {
                dbMetaWearObject = dbService.createMetawear(dbMetaWearObject);
            } else {
                dbMetaWearObject.setMacAddress(macAddress.getText().toString());
                dbMetaWearObject = dbService.updateMetawear(dbMetaWearObject);
            }
        }

        getApplicationContext().bindService(new Intent(this, BtleService.class),
                this, Context.BIND_AUTO_CREATE);
    }

    public void onClick (View view){
        board.tearDown();
        Intent intent;
        switch (view.getId()){
            case R.id.saveRecord:
                intent = new Intent(new Intent(NewShotRecord_AutoRecord.this, NewShotRecord.class));
                intent.putExtra("SHOOTING_RECORD_ID", shootingRecordId);
                intent.putExtra("SHOOTING_TITLE", shootingRecordTitle);
                startActivity(intent);
                break;
            case R.id.cancelRecord:
                intent = new Intent(new Intent(NewShotRecord_AutoRecord.this, NewShotRecord.class));
                intent.putExtra("SHOOTING_RECORD_ID", shootingRecordId);
                intent.putExtra("SHOOTING_TITLE", shootingRecordTitle);
                startActivity(intent);
                break;
            case R.id.goToHome:
                startActivity(new Intent(NewShotRecord_AutoRecord.this, MainActivity.class));
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        ///< Unbind the service when the activity is destroyed
        getApplicationContext().unbindService(this);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        serviceBinder = (BtleService.LocalBinder) service;
        retrieveBoard(macAddress.getText().toString());
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("result", "Activity");
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                double xValue = data.getDoubleExtra("XVALUE",0.0);
                double yValue = data.getDoubleExtra("YVALUE", 0.0);
                String shotId = data.getStringExtra("SHOT_RECORD_ID");

                ShotRecord record = dbService.getSingleShotsRecordsById(shotId);
                record.setTargetX(xValue);
                record.setTargetY(yValue);
                dbService.updateShotRecord(record);

            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("Metawear", "Cancelled saving coords");
            }
        }
    }

    public void addToList(ShotRecord newShot) {


        shotRecordList.add(newShot);
        shotArrayList.add("Shot - " + newShot.shotNumber());

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                shotArrayList );
        lv.setAdapter(arrayAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent(NewShotRecord_AutoRecord.this, NewShotRecord_ManualCreate.class);
                intent.putExtra("UPDATE", "True");
                intent.putExtra("SHOT_RECORD_ID", shotRecordList.get(position).id());
                startActivityForResult(intent, 1);
            }
        });

        Log.i("metashot", "Ading to list test");
    }

    public void showToastMessage(boolean success) {
        if (success) {
            Toast.makeText(this, "Connected to Metawear", Toast.LENGTH_LONG).show();
            start.setBackgroundTintList(getResources().getColorStateList(R.color.orange));
            stop.setBackgroundTintList(getResources().getColorStateList(R.color.orange));
            connect.setBackgroundTintList(getResources().getColorStateList(R.color.darkGray));
            connect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }});
        } else {
            Toast.makeText(this, "Unable to connect to Metawear, press cancel, and try again.", Toast.LENGTH_LONG).show();
        }
    }

    private void retrieveBoard(final String macAddr) {
        final BluetoothManager btManager=
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        final BluetoothDevice remoteDevice=
                btManager.getAdapter().getRemoteDevice(macAddr);

        // Create a MetaWear board object for the Bluetooth Device
        board= serviceBinder.getMetaWearBoard(remoteDevice);
        board.connectAsync().onSuccessTask(new Continuation<Void, Task<Route>>() {
            @Override
            public Task<Route> then(Task<Void> task) throws Exception {

                Log.i("MetaWear", "Connecting to " + macAddr);

                accelerometer = board.getModule(Accelerometer.class);
                accelerometer.configure()
                        .odr(60f)       // Set sampling frequency to 25Hz, or closest valid ODR
                        .commit();
                accelerometer.acceleration().addRouteAsync(new RouteBuilder() {
                    @Override
                    public void configure(RouteComponent source) {
                        source.map(Function1.RSS).average((byte) 4).filter(ThresholdOutput.BINARY, 1.0f)
                                .multicast()
                                    .to().filter(Comparison.EQ, -1).stream(new Subscriber() {
                                        @Override
                                        public void apply(Data data, Object... env) {
                                            Log.i("Metawear", "Shot Fired at " + data.formattedTimestamp());

                                            try {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        ShotRecord newShotRecord = new ShotRecord();
                                                        newShotRecord.setShootingRecordId(shootingRecordId);
                                                        newShotRecord = dbService.createShotRecord(newShotRecord);
                                                        addToList(newShotRecord);
                                                    }
                                                });
                                            }
                                            catch (Exception ex) {
                                                Log.i("Error", "Error : " + ex.getMessage());
                                            }


                                        }
                                    }).end();
                    }
                }).continueWith(new Continuation<Route, Void>() {
                    @Override
                    public Void then(Task<Route> task) throws Exception {
                        if(task.isFaulted()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showToastMessage(false);
                                }
                            });
                        } else {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showToastMessage(true);
                                }
                            });


                            Log.i("Metawear", "Metawear ready to start " + dbMetaWearObject.macAddress());
                            findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    accelerometer.acceleration().start();
                                    accelerometer.start();
                                }
                            });
                            findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    accelerometer.stop();
                                    accelerometer.acceleration().stop();

                                }
                            });

                        }
                        return null;
                    }
                });
                return null;
            }
        });
    }
}
