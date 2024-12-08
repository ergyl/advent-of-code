package com.gyllecodes.adventofcode2024;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.gyllecodes.adventofcode2024.day01.Day01;
import com.gyllecodes.adventofcode2024.day02.Day02;
import com.gyllecodes.adventofcode2024.day03.Day03;

public class DaySolverFactory {
    private static final Map<Integer, Function<String, DayTemplate>> SOLVER_MAP = new HashMap<>();

    static {
        SOLVER_MAP.put(1, Day01::new);
        SOLVER_MAP.put(2, Day02::new);
        SOLVER_MAP.put(3, Day03::new);
    }

    public static DayTemplate getSolver(int dayOfTheMonth, String inputFileName) {
        Function<String, DayTemplate> constructor = SOLVER_MAP.get(dayOfTheMonth);
        if (constructor == null) {
            throw new IllegalArgumentException("No solution added for day " + dayOfTheMonth);
        }
        return constructor.apply(inputFileName);
    }
}