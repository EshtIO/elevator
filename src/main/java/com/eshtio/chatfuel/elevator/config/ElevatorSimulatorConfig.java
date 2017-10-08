package com.eshtio.chatfuel.elevator.config;

import com.beust.jcommander.Parameter;

import java.math.BigDecimal;

/**
 * Main elevator configuration
 */
public class ElevatorSimulatorConfig {

    /** Number of floors in the building */
    @Parameter(names = {"-f", "--floors"}, description = "Floors count (>=5 && <= 20)",
            validateWith = FloorsCountValidator.class)
    private int floorsCount = 20;

    /** Height of one floor */
    @Parameter(names = {"-h", "--height"}, description = "Floor height (m)")
    private BigDecimal floorHeight = new BigDecimal("2.8");

    /** Elevator speed */
    @Parameter(names = {"-s", "--speed"}, description = "Lift speed (m/s)")
    private BigDecimal speed = new BigDecimal("2.0");

    /** Time between opening and closing doors */
    @Parameter(names = {"-d", "--door"}, description = "Time between opening and closing doors (ms)")
    private long openDoorTime = 1000;

    public int getFloorsCount() {
        return floorsCount;
    }

    public void setFloorsCount(int floorsCount) {
        this.floorsCount = floorsCount;
    }

    public BigDecimal getFloorHeight() {
        return floorHeight;
    }

    public void setFloorHeight(BigDecimal floorHeight) {
        this.floorHeight = floorHeight;
    }

    public BigDecimal getSpeed() {
        return speed;
    }

    public void setSpeed(BigDecimal speed) {
        this.speed = speed;
    }

    public long getOpenDoorTime() {
        return openDoorTime;
    }

    public void setOpenDoorTime(long openDoorTime) {
        this.openDoorTime = openDoorTime;
    }

    @Override
    public String toString() {
        return "ElevatorSimulatorConfig{" +
                "floorsCount = " + floorsCount +
                ", floorHeight (m) = " + floorHeight +
                ", speed (m/s) = " + speed +
                ", openDoorTime (mills)= " + openDoorTime +
                '}';
    }
}