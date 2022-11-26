package com.profitsoft.internship.task5;

import com.profitsoft.internship.task5.model.ViolationType;
import com.profitsoft.internship.task5.parsers.ViolationJsonParser;
import com.profitsoft.internship.task5.parsers.ViolationXMLParser;
import com.profitsoft.internship.task5.writers.ViolationStatisticJsonWriter;
import com.profitsoft.internship.task5.writers.ViolationStatisticXMLWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * У теці є перелік текстових файлів, кожен із яких є "зліпок" БД порушень правил дорожнього руху протягом певного року.
 * Кожен файл містить список json (або xml - на вибір) об'єктів - порушень приблизно такого виду:
 * {
 * "date_time: "2020-05-05 15:39:03", // час порушення
 * "first_name": "Ivan",
 * "last_name": "Ivanov"
 * "type": "SPEEDING", // тип порушення
 * "fine_amount": 340.00 // сума штрафу
 * }
 * Прочитати дані з усіх файлів, розрахувати та вивести статистику порушень у файл.
 * У вихідному файлі повинні міститися загальні суми штрафів за кожним типом порушень за всі роки, відсортовані за сумою (спочатку за найбільшою сумою штрафу).
 * Якщо вхідний файл був json, то вихідний повинен бути xml. Якщо вхідний xml, то вихідний - json. Щоб ви мали досвід роботи з обома форматами.
 * * Опціонально (на макс. бал): зробити так, щоб вхідні файли не завантажувалися цілком в пам'ять.
 */
public class ViolationStatisticGenerator {

    private final ViolationXMLParser xmlParser;
    private final ViolationJsonParser jsonParser;
    private Map<ViolationType, Double> statistic;
    private String firstFileType = "";

    public ViolationStatisticGenerator() {
        this.statistic = new HashMap<>();
        this.xmlParser = new ViolationXMLParser(statistic);
        this.jsonParser = new ViolationJsonParser(statistic);
    }

    public void collectAndGenerateStatistic(String pathToFolder, String outputPath) {
        try (Stream<Path> paths = Files.walk(Paths.get(pathToFolder))) {
            paths
                    .filter(file -> !Files.isDirectory(file))
                    .forEach((file) -> {
                        if (file.toString().endsWith(".xml")) {
                            if (firstFileType.isEmpty()) firstFileType = "xml";
                            xmlParser.parseViolationXML(file);
                        }
                        if (file.toString().endsWith(".json")) {
                            if (firstFileType.isEmpty()) firstFileType = "json";
                            jsonParser.parseViolationJson(file);
                        }
                    });

            sortMapByFineAmount();
            generateStatistic(outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateStatistic(String outputPath) {
        switch (firstFileType) {
            case "xml" -> ViolationStatisticJsonWriter.createJson(statistic, outputPath);
            case "json" -> ViolationStatisticXMLWriter.createXml(statistic, outputPath);
        }
    }

    private void sortMapByFineAmount() {
        statistic = statistic.entrySet().stream()
                .sorted(Map.Entry.<ViolationType, Double>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (i1, i2) -> i1, LinkedHashMap::new));
    }

}
