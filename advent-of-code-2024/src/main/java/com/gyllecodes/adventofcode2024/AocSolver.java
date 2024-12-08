package com.gyllecodes.adventofcode2024;

import Utils.FileHelper;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

public abstract class AocSolver {
    private final String fileName;

    protected AocSolver(String fileName) {
        this.fileName = fileName;
    }

    public void solve() throws IOException {
        final List<String> input = FileHelper.getStringList(fileName);

        Instant startTime = Instant.now();
        final String output1 = runPart1(input); // run part 1
        Instant endTime = Instant.now();
        System.out.println("Answer for part 1: " + output1);
        System.out.println("Runtime: " + Duration.between(startTime, endTime).toMillis() + " ms.");

        startTime = Instant.now();
        final String output2 = runPart2(input); // run part 2
        endTime = Instant.now();
        System.out.println("Answer for part 2: " + output2);
        System.out.println("Runtime: " + Duration.between(startTime, endTime).toMillis() + " ms.");
    }

    protected abstract String runPart1(List<String> input);

    protected abstract String runPart2(List<String> input);

}
