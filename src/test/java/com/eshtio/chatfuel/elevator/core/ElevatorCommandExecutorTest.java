package com.eshtio.chatfuel.elevator.core;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Test for {@link ElevatorCommandExecutor}
 */
public class ElevatorCommandExecutorTest {

    /** Command repository */
    private ElevatorCommandRepository repository;

    /**
     * Clear repository before every test
     */
    @Before
    public void setUp() {
        repository = new ElevatorCommandRepository();
    }

    @Test
    public void withDownExternalPriority() {
        Elevator elevator = createSpyElevator(5);
        ElevatorCommandExecutor testedObject =
                new ElevatorCommandExecutor(repository, elevator, 1);

        testedObject.executeWithDownExternalPriority();
        verify(elevator, times(1)).stop();
        assertFalse(repository.hasCommands());

        repository.addExternal(5);
        testedObject.executeWithDownExternalPriority();
        verify(elevator, times(2)).stop();
        verify(elevator).openDoors();
        verify(elevator).closeDoors();
        assertFalse(repository.hasCommands());

        repository.addExternal(4);
        repository.addExternal(6);
        testedObject.executeWithDownExternalPriority();
        verify(elevator).down();
        assertTrue(repository.hasCommands());
    }

    @Test
    public void withUpInternalPriority() {
        Elevator elevator = createSpyElevator(5);
        ElevatorCommandExecutor testedObject =
                new ElevatorCommandExecutor(repository, elevator, 1);

        testedObject.executeWithUpInternalPriority();
        verify(elevator, times(1)).stop();
        assertFalse(repository.hasCommands());

        repository.addInternal(5);
        testedObject.executeWithUpInternalPriority();
        verify(elevator, times(2)).stop();
        verify(elevator).openDoors();
        verify(elevator).closeDoors();
        assertFalse(repository.hasCommands());

        repository.addInternal(4);
        repository.addInternal(6);
        testedObject.executeWithUpInternalPriority();
        verify(elevator).up();
        assertTrue(repository.hasCommands());
    }

    @Test
    public void firstCommand() {
        Elevator spyElevator = createSpyElevator(1);

        ElevatorCommandExecutor testedObject =
                new ElevatorCommandExecutor(repository, spyElevator, 1);

        // execute with empty repository
        testedObject.executeFirstCommand();
        verify(spyElevator).stop();
        assertFalse(repository.hasCommands());

        // execute with command from current floor
        repository.addInternal(1);
        testedObject.executeFirstCommand();
        verify(spyElevator).stop();
        verify(spyElevator).openDoors();
        verify(spyElevator).closeDoors();
        assertFalse(repository.hasCommands());

        // execute up to floor with internal call
        repository.addInternal(2);
        testedObject.executeFirstCommand();
        verify(spyElevator).up();
        assertTrue(repository.hasCommands());
    }

    @Test
    public void firstExternalCommand() {
        Elevator spyElevator = createSpyElevator(2);

        ElevatorCommandExecutor testedObject =
                new ElevatorCommandExecutor(repository, spyElevator, 1);

        repository.addExternal(3);
        repository.addInternal(1);

        testedObject.executeFirstCommand();
        verify(spyElevator).up();
        assertTrue(repository.hasCommands());
    }

    @Test
    public void firstInternalCommand() {
        Elevator spyElevator = createSpyElevator(2);

        ElevatorCommandExecutor testedObject =
                new ElevatorCommandExecutor(repository, spyElevator, 1);

        repository.addInternal(1);
        repository.addExternal(3);

        testedObject.executeFirstCommand();
        verify(spyElevator).down();
        assertTrue(repository.hasCommands());
    }

    private Elevator createSpyElevator(int withCurrentFloor) {
        return spy(new Elevator(withCurrentFloor, new BigDecimal("2.0"), new BigDecimal("1000")));
    }

}