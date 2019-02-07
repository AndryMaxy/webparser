package by.epam.akulich.webparser.validator;

import by.epam.akulich.webparser.servlet.ParserServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class XMLValidator {

    private static final Logger LOGGER = LogManager.getLogger(XMLValidator.class.getSimpleName());

    public boolean validate(InputStream xml) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            String path = XMLValidator.class.getResource("/medicine-scheme.xsd").getPath();
            File scheme = new File(path);
            Schema schema = factory.newSchema(scheme);
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xml));
        } catch (IOException | SAXException e){
            LOGGER.error("xml error", e);
            return false;
        }
        return true;
    }
}
