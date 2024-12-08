package com.gyllecodes.adventofcode2024.day03;

import com.gyllecodes.adventofcode2024.DayTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 extends DayTemplate {

    public Day03(String filename) {
        super(filename);
    }

    @Override
    protected String runPart1(List<String> input) {
        try {
            return String.valueOf(getSumOfMulInstructions(input));
        } catch (IOException ex) {
            ex.printStackTrace();
            return "Error processing input";
        }
    }

    @Override
    protected String runPart2(List<String> input) {
        try {
            return String.valueOf(getSumOfMulInstructionsEnableDoAndDont(input));
        } catch (IOException ex) {
            ex.printStackTrace();
            return "Error processing input";
        }
    }

    // Method for Part 1: Sum of multiplication instructions
    private int getSumOfMulInstructions(List<String> input) throws IOException {
        List<Integer> results = new ArrayList<>();
        var regex = "mul\\((\\d{1,3}),(\\d{1,3})\\)";
        Pattern pattern = Pattern.compile(regex);

        for (String line : input) {
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                int x = Integer.parseInt(matcher.group(1));
                int y = Integer.parseInt(matcher.group(2));
                int result = x * y;
                results.add(result);
            }
        }

        return results.stream().mapToInt(Integer::intValue).sum();
    }

    private int getSumOfMulInstructionsEnableDoAndDont(List<String> input) throws IOException {
        int result = 0, filtered = 0;
        boolean enabled = true;
        var regex = "mul\\((\\d+),(\\d+)\\)|(do|don't)\\(\\)";
        Pattern pattern = Pattern.compile(regex);

        for (String s : input) {
            Matcher matcher = pattern.matcher(s);

            while (matcher.find()) {
                if (matcher.group(0).startsWith("mul")) {
                    int x = Integer.parseInt(matcher.group(1));
                    int y = Integer.parseInt(matcher.group(2));
                    int product = x * y;
                    result += product;

                    if (enabled) {
                        filtered += product;
                    }
                } else {
                    enabled = !matcher.group(0).startsWith("don't");
                }
            }
        }

        return filtered;
    }
}
