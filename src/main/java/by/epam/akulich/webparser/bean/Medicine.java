package by.epam.akulich.webparser.bean;

import java.util.ArrayList;
import java.util.List;

public class Medicine {
    private String code;
    private String name;
    private String producer;
    private MedicineGroup group;
    private List<String> analogs;
    private List<Version> versions;
    private int versionsSize;

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

    public MedicineGroup getGroup() {
        return group;
    }

    public void setGroup(MedicineGroup group) {
        this.group = group;
    }

    public List<String> getAnalogs() {
        return analogs;
    }

    public void setAnalogs(List<String> analogs) {
        this.analogs = analogs;
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

    public int getVersionsSize() {
        return versionsSize;
    }

    public void setVersionsSize(int versionsSize) {
       this.versionsSize = versionsSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Medicine)) {
            return false;
        }
        Medicine medicine = (Medicine) o;
        return versionsSize == medicine.versionsSize &&
                code.equals(medicine.code) &&
                name.equals(medicine.name) &&
                producer.equals(medicine.producer) &&
                group == medicine.group &&
                analogs.equals(medicine.analogs) &&
                versions.equals(medicine.versions);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (code == null ? 0 : code.hashCode());
        result = prime * result + (name == null ? 0 : name.hashCode());
        result = prime * result + (producer == null ? 0 : producer.hashCode());
        result = prime * result + (group == null ? 0 : group.hashCode());
        result = prime * result + (analogs == null ? 0 : analogs.hashCode());
        result = prime * result + (versions == null ? 0 : versions.hashCode());
        result = prime * result + versionsSize;
        return result;
    }
}
