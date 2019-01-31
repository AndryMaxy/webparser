package by.epam.akulich.webparser.parser;

import by.epam.akulich.webparser.bean.Drug;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public interface IParser {

    List<Drug> parse(File file);

    default LocalDate parseDate(String text) {
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-d");
        return LocalDate.parse(text, inputFormat);
    }
}
