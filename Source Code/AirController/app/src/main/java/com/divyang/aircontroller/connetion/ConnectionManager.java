/*
 * Manage connect with server.
 *
 * Developed By Divyang Patel
 */
package com.divyang.aircontroller.connetion;

import android.util.Log;

import com.divyang.aircontroller.Settings;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class ConnectionManager {
    private static final String TAG = ConnectionManager.class.getCanonicalName();

    private ConnectionState state;

    private final List<ConnectionManagerListener> listeners = new ArrayList<>();

    private static final ConnectionManager instanceOf = new ConnectionManager();
    private PrintWriter pw;

    private ConnectionManager() {}

    public static ConnectionManager getInstanceOf() {
        return instanceOf;
    }

    public void attemptConnection() {
        if(state == ConnectionState.CONNECTING || state == ConnectionState.CONNECTED) {
            return;
        }

        final String host = Settings.getInstanceOf().getHost();
        final int port = Settings.getInstanceOf().getPort();

        Log.i(TAG, String.format("Attempting connection. Results will be communicated to %s listeners", listeners.size()));
        this.state = ConnectionState.CONNECTING;
        final ConnectionManager that = this;

        new Thread(new Runnable() {
            @Override
            public void run() {
                int attemptCounter = 0;
                boolean connected = false;
                do {
                    attemptCounter++;
                    try {
                        Socket socket = new Socket(host, port);
                        pw = new PrintWriter(socket.getOutputStream(), true);
                        connected = true;
                    } catch (IOException e) {
                        Log.e(TAG, "Connection failed, reattempting in 2 seconds.");
                    }
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        Log.e(TAG,"Could not sleep for 2 seconds. ");
                    }
                } while(!connected && attemptCounter < 5);

                Log.i(TAG, String.format("Connected: %s, attempted: %d, informing %s listeners", connected, attemptCounter, listeners.size()));

                if(connected) {
                    that.state = ConnectionState.CONNECTED;
                    for(ConnectionManagerListener l : listeners) {
                        l.onConnected();
                    }
                } else {
                    that.state = ConnectionState.FAILED;
                    for(ConnectionManagerListener l : listeners) {
                        l.onConnectionFailed();
                    }
                }
            }
        }).start();
    }

    public void addConnectionManagerListener(ConnectionManagerListener listener) {
        listeners.add(listener);
        Log.i(TAG, String.format("Added %s listeners", listeners.size()));
    }

    public void sendMessage(String string) {
        pw.println(string);
    }

    public void disconnect() {
        state = ConnectionState.DISCONNECTED;
        for(ConnectionManagerListener l : listeners) {
            l.onDisconnected();
        }
    }
}
