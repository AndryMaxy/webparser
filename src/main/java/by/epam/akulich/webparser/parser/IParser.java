package by.epam.akulich.webparser.parser;

import by.epam.akulich.webparser.bean.Medicine;
import by.epam.akulich.webparser.exception.ParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface IParser {

    List<Medicine> parse(InputStream inputStream) throws ParserException, IOException;
}
