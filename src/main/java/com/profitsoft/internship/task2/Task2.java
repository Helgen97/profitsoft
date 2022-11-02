package com.profitsoft.internship.task2;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Dmytro Donchenko
 */
public class Task2 {

    /**
     * Написати метод, який на вхід приймає список рядків-текстів, в яких можуть
     * бути хеш-теги (слова, що починаються на знак "#"). Як результат, метод
     * повинен повертати top-5 найчастіше згаданих хеш-тегів із зазначенням
     * кількості згадки кожного з них. Декілька однакових хеш-тегів в одному
     * рядку повинні вважатися однією згадкою. Написати unit-тести для цього
     * методу.
     */
    public Map<String, Integer> topFiveUsedHashTags(List<String> strings) {

        if (strings == null || strings.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<String, Integer> tags = new HashMap<>();

        strings.stream()
                .map(string -> string.replaceAll("\\b(?<!#)[a-zA-z,.:]+", ""))
                .forEach(string -> Arrays.stream(string.split(" ")).distinct()
                .forEach((String tag) -> {
                    if (tag.isBlank() || tag.isEmpty()) {
                        return;
                    }
                    tags.computeIfPresent(tag, (key, value) -> value += 1);
                    tags.putIfAbsent(tag, 1);
                }));

        return tags.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (i1, i2) -> i1, LinkedHashMap::new));
    }

}
