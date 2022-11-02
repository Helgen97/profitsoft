package com.profitsoft.internship.task3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dmytro Donchenkp
 */
public class Task3Test {

    private final Task3 task3 = new Task3();

    /**
     * Test of sortShapesByVolumeInAscendingOrder method, of class Task3.
     */
    @Test
    public void testSortShapesByVolumeInAscendingOrder() {
        List<Shape> expResult = new ArrayList<>();
        expResult.add(new Ball(1));
        expResult.add(new Cylinder(2, 1));
        expResult.add(new Cube(3));
        expResult.add(new Cylinder(5, 2));
        expResult.add(new Ball(4));

        List<Shape> listToSort = new ArrayList<>();
        listToSort.add(new Cylinder(5, 2));
        listToSort.add(new Cylinder(2, 1));
        listToSort.add(new Cube(3));
        listToSort.add(new Ball(1));
        listToSort.add(new Ball(4));

        assertEquals(expResult.toString(), task3.sortShapesByVolumeInAscendingOrder(listToSort).toString());
    }

    @Test
    public void testSortShapesByVolumeInAscendingOrderPassingNullOrEmptyList() {
        assertEquals(Collections.EMPTY_LIST, task3.sortShapesByVolumeInAscendingOrder(null));
        assertEquals(Collections.EMPTY_LIST, task3.sortShapesByVolumeInAscendingOrder(Collections.EMPTY_LIST));
    }

}
