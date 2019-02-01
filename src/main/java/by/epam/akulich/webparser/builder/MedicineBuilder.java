package by.epam.akulich.webparser.builder;

import by.epam.akulich.webparser.bean.Medicine;
import by.epam.akulich.webparser.bean.MedicineGroup;
import by.epam.akulich.webparser.bean.Version;

import java.util.ArrayList;
import java.util.List;

public class MedicineBuilder {
    private String code;
    private String name;
    private String producer;
    private MedicineGroup group;
    private List<String> analogs;
    private List<Version> versions;

    public MedicineBuilder buildCode(String code) {
        this.code = code;
        return this;
    }

    public MedicineBuilder buildName(String name) {
        this.name = name;
        return this;
    }

    public MedicineBuilder buildProducer(String producer) {
        this.producer = producer;
        return this;
    }

    public MedicineBuilder buildGroup(MedicineGroup group) {
        this.group = group;
        return this;
    }

    public MedicineBuilder buildAnalog(String analog) {
        if (this.analogs == null) {
            this.analogs = new ArrayList<>();
        }
        analogs.add(analog);
        return this;
    }

    public MedicineBuilder buildVersion(Version version) {
        if (this.versions == null) {
            this.versions = new ArrayList<>();
        }
        versions.add(version);
        return this;
    }

    public Medicine build(){
        Medicine medicine = new Medicine();
        medicine.setCode(code);
        medicine.setName(name);
        medicine.setProducer(producer);
        medicine.setGroup(group);
        medicine.setAnalogs(analogs);
        medicine.setVersions(versions);
        return medicine;
    }
}
