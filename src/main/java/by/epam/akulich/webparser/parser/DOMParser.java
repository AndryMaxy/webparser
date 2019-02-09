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
import by.epam.akulich.webparser.exception.ParserException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DOMParser implements IParser, DateParser {

    private static final Logger LOGGER = LogManager.getLogger(DOMParser.class.getSimpleName());
    private MedicineBuilder medicineBuilder = new MedicineBuilder();
    private List<Medicine> medicines = new ArrayList<>();

    @Override
    public List<Medicine> parse(InputStream inputStream) throws ParserException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setIgnoringElementContentWhitespace(true);

        DocumentBuilder documentBuilder;
        Document document;
        try {
            documentBuilder = builderFactory.newDocumentBuilder();
            document = documentBuilder.parse(inputStream);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new ParserException(e);
        }
        NodeList medicineList = document.getElementsByTagName(XMLData.Tag.MEDICINE);
        for (int i = 0; i < medicineList.getLength(); i++) {
            Element medicineElement = (Element) medicineList.item(i);
            Medicine medicine = buildMedicine(medicineElement);
            medicines.add(medicine);
        }
        LOGGER.info("Medicines parsed");
        return medicines;
    }

    private Medicine buildMedicine(Element medicineElement){
        String code = medicineElement.getAttribute(XMLData.Attribute.CODE);

        String name = getTextContent(medicineElement, XMLData.Tag.NAME);
        String producer = getTextContent(medicineElement, XMLData.Tag.PRODUCER);

        NodeList analogList = medicineElement.getElementsByTagName(XMLData.Tag.ANALOG_NAME);
        List<String> analogs = getAnalogs(analogList);

        String group = getTextContent(medicineElement, XMLData.Tag.GROUP);
        MedicineGroup medicineGroup = MedicineGroup.valueByString(group);

        NodeList versionList = medicineElement.getElementsByTagName(XMLData.Tag.VERSION);
        List<Version> versions = getVersions(versionList);
        return medicineBuilder.buildCode(code)
                .buildName(name)
                .buildProducer(producer)
                .buildAnalogs(analogs)
                .buildGroup(medicineGroup)
                .buildVersions(versions)
                .build();
    }

    private List<String> getAnalogs(NodeList analogs) {
        List<String> analogsName = new ArrayList<>();
        for (int i = 0; i < analogs.getLength(); i++) {
            Node child = analogs.item(i);
            String content = child.getTextContent();
            analogsName.add(content);
        }
        return analogsName;
    }

    private List<Version> getVersions(NodeList versions) {
        List<Version> versionList = new ArrayList<>();
        for (int i = 0; i < versions.getLength(); i++) {
            Element versionEl = (Element) versions.item(i);
            Version version = buildVersion(versionEl);
            versionList.add(version);
        }
        return versionList;
    }

    private Version buildVersion(Element element) {
        VersionBuilder builder = new VersionBuilder();

        String versionTypeStr = element.getAttribute(XMLData.Attribute.FORM);
        VersionType versionType = VersionType.valueByString(versionTypeStr);

        Element certificateEl = toElement(element, XMLData.Tag.CERTIFICATE);
        Certificate certificate = buildCertificate(certificateEl);

        Element packageEl = toElement(element, XMLData.Tag.PACKAGE);
        MedicinePackage medicinePackage = buildMedicinePackage(packageEl);

        Dosage dosage = buildDosage(element);
        return builder.buildType(versionType)
                .buildCertificate(certificate)
                .buildMedicinePackage(medicinePackage)
                .buildDosage(dosage)
                .build();
    }

    private Dosage buildDosage(Element element) {
        DosageBuilder builder = new DosageBuilder();

        String dosageValue = getTextContent(element, XMLData.Tag.DOSAGE_VALUE);
        String dosagePeriod = getTextContent(element, XMLData.Tag.DOSAGE_PERIOD);

        return builder.buildValue(dosageValue)
                .buildPeriod(dosagePeriod)
                .build();
    }

    private MedicinePackage buildMedicinePackage(Element element){
        MedicinePackageBuilder builder = new MedicinePackageBuilder();

        String packageTypeStr = element.getAttribute(XMLData.Attribute.PACKAGE_TYPE);
        MedicinePackageType packageType = MedicinePackageType.valueByString(packageTypeStr);

        String count = getTextContent(element, XMLData.Tag.COUNT);
        String priceStr = getTextContent(element, XMLData.Tag.PRICE);
        double price = Double.parseDouble(priceStr);

        return builder.buildMedicinePackageType(packageType)
                .buildCount(count)
                .buildPrice(price)
                .build();
    }

    private Certificate buildCertificate(Element element) {
        CertificateBuilder builder = new CertificateBuilder();

        String registerStr = element.getAttribute(XMLData.Attribute.REGISTER);
        MedicineRegister register = MedicineRegister.valueByString(registerStr);

        String number = getTextContent(element, XMLData.Tag.NUMBER);

        String issueDateStr = getTextContent(element, XMLData.Tag.ISSUE_DATE);
        LocalDate issueDate = parseDate(issueDateStr);

        String expirationDateStr = getTextContent(element, XMLData.Tag.EXPIRATION_DATE);
        LocalDate expirationDate = parseDate(expirationDateStr);

        return builder.buildNumber(number)
                .buildRegister(register)
                .buildIssueDate(issueDate)
                .buildExpirationDate(expirationDate)
                .build();
    }

    private String getTextContent(Element element, String tag){
        NodeList list = element.getElementsByTagName(tag);
        Node node = list.item(0);
        return node.getTextContent();
    }

    private Element toElement(Element inputElement, String tag){
        NodeList nodeList = inputElement.getElementsByTagName(tag);
        return (Element) nodeList.item(0);
    }
}
