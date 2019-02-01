package by.epam.akulich.webparser.factory;

import by.epam.akulich.webparser.parser.DOMParser;
import by.epam.akulich.webparser.parser.IParser;
import by.epam.akulich.webparser.parser.MySAXParser;
import by.epam.akulich.webparser.parser.StAXParser;

public class ParserFactory {

    private static final String DOM = "DOM";
    private static final String SAX = "SAX";
    private static final String STAX = "StAX";
    private static ParserFactory instance;

    private ParserFactory(){}

    public static ParserFactory getInstance() {
        if (instance == null) {
            instance = new ParserFactory();
        }
        return instance;
    }

    public IParser getParser(String name) {
        switch (name) {
            case DOM :
                return new DOMParser();
            case SAX :
                return new MySAXParser();
            case STAX:
                return new StAXParser();
            default:
                throw new IllegalArgumentException();
        }
    }
}
