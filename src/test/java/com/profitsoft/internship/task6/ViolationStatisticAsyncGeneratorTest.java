package com.profitsoft.internship.task6;

import com.profitsoft.internship.task5.ViolationStatisticGenerator;
import com.profitsoft.internship.task5.ViolationStatisticGeneratorTest;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class ViolationStatisticAsyncGeneratorTest {

    private final ViolationStatisticAsyncGenerator generator = new ViolationStatisticAsyncGenerator(8);

    @BeforeClass
    public static void deleteCreatedStatistic() throws IOException {
        URL jsonStatisticUrl = ViolationStatisticGeneratorTest.class.getClassLoader().getResource("task6TestFiles/output/statistic.json");

        if (jsonStatisticUrl != null) {
            Files.deleteIfExists(Path.of(jsonStatisticUrl.getPath()));
        }
    }

    @Test
    public void collectAndGenerateStatistic_worksFine() throws IOException {
        String pathToFolder = Objects.requireNonNull(getClass().getClassLoader().getResource("task6TestFiles/input/")).getFile();
        String outputPath = Objects.requireNonNull(getClass().getClassLoader().getResource("task6TestFiles/output/")).getFile();

        generator.collectAndGenerateStatistic(pathToFolder, outputPath);

        Path expectedOutputFile = Path.of(Objects.requireNonNull(getClass().getClassLoader().getResource("task6TestFiles/output/expectedOutput.json")).getPath());
        Path actualOutputFile = Path.of(Objects.requireNonNull(getClass().getClassLoader().getResource("task6TestFiles/output/statistic.json")).getPath());

        assertEquals(-1, Files.mismatch(expectedOutputFile, actualOutputFile));
    }

    @Test
    public void collectAndGenerateStatistic_SpeedTest() {
        String pathToFolder = Objects.requireNonNull(getClass().getClassLoader().getResource("task6TestFiles/input/")).getFile();
        String outputPath = Objects.requireNonNull(getClass().getClassLoader().getResource("task6TestFiles/output/")).getFile();

        long oneThread = System.currentTimeMillis();
        new ViolationStatisticGenerator().collectAndGenerateStatistic(pathToFolder, outputPath);
        System.out.println("One thread: " + (System.currentTimeMillis() - oneThread));

        long twoThreads = System.currentTimeMillis();
        new ViolationStatisticAsyncGenerator(2).collectAndGenerateStatistic(pathToFolder, outputPath);
        System.out.println("Two threads: " + (System.currentTimeMillis() - twoThreads));

        long fourThreads = System.currentTimeMillis();
        new ViolationStatisticAsyncGenerator(4).collectAndGenerateStatistic(pathToFolder, outputPath);
        System.out.println("Four threads: " + (System.currentTimeMillis() - fourThreads));

        long eightThreads = System.currentTimeMillis();
        new ViolationStatisticAsyncGenerator(8).collectAndGenerateStatistic(pathToFolder, outputPath);
        System.out.println("Eight threads: " + (System.currentTimeMillis() - eightThreads));
    }

    @Test
    public void collectAndGenerateStatistic_XmlNullPathToFolder() {
        assertThrows(IllegalArgumentException.class, () -> generator.collectAndGenerateStatistic(null, "task6TestFiles/output/"));
    }

    @Test
    public void collectAndGenerateStatistic_XmlEmptyPathToFolder() {
        assertThrows(IllegalArgumentException.class, () -> generator.collectAndGenerateStatistic("", "task6TestFiles/output/"));
    }

    @Test
    public void collectAndGenerateStatistic_XmlNullOutputPath() {
        assertThrows(IllegalArgumentException.class, () -> generator.collectAndGenerateStatistic("task6TestFiles/input/", null));
    }

    @Test
    public void collectAndGenerateStatistic_XmlEmptyOutputPath() {
        assertThrows(IllegalArgumentException.class, () -> generator.collectAndGenerateStatistic("task6TestFiles/input/", ""));
    }
}