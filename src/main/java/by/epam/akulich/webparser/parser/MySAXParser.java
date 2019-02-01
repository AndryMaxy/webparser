package by.epam.akulich.webparser.parser;

import by.epam.akulich.webparser.bean.Certificate;
import by.epam.akulich.webparser.bean.Dosage;
import by.epam.akulich.webparser.bean.DosageType;
import by.epam.akulich.webparser.bean.MedicineGroup;
import by.epam.akulich.webparser.bean.MedicinePackage;
import by.epam.akulich.webparser.bean.MedicinePackageType;
import by.epam.akulich.webparser.bean.MedicineRegister;
import by.epam.akulich.webparser.bean.Medicine;
import by.epam.akulich.webparser.bean.Version;
import by.epam.akulich.webparser.bean.VersionType;
import by.epam.akulich.webparser.builder.CertificateBuilder;
import by.epam.akulich.webparser.builder.DosageBuilder;
import by.epam.akulich.webparser.builder.MedicineBuilder;
import by.epam.akulich.webparser.XMLData;
import by.epam.akulich.webparser.builder.MedicinePackageBuilder;
import by.epam.akulich.webparser.builder.VersionBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MySAXParser extends DefaultHandler implements IParser {

    private static final Logger LOGGER = LogManager.getLogger(MySAXParser.class.getSimpleName());
    private String currentElement;
    private MedicineBuilder medicineBuilder;
    private VersionBuilder versionBuilder = new VersionBuilder();
    private CertificateBuilder certificateBuilder = new CertificateBuilder();
    private MedicinePackageBuilder medicinePackageBuilder = new MedicinePackageBuilder();
    private DosageBuilder dosageBuilder = new DosageBuilder();
    private List<Medicine> medicines = new ArrayList<>();

    @Override
    public List<Medicine> parse(File file) {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser;
        XMLReader reader;
        try {
            saxParser = saxParserFactory.newSAXParser();
            reader = saxParser.getXMLReader();
            reader.setContentHandler(this);
            reader.parse(new InputSource(new FileInputStream(file)));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            LOGGER.info("SAX parser error", e);
        }
        return medicines;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentElement = qName;
        switch (currentElement) {
            case XMLData.Tag.MEDICINE:
                medicineBuilder = new MedicineBuilder();
                String code = attributes.getValue(XMLData.Attribute.CODE);
                medicineBuilder.buildCode(code);
                break;
            case XMLData.Tag.VERSION:
                String form = attributes.getValue(XMLData.Attribute.FORM);
                VersionType versionType = VersionType.valueByString(form);
                versionBuilder.buildType(versionType);
                break;
            case XMLData.Tag.CERTIFICATE:
                String register = attributes.getValue(XMLData.Attribute.REGISTER);
                MedicineRegister medicineRegister = MedicineRegister.valueByString(register);
                certificateBuilder.buildRegister(medicineRegister);
                break;
            case XMLData.Tag.PACKAGE:
                String medicinePackage = attributes.getValue(XMLData.Attribute.PACKAGE_TYPE);
                MedicinePackageType medicinePackageType = MedicinePackageType.valueByString(medicinePackage);
                medicinePackageBuilder.buildMedicinePackageType(medicinePackageType);
                break;
            case XMLData.Tag.DOSAGE:
                String dosageTypeString = attributes.getValue(XMLData.Attribute.DOSAGE_TYPE);
                DosageType dosageType = DosageType.valueByString(dosageTypeString);
                dosageBuilder.buildType(dosageType);
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String text = new String(ch, start, length);

        if (text.contains("<") | currentElement == null) {
            return;
        }

        LocalDate date;
        switch (currentElement) {
            case XMLData.Tag.NAME:
                medicineBuilder.buildName(text);
                break;
            case XMLData.Tag.PRODUCER:
                medicineBuilder.buildProducer(text);
                break;
            case XMLData.Tag.GROUP:
                MedicineGroup medicineGroup = MedicineGroup.valueByString(text);
                medicineBuilder.buildGroup(medicineGroup);
                break;
            case XMLData.Tag.ANALOG_NAME:
                medicineBuilder.buildAnalog(text);
                break;
            case XMLData.Tag.NUMBER:
                certificateBuilder.buildNumber(text);
                break;
            case XMLData.Tag.ISSUE_DATE:
                date = parseDate(text);
                certificateBuilder.buildIssueDate(date);
                break;
            case XMLData.Tag.EXPIRATION_DATE:
                date = parseDate(text);
                certificateBuilder.buildExpirationDate(date);
                break;
            case XMLData.Tag.COUNT:
                int count = Integer.parseInt(text);
                medicinePackageBuilder.buildCount(count);
                break;
            case XMLData.Tag.PRICE:
                double price = Double.parseDouble(text);
                medicinePackageBuilder.buildPrice(price);
                break;
            case XMLData.Tag.DOSAGE:
                int value = Integer.parseInt(text);
                dosageBuilder.buildValue(value);
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case XMLData.Tag.MEDICINE:
                Medicine medicine = medicineBuilder.build();
                medicines.add(medicine);
                break;
            case XMLData.Tag.VERSION:
                Version version = versionBuilder.build();
                medicineBuilder.buildVersion(version);
                break;
            case XMLData.Tag.CERTIFICATE:
                Certificate certificate = certificateBuilder.build();
                versionBuilder.buildCertificate(certificate);
                break;
            case XMLData.Tag.PACKAGE:
                MedicinePackage medicinePackage = medicinePackageBuilder.build();
                versionBuilder.buildMedicinePackage(medicinePackage);
                break;
            case XMLData.Tag.DOSAGE:
                Dosage dosage = dosageBuilder.build();
                versionBuilder.buildDosage(dosage);
                break;
        }
        currentElement = null;
    }
}
