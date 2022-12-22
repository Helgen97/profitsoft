package com.profitsoft.internship.lesson5_6.task1.parsers;

import com.profitsoft.internship.lesson3_4.task2.model.ViolationType;
import com.profitsoft.internship.lesson5_6.task1.xmlHandler.ViolationXmlHandler;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;

public class ViolationXMLParser {

    private final SAXParserFactory parserFactory;

    public ViolationXMLParser() {
        this.parserFactory = SAXParserFactory.newInstance();
    }

    public Map<ViolationType, Double> parseViolationXML(Path path) {
        try {
            SAXParser parser = parserFactory.newSAXParser();
            ViolationXmlHandler xmlHandler = new ViolationXmlHandler();
            parser.parse(path.toFile(), xmlHandler);
            return xmlHandler.getStatistic();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyMap();
    }
}
