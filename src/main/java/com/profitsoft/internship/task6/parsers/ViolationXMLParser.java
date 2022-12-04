package com.profitsoft.internship.task6.parsers;

import com.profitsoft.internship.task5.model.ViolationType;
import com.profitsoft.internship.task6.xmlHandler.ViolationXmlHandler;
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
