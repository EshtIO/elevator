package com.eshtio.chatfuel.elevator.core;

import org.junit.Test;

import static org.junit.Assert.*;

public class ElevatorCommandRepositoryTest {

    @Test
    public void remove() throws Exception {
        ElevatorCommandRepository repository = new ElevatorCommandRepository();
        assertFalse(repository.hasCommands());
        assertFalse(repository.remove(1));

        repository.addInternal(1);
        assertTrue(repository.hasCommands());
        assertTrue(repository.remove(1));
        assertFalse(repository.hasCommands());

        repository.addExternal(1);
        assertTrue(repository.hasCommands());
        assertTrue(repository.remove(1));
        assertFalse(repository.hasCommands());

        repository.addExternal(1);
        repository.addInternal(1);
        assertTrue(repository.hasCommands());
        assertTrue(repository.remove(1));
        assertFalse(repository.hasCommands());
    }

    @Test
    public void hasCommands() {
        ElevatorCommandRepository repository = new ElevatorCommandRepository();
        assertFalse(repository.hasCommands());

        repository.addExternal(1);
        assertTrue(repository.hasCommands());

        repository.remove(1);
        assertFalse(repository.hasCommands());

        repository.addInternal(1);
        assertTrue(repository.hasCommands());
    }

    @Test
    public void testInternalCommands() throws Exception {
        ElevatorCommandRepository repository = new ElevatorCommandRepository();
        // Add three commands
        repository.addInternal(3);
        repository.addInternal(2);
        repository.addInternal(1);

        // Check has commands
        assertTrue(repository.hasCommands());
        assertTrue(repository.hasInternalGreaterThan(1));
        assertTrue(repository.hasInternalGreaterThan(2));
        assertFalse(repository.hasInternalGreaterThan(3));

        // Remove non existent command
        assertFalse(repository.removeInternal(4));

        // Remove existent command
        assertTrue(repository.removeInternal(1));
        assertFalse(repository.removeInternal(1));
        assertTrue(repository.hasCommands());

        // Remove existent command with max floor
        assertTrue(repository.removeInternal(3));
        assertFalse(repository.hasInternalGreaterThan(2));
        assertTrue(repository.hasCommands());

        // Remove remaining command
        assertTrue(repository.removeInternal(2));
        assertFalse(repository.hasCommands());
    }

    @Test
    public void testExternalCommands() throws Exception {
        ElevatorCommandRepository repository = new ElevatorCommandRepository();
        // Add three commands
        repository.addExternal(3);
        repository.addExternal(2);
        repository.addExternal(1);

        // Check has commands
        assertTrue(repository.hasCommands());
        assertTrue(repository.hasExternalLessThan(3));
        assertTrue(repository.hasExternalLessThan(2));
        assertFalse(repository.hasExternalLessThan(1));

        // Remove non existent command
        assertFalse(repository.removeExternal(4));

        // Remove existent command
        assertTrue(repository.removeExternal(3));
        assertFalse(repository.removeExternal(3));
        assertTrue(repository.hasCommands());

        // Remove existent command with min floor
        assertTrue(repository.removeExternal(1));
        assertFalse(repository.removeExternal(1));
        assertFalse(repository.hasExternalLessThan(2));
        assertTrue(repository.hasCommands());

        // Remove remaining command
        assertTrue(repository.removeExternal(2));
        assertFalse(repository.hasCommands());
    }

    @Test
    public void peekFirst() {
        ElevatorCommandRepository repository = new ElevatorCommandRepository();
        assertNull(repository.peekFirst());

        repository.addInternal(2);
        repository.addInternal(1);

        ElevatorCommand first = repository.peekFirst();

        assertNotNull(first);
        assertEquals(2, first.getFloor());
        assertTrue(first.isInternal());
    }

}