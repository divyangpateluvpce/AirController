/*
 * Generate JSON message to send to server
 * like Type of Event and all values.
 *
 * Developed By Divyang Patel
 */
package com.divyang.aircontroller.controller;

public class Command {
    private final CommandType type;
    private final CommandValue value;


    private Command(CommandType type, CommandValue value){
        this.type = type;
        this.value = value;
    }

    public CommandType getType() {
        return type;
    }

    public CommandValue getValue() {
        return value;
    }

    public static Command noOp() {
        return new Command(CommandType.NO_OP, null);
    }

    public static Command of(CommandType type, CommandValue value) {
        return new Command(type, value);
    }

    @Override
    public String toString() {
        return String.format("{\"type\": \"%s\", \"value\": %s}", getType(), getValue());
    }
}
