package by.epam.akulich.webparser.parser;

import by.epam.akulich.webparser.bean.Certificate;
import by.epam.akulich.webparser.bean.Dosage;
import by.epam.akulich.webparser.bean.DosageType;
import by.epam.akulich.webparser.bean.DrugGroup;
import by.epam.akulich.webparser.bean.DrugPackage;
import by.epam.akulich.webparser.bean.DrugPackageType;
import by.epam.akulich.webparser.bean.DrugRegister;
import by.epam.akulich.webparser.bean.Version;
import by.epam.akulich.webparser.bean.VersionType;
import by.epam.akulich.webparser.builder.CertificateBuilder;
import by.epam.akulich.webparser.builder.DosageBuilder;
import by.epam.akulich.webparser.builder.DrugBuilder;
import by.epam.akulich.webparser.XMLData;
import by.epam.akulich.webparser.bean.Drug;
import by.epam.akulich.webparser.builder.DrugPackageBuilder;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MySAXParser extends DefaultHandler implements IParser {

    private static final Logger LOGGER = LogManager.getLogger(MySAXParser.class.getSimpleName());
    private String currentElement;
    private DrugBuilder drugBuilder;
    private VersionBuilder versionBuilder = new VersionBuilder();
    private CertificateBuilder certificateBuilder = new CertificateBuilder();
    private DrugPackageBuilder drugPackageBuilder = new DrugPackageBuilder();
    private DosageBuilder dosageBuilder = new DosageBuilder();
    private List<Drug> drugs = new ArrayList<>();

    @Override
    public List<Drug> parse(File file) {
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
        return drugs;
    }

    @Override
    public void startDocument() throws SAXException {
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentElement = qName;
        switch (currentElement) {
            case XMLData.Tag.DRUG:
                drugBuilder = new DrugBuilder();
                String code = attributes.getValue(XMLData.Attribute.CODE);
                drugBuilder.buildCode(code);
                break;
            case XMLData.Tag.VERSION:
                String form = attributes.getValue(XMLData.Attribute.FORM);
                VersionType versionType = VersionType.valueByString(form);
                versionBuilder.buildType(versionType);
                break;
            case XMLData.Tag.CERTIFICATE:
                String register = attributes.getValue(XMLData.Attribute.REGISTER);
                DrugRegister drugRegister = DrugRegister.valueByString(register);
                certificateBuilder.buildRegister(drugRegister);
                break;
            case XMLData.Tag.PACKAGE:
                String drugPackage = attributes.getValue(XMLData.Attribute.PACKAGE_TYPE);
                DrugPackageType drugPackageType = DrugPackageType.valueByString(drugPackage);
                drugPackageBuilder.buildDrugPackegeType(drugPackageType);
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
                drugBuilder.buildName(text);
                break;
            case XMLData.Tag.PRODUCER:
                drugBuilder.buildProducer(text);
                break;
            case XMLData.Tag.GROUP:
                DrugGroup drugGroup = DrugGroup.valueByString(text);
                drugBuilder.buildGroup(drugGroup);
                break;
            case XMLData.Tag.ANALOG_NAME:
                drugBuilder.buildAnalog(text);
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
                drugPackageBuilder.buildCount(count);
                break;
            case XMLData.Tag.PRICE:
                double price = Double.parseDouble(text);
                drugPackageBuilder.buildPrice(price);
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
            case XMLData.Tag.DRUG:
                System.out.println("!!!!!!!!");
                Drug drug = drugBuilder.build();
                drugs.add(drug);
                break;
            case XMLData.Tag.VERSION:
                Version version = versionBuilder.build();
                drugBuilder.buildVersion(version);
                break;
            case XMLData.Tag.CERTIFICATE:
                Certificate certificate = certificateBuilder.build();
                versionBuilder.buildCertificate(certificate);
                break;
            case XMLData.Tag.PACKAGE:
                DrugPackage drugPackage = drugPackageBuilder.build();
                versionBuilder.buildDrugPackage(drugPackage);
                break;
            case XMLData.Tag.DOSAGE:
                Dosage dosage = dosageBuilder.build();
                versionBuilder.buildDosage(dosage);
                break;
        }
        currentElement = null;
    }
}
