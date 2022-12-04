package com.profitsoft.internship.task6.parsers;

import com.profitsoft.internship.task5.model.ViolationType;
import com.profitsoft.internship.task6.xmlHandler.ViolationXmlHandler;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ViolationXMLParser {

    private final SAXParserFactory parserFactory;
    private final Map<ViolationType, Double> statistic;

    public ViolationXMLParser() {
        this.parserFactory = SAXParserFactory.newInstance();
        this.statistic = new HashMap<>();
    }

    public void parseViolationXML(Path path) {
        try {
            SAXParser parser = parserFactory.newSAXParser();
            ViolationXmlHandler xmlHandler = new ViolationXmlHandler();
            parser.parse(path.toFile(), xmlHandler);
            mergeMap(xmlHandler.getStatistic());
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    private void mergeMap(Map<ViolationType, Double> newStatistic) {
        newStatistic.forEach(
                ((violationType, fineAmount) -> statistic.merge(
                        violationType, fineAmount, (oldValue, newValue) -> oldValue += newValue)));
    }

    public Map<ViolationType, Double> getStatistic() {
        return statistic;
    }
}
