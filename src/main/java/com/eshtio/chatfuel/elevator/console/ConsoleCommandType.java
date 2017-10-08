package com.eshtio.chatfuel.elevator.console;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Supported console command type
 */
public enum ConsoleCommandType {

    PRESS_BUTTON_INSIDE("Button press with floor inside elevator", true, "i", "inside"),
    PRESS_BUTTON_OUTSIDE("Elevator call from floor", true, "o", "outside"),
    CONTINUE("Continue program", false, "c", "continue", "next", "n"),
    QUIT("Exiting the application", false, "q", "quit", "exit", "close");

    /** Aliases for command */
    private final Set<String> keys;
    /** Flag means is there a floor in command */
    private final boolean hasFloorNumber;
    /** Command description */
    private final String description;

    ConsoleCommandType(String description, boolean hasFloorNumber, String... keys) {
        this.hasFloorNumber = hasFloorNumber;
        this.keys = new HashSet<>(Arrays.asList(keys));
        this.description = description;
    }

    public Set<String> getKeys() {
        return keys;
    }

    public String getDescription() {
        return description;
    }

    public boolean hasKey(String key) {
        return keys.contains(key);
    }

    public boolean hasFloorNumber() {
        return hasFloorNumber;
    }

    /**
     * Get {@link ConsoleCommandType} by key (alias)
     *
     * @param key command key
     * @return {@link ConsoleCommandType}
     */
    public static ConsoleCommandType byKey(String key) {
        return Arrays.stream(ConsoleCommandType.values())
                .filter(command -> command.hasKey(key))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Unknown key: " + key));
    }

    @Override
    public String toString() {
        return "ConsoleCommandType{" +
                "keys=" + keys +
                ", hasFloorNumber=" + hasFloorNumber +
                ", description='" + description + '\'' +
                '}';
    }
}
