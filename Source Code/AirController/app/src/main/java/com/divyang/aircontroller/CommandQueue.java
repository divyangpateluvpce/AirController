/*
 * Array for all commands. and add to in list.
 *
 * Developed By Divyang Patel
 */package com.divyang.aircontroller;

import android.util.Log;

import com.divyang.aircontroller.controller.Command;

import java.util.ArrayList;
import java.util.List;

public class CommandQueue {
    private static final String TAG = CommandQueue.class.getCanonicalName();

    private CommandQueue(){}

    private static final CommandQueue instanceOf = new CommandQueue();

    public static CommandQueue getInstance() {
        return instanceOf;
    }

    private static final List<Command> commands = new ArrayList<>();

    public void push(Command command) {
        commands.add(command);
        Log.d(TAG, command.toString());
    }

    public Command pop() {
        if(commands.size() == 0) {
            return Command.noOp();
        }
        return commands.remove(0);
    }
}
