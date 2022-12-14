package com.profitsoft.internship.lesson1_2.task3;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Dmytro Donchenkp
 */
public class Task3Test {

    private final Task3 task3 = new Task3();

    /**
     * Test of sortShapesByVolumeInAscendingOrder method, of class Task3.
     */
    @Test
    public void sortShapesByVolumeInAscendingOrder_worksFine() {
        List<Shape> expected = new ArrayList<>();
        expected.add(new Ball(1));
        expected.add(new Cylinder(2, 1));
        expected.add(new Cube(3));
        expected.add(new Cylinder(5, 2));
        expected.add(new Ball(4));

        List<Shape> listToSort = new ArrayList<>();
        listToSort.add(new Cylinder(5, 2));
        listToSort.add(new Cylinder(2, 1));
        listToSort.add(new Cube(3));
        listToSort.add(new Ball(1));
        listToSort.add(new Ball(4));

        List<Shape> actual = task3.sortShapesByVolumeInAscendingOrder(listToSort);

        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void sortShapesByVolumeInAscendingOrder_takesEmptyList() {
        List<Shape> expected = Collections.EMPTY_LIST;

        List<Shape> actual = task3.sortShapesByVolumeInAscendingOrder(Collections.EMPTY_LIST);

        assertEquals(expected, actual);
    }

    @Test
    public void sortShapesByVolumeInAscendingOrder_takesNull() {
        List<Shape> expected = Collections.EMPTY_LIST;

        List<Shape> actual = task3.sortShapesByVolumeInAscendingOrder(null);

        assertEquals(expected, actual);
    }
}
