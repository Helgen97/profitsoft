
package com.profitsoft.internship.task2;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
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
        assertEquals("{#tag=3, #a=2, #top=1, #works=1, #best=1}", task2.topFiveUsedHashTags(Arrays.asList(
                "Test #tag", "Test #a #tag #best", "London is a capital of #a #tag", "Is it #works ?", "#top IT soft")).toString());
        assertEquals(Collections.EMPTY_MAP, task2.topFiveUsedHashTags(null));
        assertEquals(Collections.EMPTY_MAP, task2.topFiveUsedHashTags(Collections.EMPTY_LIST));
        
    }
    
}
