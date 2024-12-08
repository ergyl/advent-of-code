package com.gyllecodes.adventofcode2024.day01;

import com.gyllecodes.adventofcode2024.AocSolver;

import java.util.*;

public class Day01 extends AocSolver {
    private final Set<Integer> firstColumn1 = new TreeSet<>();
    private final List<Integer> secondColumn1 = new ArrayList<>();
    private final Set<Integer> firstColumn2 = new TreeSet<>();
    private final List<Integer> secondColumn2 = new ArrayList<>();

    public Day01(String filename) {
        super(filename);
    }

    @Override
    protected String runPart1(List<String> input) {
        return String.valueOf(getTotalDistance(input));
    }

    @Override
    protected String runPart2(List<String> input) {
        return String.valueOf(getSimilarityScore(input));
    }

    private int getTotalDistance(final List<String> input) {
        readInputForPart1(input);

        if (firstColumn1.size() != secondColumn1.size()) {
            throw new IllegalArgumentException("The two columns do not have the same number of elements.");
        }

        secondColumn1.sort(Integer::compareTo);
        Iterator<Integer> firstColumnIterator = firstColumn1.iterator();

        List<Integer> allDistances = new ArrayList<>();
        for (Integer secondColumnValue : secondColumn1) {
            if (!firstColumnIterator.hasNext()) {
                break;
            }

            Integer firstColumnValue = firstColumnIterator.next();
            int distance = Math.abs(firstColumnValue - secondColumnValue);
            allDistances.add(distance);
        }

        return allDistances.stream().mapToInt(Integer::intValue).sum();
    }

    private int getSimilarityScore(final List<String> input) {
        readInputForPart2(input);

        if (firstColumn2.size() != secondColumn2.size()) {
            throw new IllegalArgumentException("The two columns do not have the same number of elements.");
        }

        return calculateSimilarityScore();
    }

    private void readInputForPart1(List<String> input) {
        for (String line : input) {
            var parts = line.split("\\s+");

            if (parts.length == 2) {
                firstColumn1.add(Integer.parseInt(parts[0]));
                secondColumn1.add(Integer.parseInt(parts[1]));
            } else {
                throw new IllegalArgumentException("Malformed line found.");
            }
        }
    }

    private void readInputForPart2(List<String> input) {
        for (String line : input) {
            var parts = line.split("\\s+");

            if (parts.length == 2) {
                firstColumn2.add(Integer.parseInt(parts[0]));
                secondColumn2.add(Integer.parseInt(parts[1]));
            } else {
                throw new IllegalArgumentException("Malformed line found.");
            }
        }
    }

    private int calculateSimilarityScore() {
        Set<Integer> matches = new HashSet<>();
        Map<Integer, Integer> frequencyMap = new HashMap<>();

        for (Integer number : secondColumn2) {
            if (firstColumn2.contains(number)) {
                matches.add(number);
            }
            if (firstColumn2.contains(number)) {
                frequencyMap.put(number, frequencyMap.getOrDefault(number, 0) + 1);
            }
        }

        int similarityScore = 0;
        for (Integer number : firstColumn2) {
            int count = frequencyMap.getOrDefault(number, 0);
            similarityScore += number * count;
        }
        return similarityScore;
    }
}
