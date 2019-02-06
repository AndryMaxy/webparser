package by.epam.akulich.webparser.builder;

import by.epam.akulich.webparser.bean.Medicine;
import by.epam.akulich.webparser.bean.MedicineGroup;
import by.epam.akulich.webparser.bean.Version;

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

    public MedicineBuilder buildAnalogs(List<String> analogs) {
        this.analogs = analogs;
        return this;
    }

    public MedicineBuilder buildVersions(List<Version> versions) {
        this.versions = versions;
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
        medicine.setVersionsSize(versions.size());
        return medicine;
    }
}
