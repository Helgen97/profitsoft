package com.profitsoft.internship.task2;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dmytro Donchenko
 */
public class Task2Test {

    Task2 task2 = new Task2();

    @Test
    public void testTopFiveUsedHashTags() {
        String expected = "{#tag=3, #a=2, #top=1, #works=1, #best=1}";

        String actual = task2.topFiveUsedHashTags(Arrays.asList(
                "Test #tag", "Test #a #tag #best", "London is a capital of #a #tag", "Is it #works ?", "#top IT soft")
        ).toString();

        assertEquals(expected, actual);
    }

    @Test
    public void testTopFiveUsedHashTagsTakesEmptyList() {
        Map<String, Integer> expected = Collections.EMPTY_MAP;

        Map<String, Integer> actual = task2.topFiveUsedHashTags(Collections.EMPTY_LIST);

        assertEquals(expected, actual);
    }

    @Test
    public void testTopFiveUsedHashTagsTakesNull() {
        Map<String, Integer> expected = Collections.EMPTY_MAP;

        Map<String, Integer> actual = task2.topFiveUsedHashTags(null);

        assertEquals(expected, actual);
    }

}
