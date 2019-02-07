package by.epam.akulich.webparser.validator;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class XMLValidatorTest {

    @Test
    public void validate_InputStream_True() throws IOException {
        Path path = Paths.get("src/main/resource/medicine.xml");
        InputStream stream = Files.newInputStream(path);
        XMLValidator validator = new XMLValidator();
        boolean actual = validator.validate(stream);
        Assert.assertTrue(actual);
    }

    @Test
    public void validate_InputStream_False() throws IOException {
        Path path = Paths.get("src/main/resource/medicineNotValid.xml");
        InputStream stream = Files.newInputStream(path);
        XMLValidator validator = new XMLValidator();
        boolean actual = validator.validate(stream);
        Assert.assertFalse(actual);
    }
}