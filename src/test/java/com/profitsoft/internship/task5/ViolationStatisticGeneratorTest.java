package com.profitsoft.internship.task5;

import com.profitsoft.internship.task4.PersonsParserTest;
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
    public void collectAndGenerateStatisticJsonInputFiles() throws IOException {

        String pathToFolder = Objects.requireNonNull(PersonsParserTest.class.getClassLoader().getResource("task5TestFiles/input/json")).getFile();
        String outputPath = Objects.requireNonNull(PersonsParserTest.class.getClassLoader().getResource("task5TestFiles/output/statistic.xml")).getFile();

        generator.collectAndGenerateStatistic(pathToFolder, outputPath);

        File expectedOutputFile = new File(Objects.requireNonNull(PersonsParserTest.class.getClassLoader().getResource("task5TestFiles/output/expectedXml.xml")).getFile());
        File actualOutputFile = new File(Objects.requireNonNull(PersonsParserTest.class.getClassLoader().getResource("task5TestFiles/output/statistic.xml")).getFile());

        FileInputStream fileInputStream = new FileInputStream(actualOutputFile);
        DataInputStream actualOutput = new DataInputStream(fileInputStream);

        FileInputStream fileInputStream1 = new FileInputStream(expectedOutputFile);
        DataInputStream expectedOutputData = new DataInputStream(fileInputStream1);

        byte actual = actualOutput.readByte();
        byte expected = expectedOutputData.readByte();

        assertEquals(expected, actual);
    }

    @Test
    public void collectAndGenerateStatisticXmlInputFiles() throws IOException {
        String pathToFolder = Objects.requireNonNull(PersonsParserTest.class.getClassLoader().getResource("task5TestFiles/input/xml")).getFile();
        String outputPath = Objects.requireNonNull(PersonsParserTest.class.getClassLoader().getResource("task5TestFiles/output/statistic.json")).getFile();

        generator.collectAndGenerateStatistic(pathToFolder, outputPath);

        File expectedOutputFile = new File(Objects.requireNonNull(PersonsParserTest.class.getClassLoader().getResource("task5TestFiles/output/statistic.json")).getFile());
        File actualOutputFile = new File(Objects.requireNonNull(PersonsParserTest.class.getClassLoader().getResource("task5TestFiles/output/expectedJson.json")).getFile());

        FileInputStream fileInputStream = new FileInputStream(actualOutputFile);
        DataInputStream actualOutput = new DataInputStream(fileInputStream);

        FileInputStream fileInputStream1 = new FileInputStream(expectedOutputFile);
        DataInputStream expectedOutputData = new DataInputStream(fileInputStream1);

        byte actual = actualOutput.readByte();
        byte expected = expectedOutputData.readByte();

        assertEquals(expected, actual);
    }

    @Test
    public void collectAndGenerateStatisticXmlNullPathToFolder() {
        assertThrows(IllegalArgumentException.class, () -> generator.collectAndGenerateStatistic(null, "task5TestFiles/output/statistic.json"));
    }

    @Test
    public void collectAndGenerateStatisticXmlEmptyPathToFolder() {
        assertThrows(IllegalArgumentException.class, () -> generator.collectAndGenerateStatistic("", "task5TestFiles/output/statistic.json"));
    }

    @Test
    public void collectAndGenerateStatisticXmlNullOutputPath() {
        assertThrows(IllegalArgumentException.class, () -> generator.collectAndGenerateStatistic("task5TestFiles/input/xml", null));
    }

    @Test
    public void collectAndGenerateStatisticXmlEmptyOutputPath() {
        assertThrows(IllegalArgumentException.class, () -> generator.collectAndGenerateStatistic("task5TestFiles/input/xml", ""));
    }
}