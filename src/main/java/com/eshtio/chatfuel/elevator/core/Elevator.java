package com.eshtio.chatfuel.elevator.core;

import com.eshtio.chatfuel.elevator.core.exception.ElevatorMoveException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * Elevator box, can do base commands (up, down, open and close doors, stop)
 */
public class Elevator {

    private static final Logger LOGGER = LoggerFactory.getLogger(Elevator.class);

    /** Delay time (speed emulation) */
    private final long timePerFloorMills;

    /** Elevator direction for movement */
    private ElevatorMovementState movementState = ElevatorMovementState.STOP;
    /** Elevator current floor */
    private int currentFloor;

    public Elevator(int currentFloor, BigDecimal floorHeight, BigDecimal speed) {
        this.timePerFloorMills = (long) (floorHeight.divide(speed, 4).doubleValue() * 1000);
        this.currentFloor = currentFloor;
    }

    public ElevatorMovementState getMovementState() {
        return movementState;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    void stop() {
        movementState = ElevatorMovementState.STOP;
        LOGGER.info("Stop on {}", currentFloor);
    }

    void openDoors() {
        LOGGER.info("Open door on {}", currentFloor);
    }

    void closeDoors() {
        LOGGER.info("Close door on {}", currentFloor);
    }

    void up() {
        movementState = ElevatorMovementState.UP;
        LOGGER.info("Up to {}", currentFloor + 1);
        makeMove();
        currentFloor++;
        LOGGER.debug("Movement done. Current floor: {}", currentFloor);
    }

    void down() {
        movementState = ElevatorMovementState.DOWN;
        LOGGER.info("Down to {}", currentFloor - 1);
        makeMove();
        currentFloor--;
        LOGGER.debug("Movement done. Current floor: {}", currentFloor);
    }

    private void makeMove() {
        try {
            Thread.sleep(timePerFloorMills);
        } catch (InterruptedException e) {
            throw new ElevatorMoveException(e);
        }
    }

}
