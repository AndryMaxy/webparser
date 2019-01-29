package by.epam.akulich.webparser.parser;

import by.epam.akulich.webparser.XMLData;
import by.epam.akulich.webparser.bean.Certificate;
import by.epam.akulich.webparser.bean.Dosage;
import by.epam.akulich.webparser.bean.DosageType;
import by.epam.akulich.webparser.bean.Drug;
import by.epam.akulich.webparser.bean.DrugGroup;
import by.epam.akulich.webparser.bean.DrugPackage;
import by.epam.akulich.webparser.bean.DrugPackageType;
import by.epam.akulich.webparser.bean.DrugRegister;
import by.epam.akulich.webparser.bean.Firm;
import by.epam.akulich.webparser.bean.FormType;
import by.epam.akulich.webparser.servlet.ParserServlet;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class DOMParser extends BaseBuilder implements IParser {

    private static final Logger LOGGER = LogManager.getLogger(ParserServlet.class.getSimpleName());
    private Document document;
    private Drug drug;
    private Firm firm;
    private List<Drug> drugs = new ArrayList<>();

    @Override
    public List<Drug> parse(InputStream inputStream) {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        try {
            documentBuilder = builderFactory.newDocumentBuilder();
            document = documentBuilder.parse(inputStream);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        if (document == null) {
            LOGGER.info("document is null");
            return null;
        }
        document.getDocumentElement().normalize();
        NodeList drugs = document.getElementsByTagName(XMLData.Tag.ROOT);
        for (int i = 0; i < drugs.getLength(); i++) {
            nodes.add(drugs.item(i));
        }
        for (int i = 0; i < drugs.getLength(); i++) {
            drug = new Drug();
            Node drug = drugs.item(i);
            if (drug.getNodeType() == Node.ELEMENT_NODE) {
                Element drugElement = (Element) drug;
                String code = drugElement.getAttribute(XMLData.Attribute.CODE);
                buildCode(code);
                NodeList drugChildren = drug.getChildNodes();
                for (int j = 0; j < drugChildren.getLength(); j++) {
                    Node node = drugChildren.item(i);
                    if (drug.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;

                        switch (element.getNodeName()) {
                            case XMLData.Tag.NAME:
                                buildName(element.getTextContent());
                                break;
                            case XMLData.Tag.PRODUCER:
                                buildProducer(element.getTextContent());
                                break;
                            case XMLData.Tag.GROUP:
                                String group = element.getTextContent();
                                buildGroup(DrugGroup.valueByString(group));
                                break;
                            case XMLData.Tag.ANALOGS:
                                setAnalogs(element);
                                break;
                            case XMLData.Tag.VERSIONS:
                                setVersions(element);
                        }
                    }
                }
            }
            this.drugs.add(this.drug);
        }
        return this.drugs;
    }

    public void setVersions(Element element) {
        NodeList firms = element.getChildNodes();
        for (int i = 0; i < firms.getLength(); i++) {
            Element firm = (Element) firms.item(i);
            String form = firm.getAttribute(XMLData.Attribute.FORM);
            FormType formType = FormType.valueByString(form);
            NodeList firmChildren = firm.getChildNodes();
            String firmName = null;
            Certificate certificate = null;
            DrugPackage drugPackage = null;
            Dosage dosage = null;
            for (int j = 0; j < firmChildren.getLength(); j++) {
                switch (firm.getNodeName()) {
                    case XMLData.Tag.FIRM_NAME:
                        firmName = firm.getTextContent();
                        break;
                    case XMLData.Tag.CERTIFICATE:
                        certificate = createCertificate(element);
                        break;
                    case XMLData.Tag.PACKAGE:
                        drugPackage = createDrugPackage(element);
                        break;
                    case XMLData.Tag.DOSAGE:
                        dosage = createDosage(element);
                        break;
                }
            }
            this.firm = new Firm.Builder()
                    .setName(firmName)
                    .setCertificate(certificate)
                    .setDrugPackage(drugPackage)
                    .setDosage(dosage)
                    .setFormType(formType)
                    .build();
            buildFirm(this.firm);
        }

    }

    private Certificate createCertificate(Element element) {
        Certificate certificate = new Certificate();
        String register = element.getAttribute(XMLData.Attribute.REGISTER);
        DrugRegister drugRegister = DrugRegister.valueByString(register);
        certificate.setRegister(drugRegister);
        NodeList analogs = element.getChildNodes();
        for (int i = 0; i < analogs.getLength(); i++) {
            Element child = (Element) analogs.item(i);
            Date date = null;
            switch (child.getNodeName()) {
                case XMLData.Tag.NUMBER:
                    certificate.setNumber(child.getTextContent());
                    break;
                case XMLData.Tag.ISSUE_DATE:
                    date = format(child.getTextContent());
                    certificate.setIssueDate(date);
                    break;
                case XMLData.Tag.EXPIRATION_DATE:
                    date = format(child.getTextContent());
                    certificate.setExpirationDate(date);
                    break;
            }
        }
        return certificate;
    }

    private DrugPackage createDrugPackage(Element element) {
        DrugPackage drugPackage = new DrugPackage();
        String packageType = element.getAttribute(XMLData.Attribute.PACKAGE_TYPE);
        DrugPackageType drugPackageType = DrugPackageType.valueByString(packageType);
        drugPackage.setType(drugPackageType);
        NodeList analogs = element.getChildNodes();
        for (int i = 0; i < analogs.getLength(); i++) {
            Element child = (Element) analogs.item(i);
            switch (child.getNodeName()) {
                case XMLData.Tag.COUNT:
                    int count = Integer.parseInt(child.getTextContent());
                    drugPackage.setCount(count);
                    break;
                case XMLData.Tag.PRICE:
                    double price = Double.parseDouble(child.getTextContent());
                    drugPackage.setPrice(price);
                    break;
            }
        }
        return drugPackage;
    }

    private Dosage createDosage(Element element){
        Dosage dosage = new Dosage();
        String dosageType = element.getAttribute(XMLData.Attribute.DOSAGE_TYPE);
        DosageType drugPackageType = DosageType.valueByString(dosageType);
        dosage.setType(drugPackageType);
        int value = Integer.parseInt(element.getTextContent());
        dosage.setValue(value);
        return dosage;
    }

    private Date format(String text) {
        DateFormat dateFormat = DateFormat.getDateInstance();
        Date date = null;
        try {
            date = dateFormat.parse(text);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private void setAnalogs(Element element) {
        NodeList analogs = element.getChildNodes();
        for (int i = 0; i < analogs.getLength(); i++) {
            Element child = (Element) analogs.item(i);
            if (child.getTagName().equals(XMLData.Tag.ANALOG_NAME)) {
                buildAnalog(child.getTextContent());
            }
        }
    }

    private Queue<Node> nodes = new LinkedList<>();

    private boolean hasNext() {
        return !nodes.isEmpty();
    }

    private Node next() {
        Node node = nodes.peek();
        if (node.hasChildNodes()) {
            for (int i = 0; i < node.getChildNodes().getLength(); i++) {
                nodes.add(node.getChildNodes().item(i));
            }
        }
        return nodes.poll();
    }

    @Override
    public BaseBuilder buildCode(String code) {
        drug.setCode(code);
        return this;
    }

    @Override
    public BaseBuilder buildName(String name) {
        drug.setName(name);
        return this;
    }

    @Override
    public BaseBuilder buildProducer(String producer) {
        drug.setFirm(producer);
        return this;
    }

    @Override
    public BaseBuilder buildGroup(DrugGroup drugGroup) {
        return null;
    }

    @Override
    public BaseBuilder buildAnalog(String analog) {
        drug.addAnalog(analog);
        return this;
    }

    @Override
    public BaseBuilder buildFirm(Firm firm) {
        drug.addVersion(firm);
        return this;
    }
}
