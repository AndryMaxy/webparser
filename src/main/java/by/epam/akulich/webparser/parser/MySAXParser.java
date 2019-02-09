package by.epam.akulich.webparser.parser;

import by.epam.akulich.webparser.bean.Medicine;
import by.epam.akulich.webparser.exception.ParserException;
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
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Так как я наследуюсь от класса ParserHandler, то не могу унаследоваться ещё и
 * от класса DefaultHandler, чтобы переопределить его методы для SAX парсера,
 * поэтому реализую интерфейс ContentHandler.
 */

public class MySAXParser extends ParserHandler implements IParser, ContentHandler {

    @Override
    public List<Medicine> parse(InputStream inputStream) throws ParserException, IOException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser;
        XMLReader reader;
        try {
            saxParser = saxParserFactory.newSAXParser();
            reader = saxParser.getXMLReader();
            reader.setContentHandler(this);
            reader.parse(new InputSource(inputStream));
        } catch (ParserConfigurationException | SAXException e) {
            throw new ParserException(e);
        }
        return medicines;
    }

    @Override
    public void startDocument() {
        handleStartDocument();
    }

    @Override
    public void endDocument() {
        handleEndDocument();
    }

    @Override
    public void setDocumentLocator(Locator locator) {}

    @Override
    public void startPrefixMapping(String prefix, String uri) {}

    @Override
    public void endPrefixMapping(String prefix) {}

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) {
        handleStartElement(qName, atts, null);
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        handleEndElement(qName);
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String text = new String(ch, start, length);
        if (text.contains("<") | currentElement == null) {
            return;
        }
        handleCharacters(text);
    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) {}

    @Override
    public void processingInstruction(String target, String data) {}

    @Override
    public void skippedEntity(String name) {}

    @Override
    protected String getAttributeValue(String attributeName, Attributes attributes, StartElement element) {
        return attributes.getValue(attributeName);
    }
}
