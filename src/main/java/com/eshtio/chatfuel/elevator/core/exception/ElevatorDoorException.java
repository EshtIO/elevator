package com.eshtio.chatfuel.elevator.core.exception;

public class ElevatorDoorException extends ElevatorRuntimeException {
    public ElevatorDoorException(InterruptedException ex) {
        super(ex);
    }
}
