package com.profitsoft.internship.task5.writers;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.profitsoft.internship.task5.model.ViolationType;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class ViolationStatisticJsonWriter {

    private static final JsonFactory jsonFactory = new JsonFactory();

    public static void createJson(Map<ViolationType, Double> statistic, String outputPath) {
        String outputFile = outputPath + (outputPath.endsWith(File.separator) ? "statistic.json" : File.separator + "statistic.json");

        try (var outputStream = new FileOutputStream(outputFile);
             var bufferedOutputStream = new BufferedOutputStream(outputStream);
             var jsonGenerator = jsonFactory.createGenerator(bufferedOutputStream, JsonEncoding.UTF8)) {
            jsonGenerator.useDefaultPrettyPrinter();
            generateViolationJson(jsonGenerator, statistic);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateViolationJson(JsonGenerator jsonGenerator, Map<ViolationType, Double> statistic) throws IOException {
        jsonGenerator.writeStartArray();
        statistic.forEach(((violationType, fineAmount) -> createViolationObject(jsonGenerator, violationType, fineAmount)));
        jsonGenerator.writeEndArray();
    }

    private static void createViolationObject(JsonGenerator jsonGenerator, ViolationType violationType, Double fineAmount) {
        try {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("violationType", violationType.toString());
            jsonGenerator.writeNumberField("fineAmountSum", fineAmount);
            jsonGenerator.writeEndObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
