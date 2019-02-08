package by.epam.akulich.webparser.parser;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DateParserTest {

    private static final LocalDate EXPECTED_1 = LocalDate.of(2019, 2, 7);
    private static final LocalDate EXPECTED_2 = LocalDate.of(2015, 5, 17);
    private static final LocalDate EXPECTED_3 = LocalDate.of(2017, 12, 31);
    private static final String INPUT_DATE_1 = "2019-2-7";
    private static final String INPUT_DATE_2 = "2015-5-17";
    private static final String INPUT_DATE_3 = "2017-12-31";

    @Test
    public void parseDate_String_LocalDate() {
        List<LocalDate> expected = new ArrayList<>();
        expected.add(EXPECTED_1);
        expected.add(EXPECTED_2);
        expected.add(EXPECTED_3);
        DateParser parser = new DateParserImpl();

        LocalDate actual1 = parser.parseDate(INPUT_DATE_1);
        LocalDate actual2 = parser.parseDate(INPUT_DATE_2);
        LocalDate actual3 = parser.parseDate(INPUT_DATE_3);
        List<LocalDate> actual = new ArrayList<>();
        actual.add(actual1);
        actual.add(actual2);
        actual.add(actual3);

        Assert.assertEquals(actual, expected);
    }

    /**
     * Для теститрования default метода.
     */
    private class DateParserImpl implements DateParser {

    }
}