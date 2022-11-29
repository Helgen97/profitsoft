package com.profitsoft.internship.task4;

import org.junit.Test;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

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

    @Test
    public void parseAndWriteChangedFileNullFileInput() {
        assertThrows(IllegalArgumentException.class, () -> parser.parseAndWriteChangedFile(null, "task4TestFiles/output/"));
    }

    @Test
    public void parseAndWriteChangedFileNullOutputPathInput() {
        assertThrows(IllegalArgumentException.class, () -> parser.parseAndWriteChangedFile(new File("task4TestFiles/input/persons.xml"), null));
    }

    @Test
    public void parseAndWriteChangedFileFileIsDirectoryInput() {
        assertThrows(IllegalArgumentException.class, () -> parser.parseAndWriteChangedFile(new File("task4TestFiles/input/"), "task4TestFiles/output/"));
    }
}