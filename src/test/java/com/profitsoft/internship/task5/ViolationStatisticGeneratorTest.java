package com.profitsoft.internship.task5;

import org.junit.Test;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ViolationStatisticGeneratorTest {

    private final ViolationStatisticGenerator generator = new ViolationStatisticGenerator();

    @Test
    public void collectAndGenerateStatisticJsonInputFiles() throws IOException {
        generator.collectAndGenerateStatistic("task5TestFiles/input/json", "task5TestFiles/output/statistic.xml");
        FileInputStream fileInputStream = new FileInputStream("task5TestFiles/output/statistic.xml");
        DataInputStream actualOutput = new DataInputStream(fileInputStream);

        FileInputStream fileInputStream1 = new FileInputStream("task5TestFiles/output/expectedXml.xml");
        DataInputStream expectedOutputData = new DataInputStream(fileInputStream1);

        byte actual = actualOutput.readByte();
        byte expected = expectedOutputData.readByte();

        assertEquals(expected, actual);
    }

    @Test
    public void collectAndGenerateStatisticXmlInputFiles() throws IOException {
        generator.collectAndGenerateStatistic("task5TestFiles/input/xml", "task5TestFiles/output/statistic.json");
        FileInputStream fileInputStream = new FileInputStream("task5TestFiles/output/statistic.json");
        DataInputStream actualOutput = new DataInputStream(fileInputStream);

        FileInputStream fileInputStream1 = new FileInputStream("task5TestFiles/output/expectedJson.json");
        DataInputStream expectedOutputData = new DataInputStream(fileInputStream1);

        byte actual = actualOutput.readByte();
        byte expected = expectedOutputData.readByte();

        assertEquals(expected, actual);
    }
}