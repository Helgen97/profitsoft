package com.profitsoft.internship.task5.parsers;

import com.profitsoft.internship.task5.model.Violation;
import com.profitsoft.internship.task5.model.ViolationType;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ViolationXMLParser extends DefaultHandler {

    private final SAXParserFactory parserFactory;
    private volatile String tagValue = null;

    private final Violation violation;
    private final Map<ViolationType, Double> statistic;

    public ViolationXMLParser() {
        this.parserFactory = SAXParserFactory.newInstance();
        this.violation = new Violation();
        this.statistic = new HashMap<>();
    }

    public void parseViolationXML(Path path) {
        try {
            SAXParser parser = parserFactory.newSAXParser();
            parser.parse(path.toFile(), this);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
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
