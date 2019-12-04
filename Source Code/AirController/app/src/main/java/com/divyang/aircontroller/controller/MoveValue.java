/*
 * Generate Json for mouse Move with dx, dy values.
 *
 * Developed By Divyang Patel
 */
package com.divyang.aircontroller.controller;

public class MoveValue implements CommandValue {
    private final float x;
    private final float y;

    private MoveValue(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public static MoveValue of(float x, float y) {
        return new MoveValue(x, y);
    }

    @Override
    public String toString() {
        return String.format("{\"x\": %s, \"y\": %s}", getX(), getY());
    }
}
