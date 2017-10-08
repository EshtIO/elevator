package com.eshtio.chatfuel.elevator.config;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

/**
 * Validator for {@link ElevatorSimulatorConfig#floorHeight}
 */
public class FloorsCountValidator implements IParameterValidator {

    @Override
    public void validate(String name, String value) throws ParameterException {
        try {
            int floorsCount = Integer.parseInt(value);
            if (floorsCount < 5 || floorsCount > 20) {
                throw new ParameterException("Incorrect floors count (valid value: 5 <= count <= 20)");
            }
        } catch (NumberFormatException ex) {
            throw new ParameterException("Floors count is not number");
        }
    }
}
