package com.gyllecodes.adventofcode2024;

import com.gyllecodes.adventofcode2024.utils.FileHelper;

import java.io.IOException;
import java.util.List;

public abstract class DayTemplate {
    private final String fileName;

    protected DayTemplate(String fileName) {
        this.fileName = fileName;
    }

    public void solve() throws IOException {
        System.out.println("Attempting to solve current challenge: " + getClass().getSimpleName());
        final List<String> input = FileHelper.getStringList(fileName);

        long startTime = System.nanoTime();
        final String output1 = runPart1(input); // act
        long endTime = System.nanoTime();
        System.out.println("Answer for part 1: " + output1);
        System.out.println("Runtime: " + (endTime - startTime) / 1_000_000 + " ms.");

        startTime = System.nanoTime();
        final String output2 = runPart2(input); // act
        endTime = System.nanoTime();
        System.out.println("Answer for part 2: " + output2);
        System.out.println("Runtime: " + (endTime - startTime) / 1_000_000 + " ms.");
    }

    protected abstract String runPart1(List<String> input);

    protected abstract String runPart2(List<String> input);

}
