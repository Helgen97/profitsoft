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
        System.out.println("Testing sorting");

        assertArrayEquals(new int[]{5, 3, 2, 0}, task1.sortArrayOfIntegerInDescendingOrderAndBeingPositive(new int[]{-1, 2, 3, 0, -9, 5}));
        assertArrayEquals(new int[]{}, task1.sortArrayOfIntegerInDescendingOrderAndBeingPositive(new int[]{-1, -2, -4, -15, -2}));
        assertArrayEquals(new int[]{}, task1.sortArrayOfIntegerInDescendingOrderAndBeingPositive(null));
        assertArrayEquals(new int[]{}, task1.sortArrayOfIntegerInDescendingOrderAndBeingPositive(new int[]{}));
    }

}
