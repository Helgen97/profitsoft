package com.profitsoft.internship.lesson1_2.task1;

import java.util.Arrays;
import java.util.Comparator;

/**
 *
 * @author Dmytro Docnhenko
 */
public class Task1 {
    
    /**
     * Зробити метод, який приймає на вхід масив цілих чисел, і повертає тільки ті з них, які позитивні (>=0),
     * відсортувавши їх за спаданням.
     * Зробити unit-тести для цього методу.
     */
    public int[] sortArrayOfIntegerInDescendingOrderAndBeingPositive(int[] integers) {
        if (integers == null || integers.length == 0 ) return new int[]{};
        return Arrays.stream(integers).boxed().filter(i -> i >= 0).sorted(Comparator.reverseOrder()).mapToInt(i -> i).toArray();
    }
    
}
