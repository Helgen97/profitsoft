package com.profitsoft.internship.task4;

import org.junit.Test;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class PersonsParserTest {

    private final PersonsParser parser = new PersonsParser();

    @Test
    public void parseAndWriteChangedFile_WorksFine() throws IOException {
        File inputFile = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("task4TestFiles/input/persons.xml")).getFile());
        String outputDirectory = Objects.requireNonNull(getClass().getClassLoader().getResource("task4TestFiles/output/")).getFile();

        parser.parseAndWriteChangedFile(inputFile, outputDirectory);

        File expectedOutputFile = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("task4TestFiles/output/expectedOutput.xml")).getFile());
        File actualOutputFile = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("task4TestFiles/output/persons.xml")).getFile());

        FileInputStream fileInputStream = new FileInputStream(actualOutputFile);
        DataInputStream actualOutput = new DataInputStream(fileInputStream);

        FileInputStream fileInputStream1 = new FileInputStream(expectedOutputFile);
        DataInputStream expectedOutputData = new DataInputStream(fileInputStream1);

        byte actual = actualOutput.readByte();
        byte expected = expectedOutputData.readByte();

        assertEquals(expected, actual);
    }

    @Test
    public void parseAndWriteChangedFile_NullFileInput() {
        assertThrows(IllegalArgumentException.class, () -> parser.parseAndWriteChangedFile(null, "task4TestFiles/output/"));
    }

    @Test
    public void parseAndWriteChangedFile_NullOutputPathInput() {
        assertThrows(IllegalArgumentException.class, () -> parser.parseAndWriteChangedFile(new File("task4TestFiles/input/persons.xml"), null));
    }

    @Test
    public void parseAndWriteChangedFile_FileIsDirectoryInput() {
        File inputDirectory = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("task4TestFiles/input/")).getFile());

        assertThrows(IllegalArgumentException.class, () -> parser.parseAndWriteChangedFile(inputDirectory, "task4TestFiles/output/"));
    }
}