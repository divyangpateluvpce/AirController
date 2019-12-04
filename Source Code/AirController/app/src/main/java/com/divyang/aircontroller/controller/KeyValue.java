/*
 * Generate Keyboard and Mouse Key press event value and Make Json and send for CommandValue.
 *
 * Developed By Divyang Patel
 */
package com.divyang.aircontroller.controller;

import android.view.KeyEvent;

public class KeyValue implements CommandValue {
    private final int keyCode;
    private boolean isShiftPressed;

    private KeyValue(int keyCode) {
        this.keyCode = keyCode;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public boolean isShiftPressed() {
        return isShiftPressed;
    }

    public static KeyValue of(KeyEvent event) {
        int keyChar = event.getUnicodeChar();
        KeyValue value = new KeyValue(keyChar);
        if (keyChar == 0) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_DEL: {
                    value = new KeyValue(KeyEvent.KEYCODE_DEL);
                }
                break;
                case KeyEvent.KEYCODE_DPAD_LEFT: {
                    value = new KeyValue(KeyEvent.KEYCODE_DPAD_LEFT);
                }
                break;
                case KeyEvent.KEYCODE_DPAD_RIGHT: {
                    value = new KeyValue(KeyEvent.KEYCODE_DPAD_RIGHT);
                }
                break;
                case 900: {
                    value = new KeyValue(900);
                }
                break;
                case 901: {
                    value = new KeyValue(901);
                }
                break;
                case 902: {
                    value = new KeyValue(902);
                }
                break;
                case 903: {
                    value = new KeyValue(903);
                }
                break;
                case 904: {
                    value = new KeyValue(904);
                }
                break;
                case 905: {
                    value = new KeyValue(905);
                }
                break;
                case 906: {
                    value = new KeyValue(906);
                }
                break;
                case 907: {
                    value = new KeyValue(907);
                }
                break;
                case 908: {
                    value = new KeyValue(908);
                }
                break;
                case 909: {
                    value = new KeyValue(909);
                }
                break;
                case 910: {
                    value = new KeyValue(910);
                }
                break;
            }
        }
        value.isShiftPressed = event.isShiftPressed();
        return value;
    }

    @Override
    public String toString() {
        return String.format("{\"keyCode\": %s, \"shift\": %s}",
                getKeyCode(), isShiftPressed());
    }
}
