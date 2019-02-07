package by.epam.akulich.webparser.parser;

import by.epam.akulich.webparser.bean.Certificate;
import by.epam.akulich.webparser.bean.Dosage;
import by.epam.akulich.webparser.bean.Medicine;
import by.epam.akulich.webparser.bean.MedicineGroup;
import by.epam.akulich.webparser.bean.MedicinePackage;
import by.epam.akulich.webparser.bean.MedicinePackageType;
import by.epam.akulich.webparser.bean.MedicineRegister;
import by.epam.akulich.webparser.bean.Version;
import by.epam.akulich.webparser.bean.VersionType;
import by.epam.akulich.webparser.exception.ParserException;
import com.sun.xml.internal.fastinfoset.stax.events.StartElementEvent;
import com.sun.xml.internal.stream.events.AttributeImpl;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.xml.namespace.QName;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class StAXParserTest {

    private static final Medicine MEDICINE_1 = new Medicine();
    private static final Medicine MEDICINE_2 = new Medicine();
    private static final String CODE_1 = "med_2348974";
    private static final String CODE_2 = "med_8347775";
    private static final String NAME_1 = "Адицефр";
    private static final String NAME_2 = "Азикар";
    private static final String PRODUCER_1 = "Фармакар";
    private static final String PRODUCER_2 = "Кармакар";
    private static final String ANALOG_1 = "Биотаксим";
    private static final String ANALOG_2 = "Зинекс";
    private static final String ANALOG_3 = "Азитромицин";
    private static final MedicineGroup MEDICINE_GROUP_1 = MedicineGroup.ANTIBIOTICS;
    private static final MedicineGroup MEDICINE_GROUP_2 = MedicineGroup.VITAMINS;
    private static final Version VERSION_1 = new Version();
    private static final Version VERSION_2 = new Version();
    private static final Version VERSION_3 = new Version();
    private static final VersionType VERSION_TYPE_1 = VersionType.CAPSULES;
    private static final VersionType VERSION_TYPE_2 = VersionType.POWDER;
    private static final VersionType VERSION_TYPE_3 = VersionType.CAPSULES;
    private static final Certificate CERTIFICATE_1 = new Certificate();
    private static final Certificate CERTIFICATE_2 = new Certificate();
    private static final Certificate CERTIFICATE_3 = new Certificate();
    private static final MedicinePackage MEDICINE_PACKAGE_1 = new MedicinePackage();
    private static final MedicinePackage MEDICINE_PACKAGE_2 = new MedicinePackage();
    private static final MedicinePackage MEDICINE_PACKAGE_3 = new MedicinePackage();
    private static final MedicinePackageType MEDICINE_PACKAGE_TYPE_1 = MedicinePackageType.BOX;
    private static final MedicinePackageType MEDICINE_PACKAGE_TYPE_2 = MedicinePackageType.JAR;
    private static final MedicinePackageType MEDICINE_PACKAGE_TYPE_3 = MedicinePackageType.BOX;
    private static final Dosage DOSAGE_1 = new Dosage();
    private static final Dosage DOSAGE_2 = new Dosage();
    private static final Dosage DOSAGE_3 = new Dosage();
    private static final MedicineRegister CERTIFICATE_REGISTER_1 = MedicineRegister.MINISTRY_OF_HEALTH_CARE;
    private static final MedicineRegister CERTIFICATE_REGISTER_2 = MedicineRegister.MINISTRY_OF_HEALTH_CARE;
    private static final MedicineRegister CERTIFICATE_REGISTER_3 = MedicineRegister.CETHC;
    private static final String CERTIFICATE_NUMBER_1 = "cn_A29";
    private static final String CERTIFICATE_NUMBER_2 = "cn_B29";
    private static final String CERTIFICATE_NUMBER_3 = "cn_D39";
    private static final LocalDate CERTIFICATE_ISSUE_DATE_1 = LocalDate.of(2015,10,20);
    private static final LocalDate CERTIFICATE_ISSUE_DATE_2 = LocalDate.of(2018,5,25);
    private static final LocalDate CERTIFICATE_ISSUE_DATE_3 = LocalDate.of(2017,12,31);
    private static final LocalDate CERTIFICATE_EXPIRATION_DATE_1 = LocalDate.of(2020,10,20);
    private static final LocalDate CERTIFICATE_EXPIRATION_DATE_2 = LocalDate.of(2023,5,25);
    private static final LocalDate CERTIFICATE_EXPIRATION_DATE_3 = LocalDate.of(2022,12,31);
    private static final String PACKAGE_COUNT_1 = "10 шт";
    private static final String PACKAGE_COUNT_2 = "200 мг";
    private static final String PACKAGE_COUNT_3 = "10 шт";
    private static final double PACKAGE_PRICE_1 = 25.85;
    private static final double PACKAGE_PRICE_2 = 15.85;
    private static final double PACKAGE_PRICE_3 = 20.55;
    private static final String DOSAGE_VALUE_1 = "1 шт";
    private static final String DOSAGE_VALUE_2 = "14 мг";
    private static final String DOSAGE_VALUE_3 = "3 шт";
    private static final String DOSAGE_PERIOD_1 = "2 нед";
    private static final String DOSAGE_PERIOD_2 = "7 дн";
    private static final String DOSAGE_PERIOD_3 = "5 дн";
    private static final StartElementEvent START_ELEMENT_EVENT_1 = new StartElementEvent();
    private static final QName Q_NAME_1 = new QName("name1");
    private static final AttributeImpl ATTRIBUTE_1 = new AttributeImpl();
    private InputStream stream;

    @BeforeMethod
    public void setUp() throws IOException {
        Path path = Paths.get("src/main/resource/medTest.xml");
        stream = Files.newInputStream(path);
    }

    @Test
    public void parse_InputStream_MedicineList() throws ParserException {
        List<Medicine> expected = new ArrayList<>();
        MEDICINE_1.setCode(CODE_1);
        MEDICINE_1.setName(NAME_1);
        MEDICINE_1.setProducer(PRODUCER_1);
        List<String> analogs1 = new ArrayList<>();
        analogs1.add(ANALOG_1);
        analogs1.add(ANALOG_2);
        MEDICINE_1.setAnalogs(analogs1);
        MEDICINE_1.setGroup(MEDICINE_GROUP_1);
        VERSION_1.setVersionType(VERSION_TYPE_1);
        CERTIFICATE_1.setNumber(CERTIFICATE_NUMBER_1);
        CERTIFICATE_1.setIssueDate(CERTIFICATE_ISSUE_DATE_1);
        CERTIFICATE_1.setExpirationDate(CERTIFICATE_EXPIRATION_DATE_1);
        CERTIFICATE_1.setRegister(CERTIFICATE_REGISTER_1);
        VERSION_1.setCertificate(CERTIFICATE_1);
        MEDICINE_PACKAGE_1.setType(MEDICINE_PACKAGE_TYPE_1);
        MEDICINE_PACKAGE_1.setCount(PACKAGE_COUNT_1);
        MEDICINE_PACKAGE_1.setPrice(PACKAGE_PRICE_1);
        VERSION_1.setMedicinePackage(MEDICINE_PACKAGE_1);
        DOSAGE_1.setValue(DOSAGE_VALUE_1);
        DOSAGE_1.setPeriod(DOSAGE_PERIOD_1);
        VERSION_1.setDosage(DOSAGE_1);
        VERSION_2.setVersionType(VERSION_TYPE_2);
        CERTIFICATE_2.setNumber(CERTIFICATE_NUMBER_2);
        CERTIFICATE_2.setIssueDate(CERTIFICATE_ISSUE_DATE_2);
        CERTIFICATE_2.setExpirationDate(CERTIFICATE_EXPIRATION_DATE_2);
        CERTIFICATE_2.setRegister(CERTIFICATE_REGISTER_2);
        VERSION_2.setCertificate(CERTIFICATE_2);
        MEDICINE_PACKAGE_2.setType(MEDICINE_PACKAGE_TYPE_2);
        MEDICINE_PACKAGE_2.setCount(PACKAGE_COUNT_2);
        MEDICINE_PACKAGE_2.setPrice(PACKAGE_PRICE_2);
        VERSION_2.setMedicinePackage(MEDICINE_PACKAGE_2);
        DOSAGE_2.setValue(DOSAGE_VALUE_2);
        DOSAGE_2.setPeriod(DOSAGE_PERIOD_2);
        VERSION_2.setDosage(DOSAGE_2);
        List<Version> versions1 = new ArrayList<>();
        versions1.add(VERSION_1);
        versions1.add(VERSION_2);
        MEDICINE_1.setVersions(versions1);
        MEDICINE_1.setVersionsSize(2);
        MEDICINE_2.setCode(CODE_2);
        MEDICINE_2.setName(NAME_2);
        MEDICINE_2.setProducer(PRODUCER_2);
        List<String> analogs2 = new ArrayList<>();
        analogs2.add(ANALOG_3);
        MEDICINE_2.setAnalogs(analogs2);
        MEDICINE_2.setGroup(MEDICINE_GROUP_2);
        VERSION_3.setVersionType(VERSION_TYPE_3);
        CERTIFICATE_3.setNumber(CERTIFICATE_NUMBER_3);
        CERTIFICATE_3.setIssueDate(CERTIFICATE_ISSUE_DATE_3);
        CERTIFICATE_3.setExpirationDate(CERTIFICATE_EXPIRATION_DATE_3);
        CERTIFICATE_3.setRegister(CERTIFICATE_REGISTER_3);
        VERSION_3.setCertificate(CERTIFICATE_3);
        MEDICINE_PACKAGE_3.setType(MEDICINE_PACKAGE_TYPE_3);
        MEDICINE_PACKAGE_3.setCount(PACKAGE_COUNT_3);
        MEDICINE_PACKAGE_3.setPrice(PACKAGE_PRICE_3);
        VERSION_3.setMedicinePackage(MEDICINE_PACKAGE_3);
        DOSAGE_3.setValue(DOSAGE_VALUE_3);
        DOSAGE_3.setPeriod(DOSAGE_PERIOD_3);
        VERSION_3.setDosage(DOSAGE_3);
        List<Version> versions2 = new ArrayList<>();
        versions2.add(VERSION_3);
        MEDICINE_2.setVersions(versions2);
        MEDICINE_2.setVersionsSize(1);
        expected.add(MEDICINE_1);
        expected.add(MEDICINE_2);
        StAXParser stAXParser = new StAXParser();

        List<Medicine> actual = stAXParser.parse(stream);

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void getAttributeValue_StartElement_AttributeValue() {
        ATTRIBUTE_1.setName(Q_NAME_1);
        ATTRIBUTE_1.setValue("result1");
        START_ELEMENT_EVENT_1.addAttribute(ATTRIBUTE_1);
        String expected = "result1";
        StAXParser stAXParser = new StAXParser();

        String actual = stAXParser.getAttributeValue(Q_NAME_1.getLocalPart(), null, START_ELEMENT_EVENT_1);

        Assert.assertEquals(actual, expected);
    }
}