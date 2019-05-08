package com.eshtio.chatfuel.elevator;

import com.eshtio.chatfuel.elevator.console.ConsoleCommandType;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class ConsoleCommandTypeTest {

    @Test
    public void enumsHasUniqueKeys() {
        List<String> allKeys = Arrays.stream(ConsoleCommandType.values())
                .flatMap(command -> command.getKeys().stream())
                .collect(Collectors.toList());
        Assert.assertEquals(
                "All enum keys must be unique",
                allKeys.size(),
                new HashSet<>(allKeys).size()
        );
    }

}
