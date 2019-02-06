package by.epam.akulich.webparser.parser;

import by.epam.akulich.webparser.bean.Medicine;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.util.List;

public class StAXParser extends ParserHandler implements IParser {

    @Override
    public List<Medicine> parse(File file) throws SAXException, XMLStreamException {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        Source source = new StreamSource(file);
        XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(source);
        while (xmlEventReader.hasNext()) {
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
        return medicines;
    }

    @Override
    protected String getAttributeValue(String attributeName, Attributes attributes, StartElement element){
        return element.getAttributeByName(new QName(attributeName)).getValue();
    }
}
