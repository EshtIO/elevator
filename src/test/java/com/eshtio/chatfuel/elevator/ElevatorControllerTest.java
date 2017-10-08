package com.eshtio.chatfuel.elevator;

import com.eshtio.chatfuel.elevator.core.Elevator;
import com.eshtio.chatfuel.elevator.core.ElevatorCommandExecutor;
import com.eshtio.chatfuel.elevator.core.ElevatorCommandRepository;
import com.eshtio.chatfuel.elevator.core.ElevatorMovementState;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

public class ElevatorControllerTest {

    private Elevator spyElevator;
    private ElevatorCommandExecutor spyExecutor;
    private ElevatorCommandRepository spyRepository;

    private ElevatorController testedObject;

    @Before
    public void setUp() {
        spyElevator = spy(new Elevator(1, new BigDecimal("2.0"), new BigDecimal("1000")));
        spyRepository = spy(new ElevatorCommandRepository());
        spyExecutor = spy(new ElevatorCommandExecutor(spyRepository, spyElevator, 1));
        testedObject = new ElevatorController(spyElevator, spyExecutor, spyRepository, 10);
    }

    @Test
    public void nextElevatorStepStateStop() {
        when(spyElevator.getMovementState()).thenReturn(ElevatorMovementState.STOP);
        testedObject.nextElevatorStep();
        verify(spyExecutor).executeFirstCommand();
    }

    @Test
    public void nextElevatorStepStateUp() {
        when(spyElevator.getMovementState()).thenReturn(ElevatorMovementState.UP);
        testedObject.nextElevatorStep();
        verify(spyExecutor).executeWithUpInternalPriority();
    }

    @Test
    public void nextElevatorStepStateDown() {
        when(spyElevator.getMovementState()).thenReturn(ElevatorMovementState.DOWN);
        testedObject.nextElevatorStep();
        verify(spyExecutor).executeWithDownExternalPriority();
    }

    @Test
    public void executeAllCommands() {
        spyRepository.addInternal(5);
        spyRepository.addExternal(6);
        testedObject.executeAllCommands();

        assertFalse(spyRepository.hasCommands());
    }

    @Test(expected = IllegalArgumentException.class)
    public void pressButtonOutsideWithException() {
        testedObject.pressButtonOutside(11);
    }

    @Test(expected = IllegalArgumentException.class)
    public void pressButtonInsideWithException() {
        testedObject.pressButtonInside(0);
    }

    @Test
    public void pressButtonOutside() {
        testedObject.pressButtonOutside(10);
        verify(spyRepository).addExternal(10);
    }

    @Test
    public void pressButtonInside() {
        testedObject.pressButtonInside(6);
        verify(spyRepository).addInternal(6);
    }

}
