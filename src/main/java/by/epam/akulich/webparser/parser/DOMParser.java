package by.epam.akulich.webparser.parser;

import by.epam.akulich.webparser.XMLData;
import by.epam.akulich.webparser.bean.Certificate;
import by.epam.akulich.webparser.bean.Dosage;
import by.epam.akulich.webparser.bean.DosageType;
import by.epam.akulich.webparser.bean.Medicine;
import by.epam.akulich.webparser.bean.MedicineGroup;
import by.epam.akulich.webparser.bean.MedicinePackage;
import by.epam.akulich.webparser.bean.MedicinePackageType;
import by.epam.akulich.webparser.bean.MedicineRegister;
import by.epam.akulich.webparser.bean.Version;
import by.epam.akulich.webparser.bean.VersionType;
import by.epam.akulich.webparser.builder.MedicineBuilder;
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
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DOMParser implements IParser, DateParser {

    private static final Logger LOGGER = LogManager.getLogger(DOMParser.class.getSimpleName());
    private MedicineBuilder medicineBuilder = new MedicineBuilder();
    private List<Medicine> medicines = new ArrayList<>();

    @Override
    public List<Medicine> parse(File file) throws SAXException, ParserConfigurationException, IOException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(file);

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
        medicineBuilder.buildCode(code);
        NodeList medicineChildren = medicineElement.getChildNodes();
        for (int j = 0; j < medicineChildren.getLength(); j++) {
            Node node = medicineChildren.item(j);
            if (!isElement(node)) {
                continue;
            }
            Element element = (Element) node;
            switch (element.getNodeName()) {
                case XMLData.Tag.NAME:
                    medicineBuilder.buildName(element.getTextContent());
                    break;
                case XMLData.Tag.PRODUCER:
                    medicineBuilder.buildProducer(element.getTextContent());
                    break;
                case XMLData.Tag.GROUP:
                    String group = element.getTextContent();
                    medicineBuilder.buildGroup(MedicineGroup.valueByString(group));
                    break;
                case XMLData.Tag.ANALOGS:
                    List<String> analogs = createAnalogs(element);
                    medicineBuilder.buildAnalogs(analogs);
                    break;
                case XMLData.Tag.VERSIONS:
                    List<Version> versions = createVersions(element);
                    medicineBuilder.buildVersions(versions);
            }
        }
        return medicineBuilder.build();
    }

    private List<String> createAnalogs(Node node) {
        NodeList analogs = node.getChildNodes();
        List<String> analogsName = new ArrayList<>();
        for (int i = 0; i < analogs.getLength(); i++) {
            Node child = analogs.item(i);
            if (child.getNodeName().equals(XMLData.Tag.ANALOG_NAME)) {
                String content = child.getTextContent();
                analogsName.add(content);
            }
        }
        return analogsName;
    }

    private List<Version> createVersions(Element element) {
        NodeList versions = element.getChildNodes();
        List<Version> versionList = new ArrayList<>();
        for (int i = 0; i < versions.getLength(); i++) {
            Node versionNode = versions.item(i);
            if (!isElement(versionNode)) {
                continue;
            }
            Element versionElement = (Element) versionNode;
            Version version = createVersion(versionElement);
            versionList.add(version);
        }
        return versionList;
    }

    private Version createVersion(Element versionElement){
        String form = versionElement.getAttribute(XMLData.Attribute.FORM);
        VersionType versionType = VersionType.valueByString(form);
        Version version = new Version();
        version.setVersionType(versionType);
        NodeList versionChildren = versionElement.getChildNodes();
        for (int j = 0; j < versionChildren.getLength(); j++) {
            Node node = versionChildren.item(j);
            if (!isElement(node)) {
                continue;
            }
            Element childElement = (Element) node;
            switch (childElement.getNodeName()) {
                case XMLData.Tag.CERTIFICATE:
                    Certificate certificate = createCertificate(childElement);
                    version.setCertificate(certificate);
                    break;
                case XMLData.Tag.PACKAGE:
                    MedicinePackage medicinePackage = createMedicinePackage(childElement);
                    version.setMedicinePackage(medicinePackage);
                    break;
                case XMLData.Tag.DOSAGE:
                    Dosage dosage = createDosage(childElement);
                    version.setDosage(dosage);
                    System.out.println(dosage.getValue());
                    System.out.println(dosage.getPeriod());
                    break;
            }
        }
        return version;
    }


    private Certificate createCertificate(Element element) {
        String register = element.getAttribute(XMLData.Attribute.REGISTER);
        MedicineRegister medicineRegister = MedicineRegister.valueByString(register);
        Certificate certificate = new Certificate();
        certificate.setRegister(medicineRegister);
        NodeList certificateList = element.getChildNodes();
        for (int i = 0; i < certificateList.getLength(); i++) {
            Node child = certificateList.item(i);
            LocalDate date;
            switch (child.getNodeName()) {
                case XMLData.Tag.NUMBER:
                    String number = child.getTextContent();
                    certificate.setNumber(number);
                    break;
                case XMLData.Tag.ISSUE_DATE:
                    date = parseDate(child.getTextContent());
                    certificate.setIssueDate(date);
                    break;
                case XMLData.Tag.EXPIRATION_DATE:
                    date = parseDate(child.getTextContent());
                    certificate.setExpirationDate(date);
                    break;
            }
        }
        return certificate;
    }

    private MedicinePackage createMedicinePackage(Element element) {
        String packageType = element.getAttribute(XMLData.Attribute.PACKAGE_TYPE);
        MedicinePackageType medicinePackageType = MedicinePackageType.valueByString(packageType);
        MedicinePackage medicinePackage = new MedicinePackage();
        medicinePackage.setType(medicinePackageType);
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node child = childNodes.item(i);
            switch (child.getNodeName()) {
                case XMLData.Tag.COUNT:
                    //int count = Integer.parseInt(child.getTextContent());
                    medicinePackage.setCount(child.getTextContent());
                    break;
                case XMLData.Tag.PRICE:
                    double price = Double.parseDouble(child.getTextContent());
                    medicinePackage.setPrice(price);
                    break;
            }
        }
        return medicinePackage;
    }

    private Dosage createDosage(Element element) {
        NodeList child = element.getChildNodes();
        Dosage dosage = new Dosage();
        for (int i = 0; i < child.getLength(); i++) {
            Node node = child.item(i);
            switch (node.getNodeName()) {
                case XMLData.Tag.DOSAGE_VALUE:
                    dosage.setValue(node.getTextContent());
                    break;
                case XMLData.Tag.DOSAGE_PERIOD:
                    dosage.setPeriod(node.getTextContent());
                    break;
            }
        }
        return dosage;
    }

    private boolean isElement(Node node) {
        return node.getNodeType() == Node.ELEMENT_NODE;
    }
}
