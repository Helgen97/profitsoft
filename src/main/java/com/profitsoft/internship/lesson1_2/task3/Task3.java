package com.profitsoft.internship.lesson1_2.task3;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dmytro Donchenko
 */
public class Task3 {

    /**
     * Реалізувати метод, який сортує геометричні 3d фігури за об'ємом. Метод
     * приймає параметром колекцію довільних геометричних фігур (куб, кулю,
     * циліндр). Написати unit-тести для методу.
     */
    public List<Shape> sortShapesByVolumeInAscendingOrder(List<Shape> shapes) {

        if (shapes == null || shapes.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        return shapes.stream()
                .sorted(Comparator.comparingDouble(shape -> shape.getVolume()))
                .collect(Collectors.toList());

    }
}
