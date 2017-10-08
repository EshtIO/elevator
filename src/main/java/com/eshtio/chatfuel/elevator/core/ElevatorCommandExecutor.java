package com.eshtio.chatfuel.elevator.core;

import com.eshtio.chatfuel.elevator.core.exception.ElevatorDoorException;

/**
 * Elevator command executor
 */
public class ElevatorCommandExecutor {

    /** Command repository */
    private final ElevatorCommandRepository repository;
    /** Elevator box */
    private final Elevator elevator;
    /** Time between opening and closing doors */
    private final long openDoorTime;

    public ElevatorCommandExecutor(ElevatorCommandRepository repository, Elevator elevator, long openDoorTime) {
        this.repository = repository;
        this.elevator = elevator;
        this.openDoorTime = openDoorTime;
    }

    /**
     * Execute command with priority "Down-External".
     * Priority rule (first - top priority):
     *  - External commands from current floor
     *  - External commands from floor < current
     *  - Commands in order of addition (queue)
     */
    public void executeWithDownExternalPriority() {
        Integer currentFloor = elevator.getCurrentFloor();
        if (repository.removeExternal(currentFloor)) {
            repository.removeInternal(currentFloor);
            openAndCloseElevatorDoor();
            if (repository.hasExternalLessThan(currentFloor)) {
                elevator.down();
            } else {
                executeFirstCommand();
            }
        } else if (repository.hasExternalLessThan(currentFloor)) {
            elevator.down();
        } else {
            executeFirstCommand();
        }
    }

    /**
     * Execute command with priority "Up-Internal".
     * Priority rule (first - top priority):
     *  - Internal commands from current floor
     *  - Internal commands from floor > current
     *  - Commands in order of addition (queue)
     */
    public void executeWithUpInternalPriority() {
        Integer currentFloor = elevator.getCurrentFloor();
        if (repository.removeInternal(currentFloor)) {
            repository.removeExternal(currentFloor);
            openAndCloseElevatorDoor();
            if (repository.hasInternalGreaterThan(currentFloor)) {
                elevator.up();
            } else {
                executeFirstCommand();
            }
        } else if (repository.hasInternalGreaterThan(currentFloor)) {
            elevator.up();
        } else {
            executeFirstCommand();
        }
    }

    /**
     * Execute command in order of addition (queue)
     */
    public void executeFirstCommand() {
        if (repository.hasCommands()) {
            Integer currentFloor = elevator.getCurrentFloor();
            int target = repository.peekFirst().getFloor();
            if (target > currentFloor) {
                elevator.up();
            } else if (target < currentFloor) {
                elevator.down();
            } else {
                openAndCloseElevatorDoor();
                repository.remove(currentFloor);
            }
        } else {
            elevator.stop();
        }
    }

    /**
     * Open and close elevator door
     */
    private void openAndCloseElevatorDoor() {
        try {
            elevator.openDoors();
            Thread.sleep(openDoorTime);
            elevator.closeDoors();
        } catch (InterruptedException ex) {
            throw new ElevatorDoorException(ex);
        }
    }

}
