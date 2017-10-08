package com.eshtio.chatfuel.elevator.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Internal and external command repository
 */
public class ElevatorCommandRepository {

    /** Command list in order of addition */
    private final List<ElevatorCommand> elevatorCommands;

    public ElevatorCommandRepository() {
        this.elevatorCommands = new ArrayList<>();
    }

    public void addExternal(int floor) {
        elevatorCommands.add(ElevatorCommand.external(floor));
    }

    public void addInternal(int floor) {
        elevatorCommands.add(ElevatorCommand.internal(floor));
    }

    boolean remove(int floor) {
        return elevatorCommands.removeIf(command -> command.getFloor() == floor);
    }

    boolean removeInternal(int floor) {
        return elevatorCommands.removeIf(command -> command.isInternal() && command.getFloor() == floor);
    }

    boolean removeExternal(int floor) {
        return elevatorCommands.removeIf(command -> command.isExternal() && command.getFloor() == floor);
    }

    boolean hasExternalLessThan(int floor) {
        return elevatorCommands.stream()
                .filter(command -> command.isExternal() && command.getFloor() < floor)
                .mapToInt(ElevatorCommand::getFloor)
                .min()
                .isPresent();
    }

    boolean hasInternalGreaterThan(int floor) {
        return elevatorCommands.stream()
                .filter(command -> command.isInternal() && command.getFloor() > floor)
                .mapToInt(ElevatorCommand::getFloor)
                .max()
                .isPresent();
    }

    public boolean hasCommands() {
        return !elevatorCommands.isEmpty();
    }

    ElevatorCommand peekFirst() {
        return elevatorCommands.isEmpty() ? null : elevatorCommands.get(0);
    }

}