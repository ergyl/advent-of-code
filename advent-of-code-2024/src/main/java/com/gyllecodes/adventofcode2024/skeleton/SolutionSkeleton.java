package com.gyllecodes.adventofcode2024.skeleton;

import com.gyllecodes.adventofcode2024.DayTemplate;
import com.gyllecodes.adventofcode2024.Main;

import java.util.List;

/**
 * All solutions are implemented from this.
 * The createSrc(int day) method copies this skeleton class file
 * to a new file (e.g., Day01.java) in a corresponding directory (e.g., day01).
 * It dynamically updates placeholders in the skeleton file to reflect the day's
 * number (e.g., replacing SolutionSkeleton with Day01).
 *
 * @see Main
 */
public class SolutionSkeleton extends DayTemplate {

    protected SolutionSkeleton(String filename) {
        super(filename);
    }

    @Override
    protected String runPart1(List<String> input) {
        return "solution to part 1";
    }

    @Override
    protected String runPart2(List<String> input) {
        return "solution to part 2";
    }
}
