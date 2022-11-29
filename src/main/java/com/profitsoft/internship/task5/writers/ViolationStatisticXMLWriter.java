package com.profitsoft.internship.task5.writers;

import com.profitsoft.internship.task5.model.ViolationType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class ViolationStatisticXMLWriter {

    private static final DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
    private static final TransformerFactory transformerFactory = TransformerFactory.newInstance();
    private static Transformer transformer;
    private static DocumentBuilder documentBuilder;
    private static Document violationStatisticXml;

    public static void createXml(Map<ViolationType, Double> statistic, String outputPath) {
        String outputFile = outputPath + (outputPath.endsWith(File.separator) ? "statistic.xml" : File.separator + "statistic.xml");

        prepareWriter();
        generateViolationXml(statistic);

        try (var outputStream = new FileOutputStream(outputFile);
             var bufferedOutputStream = new BufferedOutputStream(outputStream)) {

            writeXml(bufferedOutputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void prepareWriter() {
        try {
            documentBuilder = documentFactory.newDocumentBuilder();
            transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        } catch (ParserConfigurationException | TransformerConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static void writeXml(BufferedOutputStream bufferedOutputStream) {
        DOMSource domSource = new DOMSource(violationStatisticXml);
        StreamResult result = new StreamResult(bufferedOutputStream);
        try {
            transformer.transform(domSource, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    private static void generateViolationXml(Map<ViolationType, Double> statistic) {
        violationStatisticXml = documentBuilder.newDocument();

        Element root = violationStatisticXml.createElement("statistic");
        violationStatisticXml.appendChild(root);

        statistic.forEach((violationType, fineAmount) -> root.appendChild(createViolationNode(violationType, fineAmount)));
    }

    private static Node createViolationNode(ViolationType violationType, Double fineAmount) {
        Element violationBlock = violationStatisticXml.createElement("violation");

        Element violationTypeNode = violationStatisticXml.createElement("violationType");
        violationTypeNode.setTextContent(violationType.toString());

        Element violationFineAmountNode = violationStatisticXml.createElement("fineAmountSum");
        violationFineAmountNode.setTextContent(fineAmount.toString());

        violationBlock.appendChild(violationTypeNode);
        violationBlock.appendChild(violationFineAmountNode);

        return violationBlock;
    }

}
