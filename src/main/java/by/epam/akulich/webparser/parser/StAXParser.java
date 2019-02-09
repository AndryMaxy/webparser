package by.epam.akulich.webparser.parser;

import by.epam.akulich.webparser.bean.Medicine;
import by.epam.akulich.webparser.exception.ParserException;
import org.xml.sax.Attributes;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.util.List;

public class StAXParser extends ParserHandler implements IParser {

    @Override
    public List<Medicine> parse(InputStream inputStream) throws ParserException {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream);
            while (xmlEventReader.hasNext()) {
                readEvent(xmlEventReader);
            }
        } catch (XMLStreamException e) {
            throw new ParserException(e);
        }
        return medicines;
    }

    private void readEvent(XMLEventReader xmlEventReader) throws XMLStreamException {
        XMLEvent event = xmlEventReader.nextEvent();
        String name;
        switch (event.getEventType()) {
            case XMLEvent.START_DOCUMENT:
                handleStartDocument();
                break;
            case XMLEvent.START_ELEMENT:
                StartElement element = event.asStartElement();
                name = element.getName().getLocalPart();
                handleStartElement(name, null, element);
                String content = xmlEventReader.nextEvent().asCharacters().getData();
                handleCharacters(content);
                break;
            case XMLEvent.END_ELEMENT:
                EndElement endElement = event.asEndElement();
                name = endElement.getName().getLocalPart();
                handleEndElement(name);
                break;
            case XMLEvent.END_DOCUMENT:
                handleEndDocument();
                break;
        }
    }

    @Override
    protected String getAttributeValue(String attributeName, Attributes attributes, StartElement element){
        return element.getAttributeByName(new QName(attributeName)).getValue();
    }
}
