package com.profitsoft.internship.task6;

import com.profitsoft.internship.task5.model.ViolationType;
import com.profitsoft.internship.task5.writers.ViolationStatisticJsonWriter;
import com.profitsoft.internship.task6.parsers.ViolationXMLParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Модифікувати завдання з попереднього блоку (у теці є перелік текстових файлів, кожен із яких є "зліпок" БД порушень правил дорожнього руху протягом певного року...)
 * таким чином, щоб різні файли з теки завантажувалися асинхронно за допомогою пулу потоків, але загальна статистика однаково формувалася.
 * Використовувати CompletableFuture та ExecutorService.
 * Порівняти швидкодію програми, коли не використовується паралелізація, коли використовується 2 потоки, 4 і 8.
 * Файлів в теці повинно бути 10+, їх розмір повинен бути достатнім, щоб заміри були цікавими.
 * Результати порівняння прикласти коментарем до виконаного завдання.
 */
public class ViolationStatisticAsyncGenerator {

    private final ViolationXMLParser xmlParser;
    private Map<ViolationType, Double> statistic;
    private final ExecutorService executorsService;

    public ViolationStatisticAsyncGenerator(int threadsAmount) {
        this.executorsService = Executors.newFixedThreadPool(threadsAmount);
        this.xmlParser = new ViolationXMLParser();
        this.statistic = new HashMap<>();
    }

    public void collectAndGenerateStatistic(String pathToFolder, String outputPath) {
        if (pathToFolder == null || outputPath == null || pathToFolder.isEmpty() || outputPath.isEmpty()) {
            throw new IllegalArgumentException("Bad arguments! Please verify paths arguments!");
        }
        try (Stream<Path> paths = Files.walk(Paths.get(pathToFolder))) {
            paths
                    .filter(file -> !Files.isDirectory(file))
                    .filter(file -> file.toString().endsWith(".xml"))
                    .parallel()
                    .forEach(file -> CompletableFuture.runAsync(() -> mergeMap(xmlParser.parseViolationXML(file)), executorsService));

            executorsService.close();

            sortMapByFineAmount();
            ViolationStatisticJsonWriter.createJson(statistic, outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mergeMap(Map<ViolationType, Double> newStatistic) {
        newStatistic.forEach(
                ((violationType, fineAmount) -> statistic.merge(
                        violationType, fineAmount, (oldValue, newValue) -> oldValue += newValue)));
    }

    private void sortMapByFineAmount() {
        statistic = statistic.entrySet().stream()
                .sorted(Map.Entry.<ViolationType, Double>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (i1, i2) -> i1, LinkedHashMap::new));
    }

}
