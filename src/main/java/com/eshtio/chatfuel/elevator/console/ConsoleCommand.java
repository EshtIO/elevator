package com.eshtio.chatfuel.elevator.console;

/**
 * Console command for simulator
 */
public class ConsoleCommand {

    /** Console command type */
    private final ConsoleCommandType type;

    /** Floor number in command (default -1 is incorrect floor) */
    private int floor = -1;

    ConsoleCommand(ConsoleCommandType type) {
        this.type = type;
    }

    ConsoleCommand(ConsoleCommandType type, int floor) {
        this.type = type;
        this.floor = floor;
    }

    public ConsoleCommandType getType() {
        return type;
    }

    public int getFloor() {
        return floor;
    }

    @Override
    public String toString() {
        return "ConsoleCommand{" +
                "type=" + type +
                ", floor=" + floor +
                '}';
    }
}
