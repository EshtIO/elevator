package com.eshtio.chatfuel.elevator.core.exception;

public abstract class ElevatorRuntimeException extends RuntimeException {
    public ElevatorRuntimeException(InterruptedException ex) {
        super(ex);
    }
}
