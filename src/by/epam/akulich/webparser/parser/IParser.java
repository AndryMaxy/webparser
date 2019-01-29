package by.epam.akulich.webparser.parser;

import by.epam.akulich.webparser.bean.Drug;

import java.io.InputStream;
import java.util.List;

public interface IParser {

    List<Drug> parse(InputStream inputStream);
}
