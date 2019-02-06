package by.epam.akulich.webparser.parser;

import by.epam.akulich.webparser.bean.Medicine;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.util.List;

public interface IParser {

    List<Medicine> parse(File file) throws XMLStreamException, SAXException, ParserConfigurationException, IOException ;
}
