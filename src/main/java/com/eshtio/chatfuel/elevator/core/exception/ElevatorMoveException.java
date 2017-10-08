package com.eshtio.chatfuel.elevator.core.exception;

public class ElevatorMoveException extends ElevatorRuntimeException {
    public ElevatorMoveException(InterruptedException ex) {
        super(ex);
    }
}
