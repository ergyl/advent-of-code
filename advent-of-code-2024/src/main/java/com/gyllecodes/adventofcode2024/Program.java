package com.gyllecodes.adventofcode2024;

import com.gyllecodes.adventofcode2024.day01.Day01;

import com.gyllecodes.adventofcode2024.day02.Day02;
import com.gyllecodes.adventofcode2024.day03.Day03;
import com.gyllecodes.adventofcode2024.day04.Day04;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.List;

/**
 * Argument can be passed to solve a previous task
 * for example "java Program 1" runs the solution for Day01.
 */
public class Program {
    private static final String GITHUB_REPO_URL = "https://github.com/ergyl/advent-of-code";
    private static final String BASE_PATH = "src/main/java/com/gyllecodes/adventofcode2024";
    private static final String RESOURCES_INPUT_PATH = "src/main/resources/input";
    private static final String RESOURCES_TRACKING_PATH = "src/main/resources/tracking";
    private static final Logger logger = LogManager.getLogger(Program.class);

    /**
     * This method is responsible for setting up the necessary directories,
     * files, and downloading the input for the current day in December 2024.
     *
     * @throws IOException for failure
     */
    public static void setupDay() throws IOException {
        setupDay(getDay());
    }

    public static void setupDay(int dayOfTheMonth) throws IOException {
        String day = getDayAsString(dayOfTheMonth);
        logger.info("Setting up day {}: Creating source files and downloading input", day);
        createSrc(dayOfTheMonth);
        downloadInput(dayOfTheMonth);
    }

    /**
     * Invoke the solve method dynamically for the chosen day
     * Using reflection (newInstance()), create an instance of the class.
     * The class constructor expects a filename in the format "inputXX.txt",
     * where XX corresponds to the day number.
     *
     * @param dayOfTheMonth integer between 1-25
     */
    public static void solveForDay(int dayOfTheMonth) throws IOException {
        String inputFileName = "day" + getDayAsString(dayOfTheMonth) + ".txt";
        AocSolver solver = switch (dayOfTheMonth) {
            case 1 -> new Day01(inputFileName);
            case 2 -> new Day02(inputFileName);
            case 3 -> new Day03(inputFileName);
            case 4 -> new Day04(inputFileName);
            default -> throw new IllegalArgumentException("No solver for day " + dayOfTheMonth);
        };
        solver.solve();
    }


    private static int getDay() {
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        if (month != Calendar.DECEMBER) {
            throw new IllegalStateException("Today is not december.");
        }
        return c.get(Calendar.DAY_OF_MONTH);
    }

    private static void createSrc(int dayOfTheMonth) throws IOException {
        Path dirPath = Paths.get(BASE_PATH, getSrcDirectory(dayOfTheMonth));
        Path filePath = Paths.get(BASE_PATH, getSrcDirectory(dayOfTheMonth), getSrcFile(dayOfTheMonth) + ".java");
        Path skelPath = Paths.get(BASE_PATH, "skeleton", "SolutionSkeleton.java");

        if (!dirPath.toFile().exists()) {
            Files.createDirectory(dirPath);
        }
        if (!filePath.toFile().exists()) {
            Files.copy(skelPath, filePath);
        }
        String content = new String(Files.readAllBytes(filePath));
        content = content.replace("skeleton", "day" + getDayAsString(dayOfTheMonth));
        content = content.replace("Skeleton", "Day" + getDayAsString(dayOfTheMonth));
        content = content.replace("Solution", "");
        Files.write(filePath, content.getBytes());
    }

    private static void downloadInput(int dayOfTheMonth) throws IOException {
        List<String> configuration = getConfig();
        String emailAddress = configuration.get(0);
        String sessionId = configuration.get(1);
        if (!inputExists(dayOfTheMonth)) {
            long secondsSinceLastRequest = getSecsSinceLastRequest();
            if (secondsSinceLastRequest > 300L) { // 5 min (60 * 5 = 300)
                List<String> input = getInput(dayOfTheMonth, sessionId, emailAddress);
                writeInputToFile(dayOfTheMonth, input);
            } else {
                logger.info("Must wait {} seconds before making a request to the AOC servers",
                        (300 - secondsSinceLastRequest));
            }
        } else {
            String fileName = getInputFileName(dayOfTheMonth);
            Path filePath = Paths.get(RESOURCES_INPUT_PATH, fileName);
            logger.warn("The file {} already exists in {} so it will "
                            + "not be downloaded. Please remove it to download it again.",
                    fileName, filePath.toAbsolutePath());
        }
    }

