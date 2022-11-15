package com.profitsoft.internship.task1;

import com.profitsoft.internship.task1.Task1;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dmytro Donchenko
 */
public class Task1Test {

    private final Task1 task1 = new Task1();

    @Test
    public void testSortArrayOfIntegerInDescendingOrderAndBeingPositive() {
        int[] expected = new int[]{5, 3, 2, 0};
        int[] actual = task1.sortArrayOfIntegerInDescendingOrderAndBeingPositive(new int[]{-1, 2, 3, 0, -9, 5});

        assertArrayEquals(expected, actual);
    }
    
    @Test
    public void testSortArrayOfIntegerInDescendingOrderAndBeingPositiveTakesNegativeArray() {
        int[] expected = new int[]{};
        int[] actual = task1.sortArrayOfIntegerInDescendingOrderAndBeingPositive(new int[]{-1, -2, -3, -9, -5});

        assertArrayEquals(expected, actual);
    }
    
    @Test
    public void testSortArrayOfIntegerInDescendingOrderAndBeingPositiveTakesEmptyArray() {
        int[] expected = new int[]{};
        int[] actual = task1.sortArrayOfIntegerInDescendingOrderAndBeingPositive(new int[]{});

        assertArrayEquals(expected, actual);
    }
    
    @Test
    public void testSortArrayOfIntegerInDescendingOrderAndBeingPositiveTakesNull() {
        int[] expected = new int[]{};
        int[] actual = task1.sortArrayOfIntegerInDescendingOrderAndBeingPositive(null);

        assertArrayEquals(expected, actual);
    }
    
    

}