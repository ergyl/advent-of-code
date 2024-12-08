package com.gyllecodes.adventofcode2024.day04;

import com.gyllecodes.adventofcode2024.DayTemplate;

import java.util.List;

public class Day04 extends DayTemplate {

    public Day04(String filename) {
        super(filename);
    }

    /**
     * Setting up the grid with the appropriate
     * number of rows and columns based on the input.
     *
     * @param input list of strings
     * @return 2D array with positioned characters
     *
     * The code in this Day04 was written entirely by AI for learning purposes
     */
    private char[][] getColumnsAndRows(final List<String> input) {
        int rows = input.get(0).length();
        int cols = input.size();

        final char[][] grid = new char[rows][cols];

        // Loop through each row of the input
        for (int y = 0; y < rows; y++) {
            final String line = input.get(y);

            // Loop through each character in the current row
            for (int x = 0; x < cols; x++) {
                grid[y][x] = line.charAt(x); // Place the character into the grid
            }
        }
        return grid;
    }

    private int getCountXMAS(List<String> input) {
        int count = 0;
        char[][] grid = getColumnsAndRows(input);

        int rows = grid.length;
        int cols = grid[0].length;

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                count += checkPatternXMAS(grid, x, y, rows, cols);
            }
        }
        return count;
    }

    private int checkPatternXMAS(char[][] grid, int x, int y, int rows, int cols) {
        int count = 0;

        // Horizontal check (left to right)
        if (x <= cols - 4 && new String(grid[y], x, 4).equals("XMAS")) {
            count++;
        }

        // Horizontal check (right to left)
        if (x >= 3 && new StringBuilder(new String(grid[y], x - 3, 4)).reverse().toString().equals("XMAS")) {
            count++;
        }

        // Vertical check (top to bottom)
        if (y <= rows - 4 && grid[y][x] == 'X' && grid[y + 1][x] == 'M' && grid[y + 2][x] == 'A' && grid[y + 3][x] == 'S') {
            count++;
        }

        // Vertical check (bottom to top)
        if (y >= 3 && grid[y][x] == 'X' && grid[y - 1][x] == 'M' && grid[y - 2][x] == 'A' && grid[y - 3][x] == 'S') {
            count++;
        }

        // Diagonal check (top-left to bottom-right)
        if (x <= cols - 4 && y <= rows - 4 && grid[y][x] == 'X' && grid[y + 1][x + 1] == 'M' && grid[y + 2][x + 2] == 'A' && grid[y + 3][x + 3] == 'S') {
            count++;
        }

        // Diagonal check (bottom-left to top-right)
        if (x <= cols - 4 && y >= 3 && grid[y][x] == 'X' && grid[y - 1][x + 1] == 'M' && grid[y - 2][x + 2] == 'A' && grid[y - 3][x + 3] == 'S') {
            count++;
        }

        // Diagonal check (top-right to bottom-left)
        if (x >= 3 && y <= rows - 4 && grid[y][x] == 'X' && grid[y + 1][x - 1] == 'M' && grid[y + 2][x - 2] == 'A' && grid[y + 3][x - 3] == 'S') {
            count++;
        }

        // Diagonal check (bottom-right to top-left)
        if (x >= 3 && y >= 3 && grid[y][x] == 'X' && grid[y - 1][x - 1] == 'M' && grid[y - 2][x - 2] == 'A' && grid[y - 3][x - 3] == 'S') {
            count++;
        }

        return count;
    }

    @Override
    protected String runPart1(List<String> input) {
        return String.valueOf(getCountXMAS(input));
    }

    @Override
    protected String runPart2(List<String> input) {
        return "solution to part 2";
    }
}
