package com.gyllecodes.adventofcode2024.day02;

import com.gyllecodes.adventofcode2024.AocSolver;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class Day02 extends AocSolver {

    public Day02(String filename) {
        super(filename);
    }

    @Override
    protected String runPart1(List<String> input) {
        try {
            return String.valueOf(getSafeReportsCount(input));
        } catch (IOException ex) {
            ex.printStackTrace();
            return "Error processing input";
        }
    }

    @Override
    protected String runPart2(List<String> input) {
        try {
            return String.valueOf(getSafeReportsCountWithProblemDampener(input));
        } catch (IOException ex) {
            ex.printStackTrace();
            return "Error processing input";
        }
    }

    private int getSafeReportsCount(List<String> input) throws IOException {
        var count = 0;
        List<List<Integer>> items = extractRows(input);

        for (List<Integer> row : items) {
            if (isSafeReport(row)) {
                count++;
            }
        }

        return count;
    }

    private int getSafeReportsCountWithProblemDampener(List<String> input) throws IOException {
        var count = 0;
        List<List<Integer>> items = extractRows(input);

        for (List<Integer> row : items) {
            if (isSafeReportWithMaxOneBadLevel(row)) {
                count++;
            }
        }

        return count;
    }

    private boolean isSafeReport(List<Integer> row) {
        boolean isIncreasing = true;
        boolean isDecreasing = true;

        for (int i = 0; i < row.size() - 1; i++) {
            int diff = Math.abs(row.get(i) - row.get(i + 1));

            if (diff < 1 || diff > 3) {
                return false;
            }

            if (row.get(i) < row.get(i + 1)) {
                isDecreasing = false;
            } else if (row.get(i) > row.get(i + 1)) {
                isIncreasing = false;
            }
        }
        // Logical OR operator, evaluates to true if at least one of its operands is true
        return isIncreasing || isDecreasing;
    }

    private boolean isSafeReportWithMaxOneBadLevel(List<Integer> row) {
        if (isSafeReport(row)) {
            return true;
        }

        for (int i = 0; i < row.size(); i++) {
            var modifiedRow = new ArrayList<>(row);
            modifiedRow.remove(i);
            if (isSafeReport(modifiedRow)) {
                return true;
            }
        }
        return false;
    }

    private List<List<Integer>> extractRows(List<String> input) throws IOException {
        List<List<Integer>> result = new ArrayList<>();

        for (String line : input) {
            var parts = line.split("\\s+");

            List<Integer> row = new ArrayList<>();
            for (String part : parts) {
                try {
                    row.add(Integer.parseInt(part));
                } catch (NumberFormatException ex) {
                    throw new IllegalStateException("Invalid number for part: " + part);
                }
            }

            result.add(row);
        }
        return result;
    }
}
