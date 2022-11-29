package com.profitsoft.internship.task5;

import org.junit.Test;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class ViolationStatisticGeneratorTest {

    private final ViolationStatisticGenerator generator = new ViolationStatisticGenerator();

    @Test
    public void collectAndGenerateStatistic_JsonInputFiles() throws IOException {

        String pathToFolder = Objects.requireNonNull(getClass().getClassLoader().getResource("task5TestFiles/input/json")).getFile();
        String outputPath = Objects.requireNonNull(getClass().getClassLoader().getResource("task5TestFiles/output/")).getFile();

        generator.collectAndGenerateStatistic(pathToFolder, outputPath);

        File expectedOutputFile = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("task5TestFiles/output/expectedXml.xml")).getFile());
        File actualOutputFile = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("task5TestFiles/output/statistic.xml")).getFile());

        FileInputStream fileInputStream = new FileInputStream(actualOutputFile);
        DataInputStream actualOutput = new DataInputStream(fileInputStream);

        FileInputStream fileInputStream1 = new FileInputStream(expectedOutputFile);
        DataInputStream expectedOutputData = new DataInputStream(fileInputStream1);

        byte actual = actualOutput.readByte();
        byte expected = expectedOutputData.readByte();

        assertEquals(expected, actual);
    }

    @Test
    public void collectAndGenerateStatistic_XmlInputFiles() throws IOException {
        String pathToFolder = Objects.requireNonNull(getClass().getClassLoader().getResource("task5TestFiles/input/xml")).getFile();
        String outputPath = Objects.requireNonNull(getClass().getClassLoader().getResource("task5TestFiles/output/")).getFile();

        generator.collectAndGenerateStatistic(pathToFolder, outputPath);

        File expectedOutputFile = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("task5TestFiles/output/statistic.json")).getFile());
        File actualOutputFile = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("task5TestFiles/output/expectedJson.json")).getFile());

        FileInputStream fileInputStream = new FileInputStream(actualOutputFile);
        DataInputStream actualOutput = new DataInputStream(fileInputStream);

        FileInputStream fileInputStream1 = new FileInputStream(expectedOutputFile);
        DataInputStream expectedOutputData = new DataInputStream(fileInputStream1);

        byte actual = actualOutput.readByte();
        byte expected = expectedOutputData.readByte();

        assertEquals(expected, actual);
    }

    @Test
    public void collectAndGenerateStatistic_XmlNullPathToFolder() {
        assertThrows(IllegalArgumentException.class, () -> generator.collectAndGenerateStatistic(null, "task5TestFiles/output/statistic.json"));
    }

    @Test
    public void collectAndGenerateStatistic_XmlEmptyPathToFolder() {
        assertThrows(IllegalArgumentException.class, () -> generator.collectAndGenerateStatistic("", "task5TestFiles/output/statistic.json"));
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