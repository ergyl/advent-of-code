package com.gyllecodes.adventofcode2024.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public final class ListHelper {
    private ListHelper(){}

    public static List<Integer> convertToIntList(final List<String> input) {
        return input.stream().map(Integer::parseInt).toList();
    }
}
