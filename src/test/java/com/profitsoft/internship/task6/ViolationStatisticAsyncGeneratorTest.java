package com.profitsoft.internship.task6;

import com.profitsoft.internship.task5.ViolationStatisticGeneratorTest;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.*;

public class ViolationStatisticAsyncGeneratorTest {

    private final ViolationStatisticAsyncGenerator generator = new ViolationStatisticAsyncGenerator(4);

    @BeforeClass
    public static void deleteCreatedStatistic() throws IOException {
        URL jsonStatisticUrl = ViolationStatisticGeneratorTest.class.getClassLoader().getResource("task6TestFiles/output/statistic.json");

        if(jsonStatisticUrl != null) {
            Files.deleteIfExists(Path.of(jsonStatisticUrl.getPath()));
        }
    }

    @Test
    public void collectAndGenerateStatistic_worksFine() {

    }
}