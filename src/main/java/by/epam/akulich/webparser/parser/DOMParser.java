package by.epam.akulich.webparser.parser;

import by.epam.akulich.webparser.XMLData;
import by.epam.akulich.webparser.bean.DosageType;
import by.epam.akulich.webparser.bean.Medicine;
import by.epam.akulich.webparser.bean.MedicineGroup;
import by.epam.akulich.webparser.bean.MedicinePackageType;
import by.epam.akulich.webparser.bean.MedicineRegister;
import by.epam.akulich.webparser.bean.VersionType;
import by.epam.akulich.webparser.builder.CertificateBuilder;
import by.epam.akulich.webparser.builder.DosageBuilder;
import by.epam.akulich.webparser.builder.MedicineBuilder;
import by.epam.akulich.webparser.builder.MedicinePackageBuilder;
import by.epam.akulich.webparser.builder.VersionBuilder;
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

public class DOMParser implements IParser {

    private static final Logger LOGGER = LogManager.getLogger(DOMParser.class.getSimpleName());
    private MedicineBuilder medicineBuilder = new MedicineBuilder();
    private VersionBuilder versionBuilder = new VersionBuilder();
    private CertificateBuilder certificateBuilder = new CertificateBuilder();
    private MedicinePackageBuilder medicinePackageBuilder = new MedicinePackageBuilder();
    private DosageBuilder dosageBuilder = new DosageBuilder();
    private List<Medicine> medicines = new ArrayList<>();

    public static void main(String[] args) {
        File file = new File("medicine.xml");
        IParser parser = new MySAXParser();
        parser.parse(file);
    }

    @Override
    public List<Medicine> parse(File file) {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder documentBuilder;
        Document document = null;
        try {
            documentBuilder = builderFactory.newDocumentBuilder();
            document = documentBuilder.parse(file);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            LOGGER.error("Parser error", e);
        }
        if (document == null) {
            LOGGER.info("document is null");
            return null;
        }
        NodeList medicineList = document.getElementsByTagName(XMLData.Tag.MEDICINE);
        for (int i = 0; i < medicineList.getLength(); i++) {
            Element medicineElement = (Element) medicineList.item(i);
            String code = medicineElement.getAttribute(XMLData.Attribute.CODE);
            medicineBuilder.buildCode(code);
            NodeList medicineChildren = medicineElement.getChildNodes();
            for (int j = 0; j < medicineChildren.getLength(); j++) {
                Node node = medicineChildren.item(j);
                if (isElement(node)) {
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
                            setAnalogs(element);
                            break;
                        case XMLData.Tag.VERSIONS:
                            setVersions(element);
                    }
                }
            }
            medicines.add(medicineBuilder.build());
        }
        return medicines;
    }

    private void setAnalogs(Node node) {
        NodeList analogs = node.getChildNodes();
        for (int i = 0; i < analogs.getLength(); i++) {
            Node child = analogs.item(i);
            if (child.getNodeName().equals(XMLData.Tag.ANALOG_NAME)) {
                String content = child.getTextContent();
                medicineBuilder.buildAnalog(content);
            }
        }
    }

    private boolean isElement(Node node) {
        return node.getNodeType() == Node.ELEMENT_NODE;
    }

    public void setVersions(Element element) {
        NodeList versions = element.getChildNodes();
        for (int i = 0; i < versions.getLength(); i++) {
            Node versionNode = versions.item(i);
            if (isElement(versionNode)) {
                Element version = (Element) versionNode;
                String form = version.getAttribute(XMLData.Attribute.FORM);
                VersionType versionType = VersionType.valueByString(form);
                versionBuilder.buildType(versionType);
                NodeList versionChildren = version.getChildNodes();
                for (int j = 0; j < versionChildren.getLength(); j++) {
                    Node node = versionChildren.item(j);
                    if (isElement(node)) {
                        Element childElement = (Element) node;
                        switch (childElement.getNodeName()) {
                            case XMLData.Tag.CERTIFICATE:
                                createCertificate(childElement);
                                break;
                            case XMLData.Tag.PACKAGE:
                                createMedicinePackage(childElement);
                                break;
                            case XMLData.Tag.DOSAGE:
                                createDosage(childElement);
                                break;
                        }
                    }
                }
            }
            medicineBuilder
                    .buildVersion(versionBuilder.buildCertificate(certificateBuilder.build())
                    .buildMedicinePackage(medicinePackageBuilder.build())
                    .buildDosage(dosageBuilder.build())
                    .build());
        }

    }

    private void createCertificate(Element element) {
        String register = element.getAttribute(XMLData.Attribute.REGISTER);
        MedicineRegister medicineRegister = MedicineRegister.valueByString(register);
        certificateBuilder.buildRegister(medicineRegister);
        NodeList analogs = element.getChildNodes();
        for (int i = 0; i < analogs.getLength(); i++) {
            Node child = analogs.item(i);
            LocalDate date;
            switch (child.getNodeName()) {
                case XMLData.Tag.NUMBER:
                    certificateBuilder.buildNumber(child.getTextContent());
                    break;
                case XMLData.Tag.ISSUE_DATE:
                    date = parseDate(child.getTextContent());
                    certificateBuilder.buildIssueDate(date);
                    break;
                case XMLData.Tag.EXPIRATION_DATE:
                    date = parseDate(child.getTextContent());
                    certificateBuilder.buildExpirationDate(date);
                    break;
            }
        }
    }

    private void createMedicinePackage(Element element) {
        String packageType = element.getAttribute(XMLData.Attribute.PACKAGE_TYPE);
        MedicinePackageType medicinePackageType = MedicinePackageType.valueByString(packageType);
        medicinePackageBuilder.buildMedicinePackageType(medicinePackageType);
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node child = childNodes.item(i);
            switch (child.getNodeName()) {
                case XMLData.Tag.COUNT:
                    int count = Integer.parseInt(child.getTextContent());
                    medicinePackageBuilder.buildCount(count);
                    break;
                case XMLData.Tag.PRICE:
                    double price = Double.parseDouble(child.getTextContent());
                    medicinePackageBuilder.buildPrice(price);
                    break;
            }
        }
    }

    private void createDosage(Element element) {
        String dosageType = element.getAttribute(XMLData.Attribute.DOSAGE_TYPE);
        DosageType medicinePackageType = DosageType.valueByString(dosageType);
        dosageBuilder.buildType(medicinePackageType);
        int value = Integer.parseInt(element.getTextContent());
        dosageBuilder.buildValue(value);
    }
}
