package com.profitsoft.internship.task4;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Розробити програму, яка на вхід отримує xml-файл з тегами <person>, в яких є атрибути name і surname.
 * Програма повинна створювати копію цього файлу, в якій значення атрибута surname об'єднане з name.
 * Наприклад name="Тарас" surname="Шевченко" у вхідному файлі повинно бути замінене на name="Тарас Шевченко" (атрибут surname має бути видалений).
 * Вхідний файл може бути великий, тому завантажувати його цілком в оперативну пам'ять буде поганою ідеєю.
 * Опціонально (на макс. бал): зробити так, щоб форматування вихідного файлу повторювало форматування вхідного файлу (мабуть, xml-парсер в такому разі тут не підійде).
 */
public class PersonsParser {

    private final Pattern namePattern = Pattern.compile("\\s?(?<!sur)name\\s?=\\s?\"(?<name>\\S+)\"", Pattern.MULTILINE | Pattern.COMMENTS);
    private final Pattern surnamePattern = Pattern.compile("\\s?surname\\s?=\\s?\"(?<surname>\\S+)\"", Pattern.MULTILINE | Pattern.COMMENTS);

    public void parseAndWriteChangedFile(File inputFile, String outputPath) {
        if (inputFile == null || outputPath == null || inputFile.isDirectory()) {
            throw new IllegalArgumentException("Wrong arguments! Verify arguments!");
        }

        try (var fileInputStream = new FileInputStream(inputFile);
             var scanner = new Scanner(fileInputStream, StandardCharsets.UTF_8).useDelimiter("(?<=<)");
             var fileWriter = new FileWriter(outputPath + inputFile.getName(), true);
             var bufferedWriter = new BufferedWriter(fileWriter)
        ) {

            while (scanner.hasNext()) {
                String line = replaceNameTagsToFullName(scanner.next());

                bufferedWriter.write(line);
            }

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private String replaceNameTagsToFullName(String line) {
        Matcher nameMatcher = namePattern.matcher(line);
        Matcher surnameMatcher = surnamePattern.matcher(line);

        String name;
        String surname = "";
        String newline = line;

        if (surnameMatcher.find()) {
            surname = surnameMatcher.group("surname");
            newline = newline.replaceAll(surnamePattern.toString(), "");
        }

        if (nameMatcher.find()) {
            name = nameMatcher.group("name");
            newline = newline.replaceAll(namePattern.toString(), String.format(" name=\"%s %s\"", name, surname).stripTrailing());
        }
        return newline;
    }
}
