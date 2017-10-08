package com.eshtio.chatfuel.elevator.core;

/**
 * Elevator command class, can be internal or external
 */
class ElevatorCommand {

    /** Internal command type */
    private static final String INTERNAL_TYPE = "internal";
    /** External command type */
    private static final String EXTERNAL_TYPE = "external";

    /** Target floor */
    private int floor;
    /** Command type */
    private String type;

    private ElevatorCommand(int floor, String type) {
        this.floor = floor;
        this.type = type;
    }

    boolean isInternal() {
        return INTERNAL_TYPE.equals(type);
    }

    boolean isExternal() {
        return EXTERNAL_TYPE.equals(type);
    }

    static ElevatorCommand internal(int floor) {
        return new ElevatorCommand(floor, INTERNAL_TYPE);
    }

    static ElevatorCommand external(int floor) {
        return new ElevatorCommand(floor, EXTERNAL_TYPE);
    }

    int getFloor() {
        return floor;
    }

}