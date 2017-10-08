package com.eshtio.chatfuel.elevator;

import com.eshtio.chatfuel.elevator.core.Elevator;
import com.eshtio.chatfuel.elevator.core.ElevatorCommandExecutor;
import com.eshtio.chatfuel.elevator.core.ElevatorCommandRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Elevator controller, controls execution of elevator commands
 */
public class ElevatorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElevatorController.class);

    /** Elevator info */
    private final Elevator elevator;
    /** Command repository */
    private final ElevatorCommandRepository repository;
    /** Command priority executor */
    private final ElevatorCommandExecutor executor;

    /** Максимальное количество этажей 5 - 20 */
    private final int maxFloor;

    public ElevatorController(Elevator elevator,
                              ElevatorCommandExecutor executor,
                              ElevatorCommandRepository repository, int maxFloor) {
        this.elevator = elevator;
        this.maxFloor = maxFloor;
        this.repository = repository;
        this.executor = executor;
    }

    /**
     * Add external (from the floor) command to elevator command repository
     *
     * @param floor target floor
     */
    public void pressButtonOutside(int floor) {
        checkFloor(floor);
        LOGGER.debug("Press button from floor {}", floor);
        repository.addExternal(floor);
    }

    /**
     * Add internal (from the elevator) command to elevator command repository
     *
     * @param floor target floor
     */
    public void pressButtonInside(int floor) {
        checkFloor(floor);
        LOGGER.debug("Press button '{}' inside elevator", floor);
        repository.addInternal(floor);
    }

    /**
     * Execute priority elevator step
     */
    public void nextElevatorStep() {
        executePriorityCommand();
    }

    /**
     * Execute all elevator commands
     */
    public void executeAllCommands() {
        LOGGER.debug("Executing all commands...");
        while (repository.hasCommands()) {
            executePriorityCommand();
        }
        LOGGER.debug("All commands was executed");
    }

    /**
     * Execute priority elevator command
     */
    private void executePriorityCommand() {
        LOGGER.debug("Execute priority command with movement state: {}", elevator.getMovementState());
        switch (elevator.getMovementState()) {
            case UP:
                executor.executeWithUpInternalPriority();
                break;
            case DOWN:
                executor.executeWithDownExternalPriority();
                break;
            case STOP:
                executor.executeFirstCommand();
                break;
            default: throw new IllegalArgumentException(
                    "Illegal elevator movement state " + elevator.getMovementState());
        }
    }

    /**
     * Validate floor number
     *
     * @param floor floor number
     * @throws IllegalArgumentException if floor is incorrect
     */
    private void checkFloor(int floor) throws IllegalArgumentException {
        if (floor < 1 || floor > maxFloor) {
            throw new IllegalArgumentException("Illegal floor " + floor);
        }
    }

}
