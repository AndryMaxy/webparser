package by.epam.akulich.webparser.bean;

public class Version {
    private VersionType versionType;
    private Certificate certificate;
    private DrugPackage drugPackage;
    private Dosage dosage;

    public VersionType getVersionType() {
        return versionType;
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public DrugPackage getDrugPackage() {
        return drugPackage;
    }

    public Dosage getDosage() {
        return dosage;
    }

    public void setVersionType(VersionType versionType) {
        this.versionType = versionType;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    public void setDrugPackage(DrugPackage drugPackage) {
        this.drugPackage = drugPackage;
    }

    public void setDosage(Dosage dosage) {
        this.dosage = dosage;
    }
}