    private static boolean inputExists(int dayOfTheMonth) {
        String fileName = getInputFileName(dayOfTheMonth);
        Path path = Paths.get(RESOURCES_INPUT_PATH, fileName);
        return path.toFile().exists() && path.toFile().isFile();
    }

    /**
     * Downloads the input for a given day using the Advent of Code API,
     * requiring the `sessionId` and `emailAddress` to authenticate.
     *
     * @param dayOfTheMonth an integer between 1-25
     * @param sessionId     the sessionID
     * @param emailAddress  the email address
     * @return task input as a list
     * @throws IOException for failure
     */
    private static List<String> getInput(int dayOfTheMonth, String sessionId, String emailAddress) throws IOException {
        URL url = new URL("https://adventofcode.com/2024/day/" + dayOfTheMonth + "/input");

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Cookie", "session=" + sessionId);
        con.setRequestProperty("User-Agent", GITHUB_REPO_URL + " by " + emailAddress);

        logger.info("Sending request to AoC API for day {}: {}", dayOfTheMonth, url);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            List<String> input = reader.lines().toList();
            logger.debug("Successfully retrieved input for day {}", dayOfTheMonth);
            return input;
        } catch (IOException ex) {
            logger.error("Failed to fetch input for day {}. URL: {}. Error: {}", dayOfTheMonth, url, ex.getMessage(), ex);
            throw new IOException("Failed to retrieve input from the AoC server.", ex);
        } finally {
            con.disconnect();
        }
    }


    private static String getDayAsString(int dayOfTheMonth) {
        return (dayOfTheMonth < 10 ? "0" : "") + dayOfTheMonth;
    }

    private static String getSrcDirectory(int dayOfTheMonth) {
        return "day" + getDayAsString(dayOfTheMonth);
    }

    private static String getSrcFile(int dayOfTheMonth) {
        return "Day" + getDayAsString(dayOfTheMonth);
    }

    private static String getInputFileName(int dayOfTheMonth) {
        return "day" + getDayAsString(dayOfTheMonth) + ".txt";
    }

    private static void verifyTrackingDirectory() {
        Path trackingDirPath = Paths.get(RESOURCES_TRACKING_PATH);
        if (Files.exists(trackingDirPath)) {
            logger.info("The tracking directory exists at: {}", trackingDirPath);
        } else {
            logger.error("The tracking directory does not exist at: {}", trackingDirPath);
        }
    }


    private static long getSecsSinceLastRequest() throws IOException {
        verifyTrackingDirectory();
        Path path = Paths.get(RESOURCES_TRACKING_PATH, "last_request_timestamp.txt");
        try {
            if (Files.exists(path)) {
                BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
                FileTime fileTime = attr.creationTime();
                Instant time = fileTime.toInstant();
                Instant now = Instant.now();
                long secondsSince = ChronoUnit.SECONDS.between(time, now);
                logger.debug("Timestamp read from file {}. Last request: {} seconds ago", path, secondsSince);
                return secondsSince;
            } else {
                Files.createFile(path);
                logger.info("Preparing to send initial request. Tracking file {} created", path);
                return Long.MAX_VALUE;
            }
        } catch (IOException ex) {
            logger.error("Error reading timestamp file {}. Error: {}", path, ex.getMessage(), ex);
            throw ex;
        }
    }

    private static void writeInputToFile(int dayOfTheMonth, List<String> input) throws IOException {
        String fileName = getInputFileName(dayOfTheMonth);
        Path path = Paths.get(RESOURCES_INPUT_PATH, fileName);
        Files.createFile(path);
        Files.write(path, input);
    }

    private static List<String> getConfig() throws IOException {
        try (InputStream inputStream = Program.class.getClassLoader().getResourceAsStream("config.txt")) {
            assert inputStream != null;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                List<String> content = reader.lines().toList();
                if (content.size() != 2) {
                    throw new IOException("The file config.txt is invalid. The expected format is " +
                            "email address on the first line and the session id on the second line.");
                }
                return content;
            }
        } catch (final IOException ex) {
            throw new IOException("Unable to read config file, please ensure you have a 'config.txt' file in " +
                    "'resources' directory.");
        }
    }

    public static void main(String... args) {
        if (args.length == 0) {
            try {
                setupDay();
            } catch (IOException ex) {
                logger.error("Error setting up day: ", ex);
            }
        } else {
            try {
                int dayOfTheMonth = Integer.parseInt(args[0]);
                solveForDay(dayOfTheMonth);
            } catch (NumberFormatException ex) {
                logger.error("Invalid day argument. Please enter an integer between 1 and 25.");
            } catch (IOException ex) {
                logger.error("IO exception: {}", ex.getMessage());
            } catch (Exception ex) {
                logger.error("Error: {}", ex.getMessage());
            }
        }
    }
}