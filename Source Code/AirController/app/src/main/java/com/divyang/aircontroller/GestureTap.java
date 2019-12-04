/*
 * GestureTap class is for Tap detection on screen and perform action according to that.
 *
 * Developed By Divyang Patel
 */
package com.divyang.aircontroller;

import android.view.GestureDetector;
import android.view.MotionEvent;

import com.divyang.aircontroller.controller.Command;
import com.divyang.aircontroller.controller.CommandType;
import com.divyang.aircontroller.controller.MoveValue;

public class GestureTap extends GestureDetector.SimpleOnGestureListener {

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) { // move finger on screen.
        if(e2.getPointerCount() == 1){
            CommandQueue.getInstance().push(Command.of(CommandType.MOUSE_MOVE, MoveValue.of(distanceX, distanceY)));
        } else {
            CommandQueue.getInstance().push(Command.of(CommandType.MOUSE_SCROLL, MoveValue.of(distanceX, distanceY)));
        }
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) { // on double click.
        CommandQueue.getInstance().push(Command.of(CommandType.MOUSE_DOUBLE_CLICK, null));
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) { // on long press open left click.
        super.onLongPress(e);
        CommandQueue.getInstance().push(Command.of(CommandType.MOUSE_RIGHT_CLICK, null));
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) { // on single Tap.
        CommandQueue.getInstance().push(Command.of(CommandType.MOUSE_LEFT_CLICK, null));
        return true;
    }
}
