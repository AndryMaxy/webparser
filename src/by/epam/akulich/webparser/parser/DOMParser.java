package by.epam.akulich.webparser.parser;

import by.epam.akulich.webparser.servlet.ParserServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

public class DOMParser implements IParser {

    private static final Logger LOGGER = LogManager.getLogger(ParserServlet.class.getSimpleName());

    @Override
    public String parse(InputStream inputStream) {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        Document document = null;
        try {
            documentBuilder = builderFactory.newDocumentBuilder();
            document = documentBuilder.parse(inputStream);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        if (document == null) {
            LOGGER.info("document is null");
            return null;
        }
        document.getDocumentElement().normalize();
        LOGGER.info("works");
        return document.getElementsByTagName("ms:count").item(0).getTextContent();
    }
}
