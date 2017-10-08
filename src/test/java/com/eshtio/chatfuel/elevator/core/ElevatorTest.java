package com.eshtio.chatfuel.elevator.core;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * Elevator test
 */
public class ElevatorTest {

    /** Tested elevator instance */
    private Elevator testedObject;

    @Before
    public void setUp() throws Exception {
        testedObject = new Elevator(5, new BigDecimal("2.5"), new BigDecimal("5"));
    }

    @Test(timeout = 1000)
    public void moveSpeedUp() throws Exception {
        testedObject.up();
    }

    @Test(timeout = 1000)
    public void moveSpeedDown() throws Exception {
        testedObject.down();
    }

    @Test
    public void up() throws Exception {
        testedObject.up();
        assertEquals(6, testedObject.getCurrentFloor());
        assertEquals(ElevatorMovementState.UP, testedObject.getMovementState());
    }

    @Test
    public void down() throws Exception {
        testedObject.down();
        assertEquals(4, testedObject.getCurrentFloor());
        assertEquals(ElevatorMovementState.DOWN, testedObject.getMovementState());
    }

    @Test
    public void stop() throws Exception {
        testedObject.stop();
        assertEquals(5, testedObject.getCurrentFloor());
        assertEquals(ElevatorMovementState.STOP, testedObject.getMovementState());
    }

    @Test
    public void openDoors() throws Exception {
        testedObject.openDoors();
        assertEquals(5, testedObject.getCurrentFloor());
        assertEquals(ElevatorMovementState.STOP, testedObject.getMovementState());
    }

    @Test
    public void closeDoors() throws Exception {
        testedObject.closeDoors();
        assertEquals(5, testedObject.getCurrentFloor());
        assertEquals(ElevatorMovementState.STOP, testedObject.getMovementState());
    }
}