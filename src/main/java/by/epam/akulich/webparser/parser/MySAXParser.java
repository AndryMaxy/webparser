package by.epam.akulich.webparser.parser;

import by.epam.akulich.webparser.bean.Medicine;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.events.StartElement;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class MySAXParser extends ParserHandler implements IParser, ContentHandler {

    @Override
    public List<Medicine> parse(File file) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        XMLReader reader = saxParser.getXMLReader();
        reader.setContentHandler(this);
        reader.parse(new InputSource(new FileInputStream(file)));
        return medicines;
    }

    @Override
    public void startDocument() throws SAXException {
        handleStartDocument();
    }

    @Override
    public void endDocument() throws SAXException {
        handleEndDocument();
    }

    @Override
    public void setDocumentLocator(Locator locator) {

    }

    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {

    }

    @Override
    public void endPrefixMapping(String prefix) throws SAXException {

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        handleStartElement(qName, atts, null);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        handleEndElement(qName);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String text = new String(ch, start, length);

        if (text.contains("<") | currentElement == null) {
            return;
        }
        handleCharacters(text);
    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {

    }

    @Override
    public void processingInstruction(String target, String data) throws SAXException {

    }

    @Override
    public void skippedEntity(String name) throws SAXException {

    }

    @Override
    protected String getAttributeValue(String attributeName, Attributes attributes, StartElement element) {
        return attributes.getValue(attributeName);
    }
}
