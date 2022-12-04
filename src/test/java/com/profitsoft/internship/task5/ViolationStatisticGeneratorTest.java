package com.profitsoft.internship.task5;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class ViolationStatisticGeneratorTest {

    private final ViolationStatisticGenerator generator = new ViolationStatisticGenerator();

    @BeforeClass
    public static void deleteCreatedStatistic() throws IOException {
        URL xmlStatisticUrl = ViolationStatisticGeneratorTest.class.getClassLoader().getResource("task5TestFiles/output/statistic.xml");
        URL jsonStatisticUrl = ViolationStatisticGeneratorTest.class.getClassLoader().getResource("task5TestFiles/output/statistic.json");

        if (xmlStatisticUrl != null) {
            Files.deleteIfExists(Path.of(xmlStatisticUrl.getPath()));
        }

        if (jsonStatisticUrl != null) {
            Files.deleteIfExists(Path.of(jsonStatisticUrl.getPath()));
        }
    }

    @Test
    public void collectAndGenerateStatistic_JsonInputFiles() throws IOException {

        String pathToFolder = Objects.requireNonNull(getClass().getClassLoader().getResource("task5TestFiles/input/json")).getFile();
        String outputPath = Objects.requireNonNull(getClass().getClassLoader().getResource("task5TestFiles/output/")).getFile();

        generator.collectAndGenerateStatistic(pathToFolder, outputPath);

        Path expectedOutputFile = Path.of(Objects.requireNonNull(getClass().getClassLoader().getResource("task5TestFiles/output/expectedXml.xml")).getPath());
        Path actualOutputFile = Path.of(Objects.requireNonNull(getClass().getClassLoader().getResource("task5TestFiles/output/statistic.xml")).getPath());

        assertEquals(-1, Files.mismatch(expectedOutputFile, actualOutputFile));
    }

    @Test
    public void collectAndGenerateStatistic_XmlInputFiles() throws IOException {
        String pathToFolder = Objects.requireNonNull(getClass().getClassLoader().getResource("task5TestFiles/input/xml")).getFile();
        String outputPath = Objects.requireNonNull(getClass().getClassLoader().getResource("task5TestFiles/output/")).getFile();

        generator.collectAndGenerateStatistic(pathToFolder, outputPath);

        Path expectedOutputFile = Path.of(Objects.requireNonNull(getClass().getClassLoader().getResource("task5TestFiles/output/expectedJson.json")).getPath());
        Path actualOutputFile = Path.of(Objects.requireNonNull(getClass().getClassLoader().getResource("task5TestFiles/output/statistic.json")).getPath());

        assertEquals(-1, Files.mismatch(expectedOutputFile, actualOutputFile));
    }

    @Test
    public void collectAndGenerateStatistic_XmlNullPathToFolder() {
        assertThrows(IllegalArgumentException.class, () -> generator.collectAndGenerateStatistic(null, "task5TestFiles/output/expectedOutput.json"));
    }

    @Test
    public void collectAndGenerateStatistic_XmlEmptyPathToFolder() {
        assertThrows(IllegalArgumentException.class, () -> generator.collectAndGenerateStatistic("", "task5TestFiles/output/expectedOutput.json"));
    }

    @Test
    public void collectAndGenerateStatistic_XmlNullOutputPath() {
        assertThrows(IllegalArgumentException.class, () -> generator.collectAndGenerateStatistic("task5TestFiles/input/xml", null));
    }

    @Test
    public void collectAndGenerateStatistic_XmlEmptyOutputPath() {
        assertThrows(IllegalArgumentException.class, () -> generator.collectAndGenerateStatistic("task5TestFiles/input/xml", ""));
    }
}