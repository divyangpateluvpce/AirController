/*
 * Presentation remote activity which has all the buttons and Perform clicks of all events.
 *
 * Developed By Divyang Patel
 */

package com.divyang.aircontroller.activity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.divyang.aircontroller.CommandQueue;
import com.divyang.aircontroller.R;
import com.divyang.aircontroller.controller.Command;
import com.divyang.aircontroller.controller.CommandType;
import com.divyang.aircontroller.controller.KeyValue;
import com.divyang.aircontroller.controller.MoveValue;

public class Presentation extends AppCompatActivity implements View.OnClickListener, SensorEventListener {
    private boolean cflag;
    private boolean lflag;
    private boolean aflag;
    private SensorManager sensorManager;
    Button btn_next,btn_pre,btn_bs,btn_ws,btn_clean,btn_hide,btn_pen,btn_pt,btn_hl,btn_esc,btn_full,btn_leftclick,btn_arrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presentation_activity);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        /*get instance of ACCELEROMETER and start sensor listening */
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME);

        /*Listen All click event on button*/
        btn_next= findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);

        btn_pre = findViewById(R.id.btn_pre);
        btn_pre.setOnClickListener(this);


        btn_bs = findViewById(R.id.btn_bs);
        btn_bs.setOnClickListener(this);

        btn_ws = findViewById(R.id.btn_ws);
        btn_ws.setOnClickListener(this);

        btn_clean = findViewById(R.id.btn_clean);
        btn_clean.setOnClickListener(this);

        btn_hide = findViewById(R.id.btn_hide);
        btn_hide.setOnClickListener(this);

        btn_hl = findViewById(R.id.btn_hl);
        btn_hl.setOnClickListener(this);

        btn_pt = findViewById(R.id.btn_pt);
        btn_pt.setOnClickListener(this);

        btn_leftclick = findViewById(R.id.btn_leftclick);
        btn_leftclick.setOnClickListener(this);

        btn_pen = findViewById(R.id.btn_pen);
        btn_pen.setOnClickListener(this);

        btn_full = findViewById(R.id.btn_full);
        btn_full.setOnClickListener(this);

        btn_esc = findViewById(R.id.btn_esc);
        btn_esc.setOnClickListener(this);

        btn_arrow = findViewById(R.id.btn_arrow);
        btn_arrow.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) { // Onclick event Actions.

        switch (view.getId()) {
            case R.id.btn_arrow: { // Start Sensor listening
                if(aflag == false) {
                    btn_arrow.setText("MOUSE MOVE ON");
                    aflag = true;
                }
                else {
                    btn_arrow.setText("MOUSE MOVE");
                    aflag = false;
                }
            }
            break;
            case R.id.btn_next: {
                CommandQueue.getInstance().push(Command.of(CommandType.KEYBOARD_INPUT, KeyValue.of(new KeyEvent(KeyEvent.ACTION_DOWN, 22)))); // next arrow click on keyboard
            }
            break;
            case R.id.btn_pre: {
                CommandQueue.getInstance().push(Command.of(CommandType.KEYBOARD_INPUT, KeyValue.of(new KeyEvent(KeyEvent.ACTION_DOWN, 21)))); // Previous Arrow click on keyboard.
            }
            break;
            case R.id.btn_bs: {
                    CommandQueue.getInstance().push(Command.of(CommandType.KEYBOARD_INPUT, KeyValue.of(new KeyEvent(KeyEvent.ACTION_DOWN, 900)))); // custom Key for BlackScreen.
            }
            break;
            case R.id.btn_ws: {
                CommandQueue.getInstance().push(Command.of(CommandType.KEYBOARD_INPUT, KeyValue.of(new KeyEvent(KeyEvent.ACTION_DOWN, 901))));// custom Key for WhiteScreen.
            }
            break;
            case R.id.btn_hide: {
                CommandQueue.getInstance().push(Command.of(CommandType.KEYBOARD_INPUT, KeyValue.of(new KeyEvent(KeyEvent.ACTION_DOWN, 902))));// custom Key for Hide Drawing.
            }
            break;
            case R.id.btn_clean: {
                CommandQueue.getInstance().push(Command.of(CommandType.KEYBOARD_INPUT, KeyValue.of(new KeyEvent(KeyEvent.ACTION_DOWN, 903))));// custom Key for Clean Drawing.
            }
            break;
            case R.id.btn_pen: {
                CommandQueue.getInstance().push(Command.of(CommandType.KEYBOARD_INPUT, KeyValue.of(new KeyEvent(KeyEvent.ACTION_DOWN, 904))));// custom Key for open Pen.
            }
            break;
            case R.id.btn_pt: {

                if(cflag == false){
                    cflag = true;
                    btn_pt.setText("POINTER ON");
                    CommandQueue.getInstance().push(Command.of(CommandType.KEYBOARD_INPUT, KeyValue.of(new KeyEvent(KeyEvent.ACTION_DOWN, 905)))); // custom Key for Start Pointer.
                }
                else{
                    btn_pt.setText("POINTER");
                    cflag = false;
                    CommandQueue.getInstance().push(Command.of(CommandType.KEYBOARD_INPUT, KeyValue.of(new KeyEvent(KeyEvent.ACTION_DOWN, 909)))); // custom Key for Stop Pointer.
                }

            }
            break;
            case R.id.btn_leftclick: {

                if(lflag == false){
                    btn_leftclick.setText("LEFT CLICK ON");
                    lflag = true;
                    CommandQueue.getInstance().push(Command.of(CommandType.KEYBOARD_INPUT, KeyValue.of(new KeyEvent(KeyEvent.ACTION_DOWN, 910)))); // custom Key for Press Mouse lift click.
                }
                else{
                    btn_leftclick.setText("LEFT CLICK");
                    lflag = false;
                    CommandQueue.getInstance().push(Command.of(CommandType.KEYBOARD_INPUT, KeyValue.of(new KeyEvent(KeyEvent.ACTION_DOWN, 909)))); // custom Key for Release Left Click.
                }

            }
            break;
            case R.id.btn_hl: {
                CommandQueue.getInstance().push(Command.of(CommandType.KEYBOARD_INPUT, KeyValue.of(new KeyEvent(KeyEvent.ACTION_DOWN, 906)))); // custom Key for Highlighter.
            }
            break;
            case R.id.btn_full: {
                CommandQueue.getInstance().push(Command.of(CommandType.KEYBOARD_INPUT, KeyValue.of(new KeyEvent(KeyEvent.ACTION_DOWN, 907))));// // custom Key for Full Screen.
            }
            break;
            case R.id.btn_esc: {
                CommandQueue.getInstance().push(Command.of(CommandType.KEYBOARD_INPUT, KeyValue.of(new KeyEvent(KeyEvent.ACTION_DOWN, 908)))); // custom Key for Press escape key .
            }
            break;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) { // Senser Change
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            if (aflag == true)
            getAccelerometer(sensorEvent);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    @Override
    protected void onResume() {
        super.onResume();
        // register this class as a listener for the orientation and
        // accelerometer sensors
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        // unregister listener
        super.onPause();
        sensorManager.unregisterListener(this);
        super.onStop();
    }

    private void getAccelerometer(SensorEvent event) { // Send New Difference to Server.
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        CommandQueue.getInstance().push(Command.of(CommandType.MOUSE_MOVE, MoveValue.of(x,y)));
    }
}
