package com.profitsoft.internship.lesson3_4.task1;

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

        URL actualOutputFile = PersonsParserTest.class.getClassLoader().getResource("lesson3_4Task1TestFiles/output/persons.xml");
        if(actualOutputFile != null) {
            Files.deleteIfExists(Path.of(actualOutputFile.getPath()));
        }
    }

    @Test
    public void parseAndWriteChangedFile_WorksFine() throws IOException {
        File inputFile = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("lesson3_4Task1TestFiles/input/persons.xml")).getFile());
        String outputDirectory = Objects.requireNonNull(getClass().getClassLoader().getResource("lesson3_4Task1TestFiles/output/")).getFile();

        parser.parseAndWriteChangedFile(inputFile, outputDirectory);

        Path expectedOutputFile = Path.of(Objects.requireNonNull(getClass().getClassLoader().getResource("lesson3_4Task1TestFiles/output/expectedOutput.xml")).getPath());
        Path actualOutputFile = Path.of(Objects.requireNonNull(getClass().getClassLoader().getResource("lesson3_4Task1TestFiles/output/persons.xml")).getPath());

        assertEquals(-1, Files.mismatch(expectedOutputFile, actualOutputFile));
    }

    @Test
    public void parseAndWriteChangedFile_NullFileInput() {
        assertThrows(IllegalArgumentException.class, () -> parser.parseAndWriteChangedFile(null, "lesson3_4Task1TestFiles/output/"));
    }

    @Test
    public void parseAndWriteChangedFile_NullOutputPathInput() {
        assertThrows(IllegalArgumentException.class, () -> parser.parseAndWriteChangedFile(new File("lesson3_4Task1TestFiles/input/persons.xml"), null));
    }

    @Test
    public void parseAndWriteChangedFile_FileIsDirectoryInput() {
        File inputDirectory = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("lesson3_4Task1TestFiles/input/")).getFile());

        assertThrows(IllegalArgumentException.class, () -> parser.parseAndWriteChangedFile(inputDirectory, "lesson3_4Task1TestFiles/output/"));
    }
}