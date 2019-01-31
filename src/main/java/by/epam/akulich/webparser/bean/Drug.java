package by.epam.akulich.webparser.bean;

import java.util.ArrayList;
import java.util.List;

public class Drug {
    private String code;
    private String name;
    private String producer;
    private DrugGroup group;
    private List<String> analogs;
    private List<Version> versions;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public DrugGroup getGroup() {
        return group;
    }

    public void setGroup(DrugGroup group) {
        this.group = group;
    }

    public List<String> getAnalogs() {
        return analogs;
    }

    public void setAnalogs(List<String> analogs) {
        this.analogs = analogs;
    }

    public void addAnalog(String analog) {
        if (this.analogs == null) {
            this.analogs = new ArrayList<>();
        }
        this.analogs.add(analog);
    }

    public List<Version> getVersions() {
        return versions;
    }

    public void setVersions(List<Version> versions) {
        if (this.versions == null) {
            this.versions = new ArrayList<>();
        }
        this.versions = versions;
    }

    public void addVersion(Version version) {
        this.versions.add(version);
    }
}
