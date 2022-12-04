package com.profitsoft.internship.task4;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class PersonsParserTest {

    private final PersonsParser parser = new PersonsParser();

    @BeforeClass
    public static void deleteChangedFile() throws IOException {

        URL actualOutputFile = PersonsParserTest.class.getClassLoader().getResource("task4TestFiles/output/persons.xml");
        if(actualOutputFile != null) {
            Files.deleteIfExists(Path.of(actualOutputFile.getPath()));
        }
    }

    @Test
    public void parseAndWriteChangedFile_WorksFine() throws IOException {
        File inputFile = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("task4TestFiles/input/persons.xml")).getFile());
        String outputDirectory = Objects.requireNonNull(getClass().getClassLoader().getResource("task4TestFiles/output/")).getFile();

        parser.parseAndWriteChangedFile(inputFile, outputDirectory);

        Path expectedOutputFile = Path.of(Objects.requireNonNull(getClass().getClassLoader().getResource("task4TestFiles/output/expectedOutput.xml")).getPath());
        Path actualOutputFile = Path.of(Objects.requireNonNull(getClass().getClassLoader().getResource("task4TestFiles/output/persons.xml")).getPath());

        assertEquals(-1, Files.mismatch(expectedOutputFile, actualOutputFile));
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