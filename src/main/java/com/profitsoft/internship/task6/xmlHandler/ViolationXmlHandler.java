package com.profitsoft.internship.task6.xmlHandler;

import com.profitsoft.internship.task5.model.Violation;
import com.profitsoft.internship.task5.model.ViolationType;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashMap;
import java.util.Map;

public class ViolationXmlHandler extends DefaultHandler {

    private String tagValue = null;
    private final Violation violation;

    private final Map<ViolationType, Double> statistic;

    public ViolationXmlHandler() {
        this.violation = new Violation();
        this.statistic = new HashMap<>();
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        switch (qName) {
            case "type" -> violation.setType(ViolationType.valueOf(tagValue));
            case "fine_amount" -> violation.setFineAmount(Double.parseDouble(tagValue));
            case "violation" -> {
                statistic.computeIfPresent(violation.getType(), (key, value) -> value += violation.getFineAmount());
                statistic.putIfAbsent(violation.getType(), violation.getFineAmount());
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        tagValue = String.copyValueOf(ch, start, length).trim();
    }

    public Map<ViolationType, Double> getStatistic() {
        return statistic;
    }
}
