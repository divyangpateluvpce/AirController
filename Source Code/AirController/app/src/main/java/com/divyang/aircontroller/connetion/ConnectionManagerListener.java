/*
 * Interface for connection listing.
 *
 * Developed By Divyang Patel
 */
package com.divyang.aircontroller.connetion;

public interface ConnectionManagerListener {
    void onConnected();

    void onDisconnected();

    void onConnectionFailed();
}
