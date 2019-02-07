package by.epam.akulich.webparser.parser;

import by.epam.akulich.webparser.bean.XMLData;
import by.epam.akulich.webparser.bean.Certificate;
import by.epam.akulich.webparser.bean.Dosage;
import by.epam.akulich.webparser.bean.Medicine;
import by.epam.akulich.webparser.bean.MedicineGroup;
import by.epam.akulich.webparser.bean.MedicinePackage;
import by.epam.akulich.webparser.bean.MedicinePackageType;
import by.epam.akulich.webparser.bean.MedicineRegister;
import by.epam.akulich.webparser.bean.Version;
import by.epam.akulich.webparser.bean.VersionType;
import by.epam.akulich.webparser.builder.CertificateBuilder;
import by.epam.akulich.webparser.builder.DosageBuilder;
import by.epam.akulich.webparser.builder.MedicineBuilder;
import by.epam.akulich.webparser.builder.MedicinePackageBuilder;
import by.epam.akulich.webparser.builder.VersionBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;

import javax.xml.stream.events.StartElement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Я создал данный абстрактный класс т.к. по сути SAX и StAX парсеры задают значения в объекты
 * по одной логике, таким образом я вынес её сюда, а затем унаследовался.
 */
public abstract class ParserHandler implements DateParser {

    private final Logger LOGGER = LogManager.getLogger(getClass().getSimpleName());
    String currentElement;
    List<Medicine> medicines;
    private MedicineBuilder medicineBuilder;
    private VersionBuilder versionBuilder;
    private CertificateBuilder certificateBuilder;
    private MedicinePackageBuilder medicinePackageBuilder;
    private DosageBuilder dosageBuilder;
    private List<Version> versions;
    private List<String> analogs;

    protected abstract String getAttributeValue(String attributeName, Attributes attributes, StartElement element);

    void handleStartDocument() {
        LOGGER.info("Parsing started");
        medicines = new ArrayList<>();
    }

    void handleStartElement(String qName, Attributes attributes, StartElement element) {
        currentElement = qName;
        switch (currentElement) {
            case XMLData.Tag.MEDICINE:
                medicineBuilder = new MedicineBuilder();
                String code = getAttributeValue(XMLData.Attribute.CODE, attributes, element);
                medicineBuilder.buildCode(code);
                break;
            case XMLData.Tag.ANALOGS:
                analogs = new ArrayList<>();
                break;
            case XMLData.Tag.VERSIONS:
                versionBuilder = new VersionBuilder();
                versions = new ArrayList<>();
                break;
            case XMLData.Tag.VERSION:
                String form = getAttributeValue(XMLData.Attribute.FORM, attributes, element);
                VersionType versionType = VersionType.valueByString(form);
                versionBuilder.buildType(versionType);
                break;
            case XMLData.Tag.CERTIFICATE:
                certificateBuilder = new CertificateBuilder();
                String register = getAttributeValue(XMLData.Attribute.REGISTER, attributes, element);
                MedicineRegister medicineRegister = MedicineRegister.valueByString(register);
                certificateBuilder.buildRegister(medicineRegister);
                break;
            case XMLData.Tag.PACKAGE:
                medicinePackageBuilder = new MedicinePackageBuilder();
                String medicinePackage = getAttributeValue(XMLData.Attribute.PACKAGE_TYPE, attributes, element);
                MedicinePackageType medicinePackageType = MedicinePackageType.valueByString(medicinePackage);
                medicinePackageBuilder.buildMedicinePackageType(medicinePackageType);
                break;
            case XMLData.Tag.DOSAGE:
                dosageBuilder = new DosageBuilder();
                break;
        }
    }

    void handleCharacters(String text) {
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
                analogs.add(text);
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
                medicinePackageBuilder.buildCount(text);
                break;
            case XMLData.Tag.PRICE:
                double price = Double.parseDouble(text);
                medicinePackageBuilder.buildPrice(price);
                break;
            case XMLData.Tag.DOSAGE_VALUE:
                dosageBuilder.buildValue(text);
                break;
            case XMLData.Tag.DOSAGE_PERIOD:
                dosageBuilder.buildPeriod(text);
                break;
        }
    }

    void handleEndElement(String qName){
        switch (qName) {
            case XMLData.Tag.MEDICINE:
                Medicine medicine = medicineBuilder.build();
                medicines.add(medicine);
                break;
            case XMLData.Tag.ANALOGS:
                medicineBuilder.buildAnalogs(analogs);
                break;
            case XMLData.Tag.VERSIONS:
                medicineBuilder.buildVersions(versions);
                break;
            case XMLData.Tag.VERSION:
                Version version = versionBuilder.build();
                versions.add(version);
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

    void handleEndDocument() {
        LOGGER.info("Medicine parsed");
    }
}
