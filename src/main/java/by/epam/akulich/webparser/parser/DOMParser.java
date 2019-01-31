package by.epam.akulich.webparser.parser;

import by.epam.akulich.webparser.XMLData;
import by.epam.akulich.webparser.bean.DosageType;
import by.epam.akulich.webparser.bean.Drug;
import by.epam.akulich.webparser.bean.DrugGroup;
import by.epam.akulich.webparser.bean.DrugPackageType;
import by.epam.akulich.webparser.bean.DrugRegister;
import by.epam.akulich.webparser.bean.VersionType;
import by.epam.akulich.webparser.builder.CertificateBuilder;
import by.epam.akulich.webparser.builder.DosageBuilder;
import by.epam.akulich.webparser.builder.DrugBuilder;
import by.epam.akulich.webparser.builder.DrugPackageBuilder;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DOMParser implements IParser {

    private static final Logger LOGGER = LogManager.getLogger(DOMParser.class.getSimpleName());
    private DrugBuilder drugBuilder = new DrugBuilder();
    private VersionBuilder versionBuilder = new VersionBuilder();
    private CertificateBuilder certificateBuilder = new CertificateBuilder();
    private DrugPackageBuilder drugPackageBuilder = new DrugPackageBuilder();
    private DosageBuilder dosageBuilder = new DosageBuilder();
    private List<Drug> drugs = new ArrayList<>();

    public static void main(String[] args) {
        File file = new File("medicine.xml");
        IParser parser = new MySAXParser();
        parser.parse(file);
    }

    @Override
    public List<Drug> parse(File file) {
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
        NodeList drugList = document.getElementsByTagName(XMLData.Tag.DRUG);
        for (int i = 0; i < drugList.getLength(); i++) {
            Element drugElement = (Element) drugList.item(i);
            String code = drugElement.getAttribute(XMLData.Attribute.CODE);
            drugBuilder.buildCode(code);
            NodeList drugChildren = drugElement.getChildNodes();
            for (int j = 0; j < drugChildren.getLength(); j++) {
                Node node = drugChildren.item(j);
                if (isElement(node)) {
                    Element element = (Element) node;
                    switch (element.getNodeName()) {
                        case XMLData.Tag.NAME:
                            drugBuilder.buildName(element.getTextContent());
                            break;
                        case XMLData.Tag.PRODUCER:
                            drugBuilder.buildProducer(element.getTextContent());
                            break;
                        case XMLData.Tag.GROUP:
                            String group = element.getTextContent();
                            drugBuilder.buildGroup(DrugGroup.valueByString(group));
                            break;
                        case XMLData.Tag.ANALOGS:
                            setAnalogs(element);
                            break;
                        case XMLData.Tag.VERSIONS:
                            setVersions(element);
                    }
                }
            }
            drugs.add(drugBuilder.build());
        }
        return drugs;
    }

    private void setAnalogs(Node node) {
        NodeList analogs = node.getChildNodes();
        for (int i = 0; i < analogs.getLength(); i++) {
            Node child = analogs.item(i);
            if (child.getNodeName().equals(XMLData.Tag.ANALOG_NAME)) {
                String content = child.getTextContent();
                drugBuilder.buildAnalog(content);
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
                                createDrugPackage(childElement);
                                break;
                            case XMLData.Tag.DOSAGE:
                                createDosage(childElement);
                                break;
                        }
                    }
                }
            }
            drugBuilder
                    .buildVersion(versionBuilder.buildCertificate(certificateBuilder.build())
                    .buildDrugPackage(drugPackageBuilder.build())
                    .buildDosage(dosageBuilder.build())
                    .build());
        }

    }

    private void createCertificate(Element element) {
        String register = element.getAttribute(XMLData.Attribute.REGISTER);
        DrugRegister drugRegister = DrugRegister.valueByString(register);
        certificateBuilder.buildRegister(drugRegister);
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

    private void createDrugPackage(Element element) {
        String packageType = element.getAttribute(XMLData.Attribute.PACKAGE_TYPE);
        DrugPackageType drugPackageType = DrugPackageType.valueByString(packageType);
        drugPackageBuilder.buildDrugPackegeType(drugPackageType);
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node child = childNodes.item(i);
            System.out.println("childs " + child.getTextContent());
            switch (child.getNodeName()) {
                case XMLData.Tag.COUNT:
                    System.out.println("count " + child.getTextContent());
                    int count = Integer.parseInt(child.getTextContent());
                    drugPackageBuilder.buildCount(count);
                    break;
                case XMLData.Tag.PRICE:
                    double price = Double.parseDouble(child.getTextContent());
                    drugPackageBuilder.buildPrice(price);
                    break;
            }
        }
    }

    private void createDosage(Element element) {
        String dosageType = element.getAttribute(XMLData.Attribute.DOSAGE_TYPE);
        DosageType drugPackageType = DosageType.valueByString(dosageType);
        dosageBuilder.buildType(drugPackageType);
        int value = Integer.parseInt(element.getTextContent());
        dosageBuilder.buildValue(value);
    }
}
