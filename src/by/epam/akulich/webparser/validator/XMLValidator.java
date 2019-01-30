package by.epam.akulich.webparser.validator;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class XMLValidator {

    public boolean validate(File xml, File scheme) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(scheme);
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xml));
        } catch (IOException | SAXException e){
            //////
            return false;
        }
        return true;
    }
}
