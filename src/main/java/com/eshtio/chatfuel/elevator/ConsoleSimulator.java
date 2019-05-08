package com.eshtio.chatfuel.elevator;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.eshtio.chatfuel.elevator.config.ElevatorSimulatorConfig;
import com.eshtio.chatfuel.elevator.console.ConsoleCommand;
import com.eshtio.chatfuel.elevator.console.ConsoleCommandReader;
import com.eshtio.chatfuel.elevator.console.ConsoleCommandType;
import com.eshtio.chatfuel.elevator.core.Elevator;
import com.eshtio.chatfuel.elevator.core.ElevatorCommandExecutor;
import com.eshtio.chatfuel.elevator.core.ElevatorCommandRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Main class for using elevator simulator
 */
public class ConsoleSimulator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleSimulator.class);

    /** Elevator start floor */
    private static final int START_FLOOR = 1;

    /** Elevator controller */
    private final ElevatorController controller;

    public ConsoleSimulator(ElevatorController controller) {
        this.controller = controller;
    }

    /**
     * Redirect console command to elevator controller
     *
     * @param command console command
     */
    public void executeConsoleCommand(ConsoleCommand command) {
        switch (command.getType()) {
            case PRESS_BUTTON_INSIDE:
                controller.pressButtonInside(command.getFloor());
                controller.nextElevatorStep();
                break;
            case PRESS_BUTTON_OUTSIDE:
                controller.pressButtonOutside(command.getFloor());
                controller.nextElevatorStep();
                break;
            case CONTINUE:
                controller.nextElevatorStep();
                break;
            default:
                throw new IllegalArgumentException("Illegal console command type: " + command.getType());
        }
        LOGGER.info("Console command was executed");
    }

    /**
     * Stop simulator. Execute all remaining commands
     */
    public void stopSimulator() {
        LOGGER.info("Stop simulator...");
        controller.executeAllCommands();
        LOGGER.info("Simulator was stopped");
    }

    /**
     * Create simulator object from configuration
     *
     * @param config application configuration
     * @return {@link ConsoleSimulator}
     */
    public static ConsoleSimulator createFromConfig(ElevatorSimulatorConfig config) {
        LOGGER.info("Configuration: {}", config);

        // Create simulator context
        Elevator elevator = new Elevator(START_FLOOR, config.getFloorHeight(), config.getSpeed());
        LOGGER.info("Elevator current floor {}", elevator.getCurrentFloor());
        ElevatorCommandRepository repository = new ElevatorCommandRepository();
        ElevatorCommandExecutor executor =
                new ElevatorCommandExecutor(repository, elevator, config.getOpenDoorTime());
        ElevatorController controller =
                new ElevatorController(elevator, executor, repository, config.getFloorsCount());

        return new ConsoleSimulator(controller);
    }

    public static void main(String[] args) {
        // Parse config
        ElevatorSimulatorConfig config = new ElevatorSimulatorConfig();
        LOGGER.info("Welcome to elevator simulator");
        try {
            JCommander.newBuilder()
                    .addObject(config)
                    .build()
                    .parse(args);
        } catch (ParameterException ex) {
            LOGGER.error("Parse parameters error: {}", ex.getMessage());
            ex.getJCommander().usage();
            return;
        }

        // Create simulator
        ConsoleSimulator simulator = ConsoleSimulator.createFromConfig(config);
        Runtime.getRuntime().addShutdownHook(new Thread(simulator::stopSimulator));

        // Read commands from console
        try (ConsoleCommandReader reader = new ConsoleCommandReader()) {
            LOGGER.info(reader.getApiInfo());

            ConsoleCommand command;
            while ((command = reader.readCommand()) != null &&
                    command.getType() != ConsoleCommandType.QUIT) {
                try {
                    LOGGER.info("Execute console command...Wait...");
                    simulator.executeConsoleCommand(command);
                    LOGGER.debug("Console command executing was done");
                } catch (Exception ex) {
                    LOGGER.error("Execute console command error", ex);
                }
            }
        } catch (IOException ex) {
            LOGGER.error("Read command error", ex);
        }
    }

}
