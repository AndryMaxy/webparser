package by.epam.akulich.webparser.parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public interface DateParser {

    default LocalDate parseDate(String text) {
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-M-d");
        return LocalDate.parse(text, inputFormat);
    }
}
