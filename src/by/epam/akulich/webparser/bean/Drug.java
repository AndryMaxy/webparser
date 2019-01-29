package by.epam.akulich.webparser.bean;

import java.util.ArrayList;
import java.util.List;

public class Drug {
    private String code;
    private String name;
    private String firm;
    private DrugGroup group;
    private List<String> analogs = new ArrayList<>();
    private List<Firm> versions;

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

    public String getFirm() {
        return firm;
    }

    public void setFirm(String firm) {
        this.firm = firm;
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
        this.analogs.add(analog);
    }

    public List<Firm> getVersions() {
        return versions;
    }

    public void setVersions(List<Firm> versions) {
        this.versions = versions;
    }

    public void addVersion(Firm firm) {
        this.versions.add(firm);
    }
}
