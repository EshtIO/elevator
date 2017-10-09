package com.eshtio.chatfuel.elevator.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Elevator simulator command reader from console, need close after using
 */
public class ConsoleCommandReader implements AutoCloseable {

    /**
     * Buffered reader
     */
    private final BufferedReader reader;

    public ConsoleCommandReader() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Read command from console input stream
     *
     * @return {@link ConsoleCommand}
     * @throws IOException if an I/O error occurs
     */
    public ConsoleCommand readCommand() throws IOException {
        while (true) {
            System.out.println("Write your command...");
            String line = reader.readLine();
            if (line == null) {
                return null;
            }
            String[] args = line.split(" ");
            if (args.length < 1 || args.length > 2) {
                System.err.println("Illegal args length");
                System.out.println(getApiInfo());
                continue;
            }
            try {
                ConsoleCommandType type = ConsoleCommandType.byKey(args[0]);
                if (type.hasFloorNumber() && args.length < 2) {
                    System.err.println("Illegal args length");
                    System.out.println(getApiInfo());
                    continue;
                }
                return type.hasFloorNumber() ?
                        new ConsoleCommand(type, Integer.parseInt(args[1])) :
                        new ConsoleCommand(type);
            } catch (Exception ex) {
                System.err.println("Parse console command error " + ex.getMessage());
                System.out.println(getApiInfo());
            }
        }
    }

    /**
     * @return supported commands information with examples
     */
    public String getApiInfo() {
        return "Supported commands:" +
                Arrays.stream(ConsoleCommandType.values())
                        .map(command ->
                                "\n    Command: " + command.getKeys() +
                                        "\n    Description: " + command.getDescription() +
                                        "\n    Has floor numbers: " + command.hasFloorNumber())
                        .collect(Collectors.joining("\n"))+
                "\n\nExamples: " +
                "\n    i 8 - press button 8 inside elevator" +
                "\n    o 5 - press button outside elevator on 5 floor" +
                "\n    c - continue" +
                "\n    q - quit";
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
