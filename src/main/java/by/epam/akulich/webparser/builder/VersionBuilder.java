package by.epam.akulich.webparser.builder;

import by.epam.akulich.webparser.bean.Certificate;
import by.epam.akulich.webparser.bean.Dosage;
import by.epam.akulich.webparser.bean.DrugPackage;
import by.epam.akulich.webparser.bean.Version;
import by.epam.akulich.webparser.bean.VersionType;

public class VersionBuilder {
    private VersionType type;
    private Certificate certificate;
    private DrugPackage drugPackage;
    private Dosage dosage;

    public VersionBuilder buildType(VersionType type) {
        this.type = type;
        return this;
    }

    public VersionBuilder buildCertificate(Certificate certificate) {
        this.certificate = certificate;
        return this;
    }

    public VersionBuilder buildDrugPackage(DrugPackage drugPackage) {
        this.drugPackage = drugPackage;
        return this;
    }

    public VersionBuilder buildDosage(Dosage dosage) {
        this.dosage = dosage;
        return this;
    }

    public Version build(){
        Version version = new Version();
        version.setVersionType(type);
        version.setCertificate(certificate);
        version.setDrugPackage(drugPackage);
        version.setDosage(dosage);
        return version;
    }
}
