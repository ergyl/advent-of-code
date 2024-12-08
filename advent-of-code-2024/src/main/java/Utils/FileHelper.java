package Utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public final class FileHelper {
    private static final Logger logger = LogManager.getLogger(FileHelper.class);

    private FileHelper() {
    }

    public static List<String> getStringList(final String fileName) throws IOException {
        try (InputStream inputStream = FileHelper.class.getClassLoader().getResourceAsStream("input/" + fileName)) {
            if (inputStream == null) {
                logger.error("File {} not found in resources.", fileName);
                return new ArrayList<>();
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                return reader.lines().toList();
            }
        } catch (IOException ex) {
            logger.error("Error reading input file {}", fileName, ex);
            throw new IOException(ex.getMessage());
        }
    }

    public static List<List<Integer>> getLists(String fileName) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Path.of("src/main/resources/input/" + fileName))) {
            var result = new ArrayList<List<Integer>>();
            String line;

            while ((line = reader.readLine()) != null) {
                var parts = line.split("\\s+");

                while (result.size() < parts.length) {
                    result.add(new ArrayList<>());
                }

                for (int i = 0; i < parts.length; i++) {
                    result.get(i).add(Integer.parseInt(parts[i]));
                }
            }

            return result;
        }
    }
}