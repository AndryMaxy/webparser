package by.epam.akulich.webparser.parser;

import by.epam.akulich.webparser.bean.Medicine;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public interface IParser {

    List<Medicine> parse(File file);

    default LocalDate parseDate(String text) {
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-d");
        return LocalDate.parse(text, inputFormat);
    }
}
