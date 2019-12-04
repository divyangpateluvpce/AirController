/*
 * Check connectiona and send the Message to Server.
 *
 * Developed By Divyang Patel
 */
package com.divyang.aircontroller.connetion;

import android.util.Log;

import com.divyang.aircontroller.controller.Command;
import com.divyang.aircontroller.controller.CommandType;
import com.divyang.aircontroller.CommandQueue;

public class MessageSender implements ConnectionManagerListener {

    private static final String TAG = MessageSender.class.getCanonicalName();

    private static MessageSender instanceOf = new MessageSender();
    private boolean connected = false;

    private MessageSender() {
        ConnectionManager.getInstanceOf().addConnectionManagerListener(this);
    }

    public static MessageSender getInstance() {
        return instanceOf;
    }

    @Override
    public void onConnected() {
        Log.i(TAG, "onConnected");
        connected = true;
    }

    @Override
    public void onDisconnected() {
        Log.i(TAG, "onDisconnect");
        connected = false;
    }

    @Override
    public void onConnectionFailed() {
        Log.i(TAG, "onConnectionFailed");
        connected = false;
    }

    public void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    if(!connected) {
                        continue;
                    }
                    Command command = CommandQueue.getInstance().pop();
                    if(command == null || command.getType() == null || command.getType().equals(CommandType.NO_OP)) {
                        continue;
                    }
                    ConnectionManager.getInstanceOf().sendMessage(command.toString());
                }
            }
        }).start();
    }
}
