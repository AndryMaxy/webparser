package by.epam.akulich.webparser.builder;

import by.epam.akulich.webparser.bean.Drug;
import by.epam.akulich.webparser.bean.DrugGroup;
import by.epam.akulich.webparser.bean.Version;

import java.util.ArrayList;
import java.util.List;

public class DrugBuilder {
    private String code;
    private String name;
    private String producer;
    private DrugGroup group;
    private List<String> analogs;
    private List<Version> versions;

    public DrugBuilder buildCode(String code) {
        this.code = code;
        return this;
    }

    public DrugBuilder buildName(String name) {
        this.name = name;
        return this;
    }

    public DrugBuilder buildProducer(String producer) {
        this.producer = producer;
        return this;
    }

    public DrugBuilder buildGroup(DrugGroup group) {
        this.group = group;
        return this;
    }

    public DrugBuilder buildAnalog(String analog) {
        if (this.analogs == null) {
            this.analogs = new ArrayList<>();
        }
        analogs.add(analog);
        return this;
    }

    public DrugBuilder buildVersion(Version version) {
        if (this.versions == null) {
            this.versions = new ArrayList<>();
        }
        versions.add(version);
        return this;
    }

    public Drug build(){
        Drug drug = new Drug();
        drug.setCode(code);
        drug.setName(name);
        drug.setProducer(producer);
        drug.setGroup(group);
        drug.setAnalogs(analogs);
        drug.setVersions(versions);
        return drug;
    }
}
