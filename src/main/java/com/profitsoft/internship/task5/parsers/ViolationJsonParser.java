package com.profitsoft.internship.task5.parsers;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.profitsoft.internship.task5.model.Violation;
import com.profitsoft.internship.task5.model.ViolationType;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ViolationJsonParser {

    private final JsonFactory jsonFactory;
    private final Violation violation;
    private final Map<ViolationType, Double> statistic;

    public ViolationJsonParser() {
        this.jsonFactory = new JsonFactory();
        this.violation = new Violation();
        this.statistic = new HashMap<>();
    }

    public void parseViolationJson(Path path) {
        try (var jsonParser = jsonFactory.createParser(path.toFile())) {
            parse(jsonParser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parse(JsonParser parser) throws IOException {
        while (parser.nextToken() != JsonToken.END_ARRAY) {
            parser.nextToken();
            while (parser.nextToken() != JsonToken.END_OBJECT) {
                String fieldName = parser.getCurrentName();
                switch (fieldName) {
                    case "type" -> {
                        parser.nextToken();
                        violation.setType(ViolationType.valueOf(parser.getText()));
                    }
                    case "fine_amount" -> {
                        parser.nextToken();
                        violation.setFineAmount(Double.parseDouble(parser.getText()));
                    }
                }
            }
            statistic.computeIfPresent(violation.getType(), (key, value) -> value += violation.getFineAmount());
            statistic.putIfAbsent(violation.getType(), violation.getFineAmount());
        }
    }

    public Map<ViolationType, Double> getStatistic() {
        return statistic;
    }
}

