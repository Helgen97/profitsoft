package com.profitsoft.internship.task4;

import org.junit.Test;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class PersonsParserTest {

    private final PersonsParser parser = new PersonsParser();

    @Test
    public void parseAndWriteChangedFileWorksFine() throws IOException {
        parser.parseAndWriteChangedFile(new File("task4TestFiles/input/persons.xml"), "task4TestFiles/output/");

        FileInputStream fileInputStream = new FileInputStream("task4TestFiles/output/persons.xml");
        DataInputStream actualOutput = new DataInputStream(fileInputStream);

        FileInputStream fileInputStream1 = new FileInputStream("task4TestFiles/output/expectedOutput.xml");
        DataInputStream expectedOutputData = new DataInputStream(fileInputStream1);

        byte actual = actualOutput.readByte();
        byte expected = expectedOutputData.readByte();

        assertEquals(expected, actual);
    }
}