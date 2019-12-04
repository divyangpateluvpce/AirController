/*
* Main Activity which has a mouse key board, presentation and setting button.
*
* Developed By Divyang Patel
*/
package com.divyang.aircontroller.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.divyang.aircontroller.CommandQueue;
import com.divyang.aircontroller.GestureTap;
import com.divyang.aircontroller.R;
import com.divyang.aircontroller.Settings;
import com.divyang.aircontroller.connetion.MessageSender;
import com.divyang.aircontroller.connetion.ConnectionManager;
import com.divyang.aircontroller.connetion.ConnectionManagerListener;
import com.divyang.aircontroller.controller.Command;
import com.divyang.aircontroller.controller.CommandType;
import com.divyang.aircontroller.controller.KeyValue;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener, DialogInterface.OnClickListener, ConnectionManagerListener {

    private String TAG = MainActivity.class.getCanonicalName();
    private EditText hostEditText;
    private EditText portEditText;
    private boolean connected;
    private Context context;
    private GestureDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;
        detector = new GestureDetector(this, new GestureTap());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        } // hide action bar

        Button settings = findViewById(R.id.settings_button);
        settings.setOnClickListener(this);
        settings.performClick(); // open dialog box

        Button keyboardSwitch = findViewById(R.id.keyboard_toggle);
        keyboardSwitch.setOnClickListener(this);

        Button presentationSwitch = findViewById(R.id.btn_presentation);
        presentationSwitch.setOnClickListener(this);

        LinearLayout touchscreen = findViewById(R.id.touch_screen);
        touchscreen.setOnTouchListener(this);

        MessageSender.getInstance().init();
        ConnectionManager.getInstanceOf().addConnectionManagerListener(this);
    }

    @Override
    public void onClick(View view) {
        String msg = String.format("click is happening on %s", view.getId());
        switch (view.getId()) {

            case R.id.btn_presentation: {
                if(connected){
                Intent i = new Intent(MainActivity.this, Presentation.class);
                startActivity(i);
                } // open presentation activity
                else {
                    Toast.makeText(getApplicationContext(), "Please connect with server", Toast.LENGTH_SHORT).show();
                }
            }
            break;
            case R.id.keyboard_toggle: {
                toggleSoftKeyboard();
            }
            break;
            case R.id.settings_button: {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_settings, null);
                hostEditText = dialogView.findViewById(R.id.button_host);
                portEditText = dialogView.findViewById(R.id.button_port);

                if (Settings.getInstanceOf().getHost() != null && !Settings.getInstanceOf().getHost().isEmpty()) {
                    hostEditText.setText(Settings.getInstanceOf().getHost());
                }
                if (Settings.getInstanceOf().getPort() != 0) {
                    portEditText.setText(String.format("%s", Settings.getInstanceOf().getPort()));
                }

                if (connected) {
                    builder.setNegativeButton("Disconnect", this);
                } else {
                    builder.setPositiveButton("Connect", this);
                }
                builder.setNeutralButton("Cancel", this);
                builder.setView(dialogView);
                builder.setCancelable(false);
                builder.show();
            }
            break;
        } // create dialog box and show it.
        Log.i(TAG, msg);
    }

    private void toggleSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }// call inbuilt keyboard.

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (connected) {
            CommandQueue.getInstance().push(Command.of(CommandType.KEYBOARD_INPUT, KeyValue.of(event)));// send which key press from keyboard.
           }
      return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        if (connected) {
            detector.onTouchEvent(motionEvent); // detect touch event and call method from Gesture Tap
        }

        return true;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        Log.i(TAG, "I: " + i);
        switch (i) {
            case -1: {
                // positive
                String host = hostEditText.getText().toString();
                String portString = portEditText.getText().toString();
                if (host.isEmpty() || portString.isEmpty()) {
                    return;
                }
                int port = Integer.parseInt(portString);
                Settings.getInstanceOf().setHost(host);
                Settings.getInstanceOf().setPort(port);
                ConnectionManager.getInstanceOf().attemptConnection();
            } // get host ip address and port and perform connection using Connection Manager
            break;
            case -2: {
                // negative
                ConnectionManager.getInstanceOf().disconnect(); // disconnect with the server.
            }
            break;
        }
    }

    @Override
    public void onConnected() { // when connect with server
        connected = true;

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "Connected with AirController Server.", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDisconnected() { // when disconnect with server.
        connected = false;
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "Disconnected with AirController Server.", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onConnectionFailed() { // when server not found.
        connected = false;
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "Not connected with AirController Server.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
