package by.epam.akulich.webparser.bean;

public class Version {
    private VersionType versionType;
    private Certificate certificate;
    private MedicinePackage medicinePackage;
    private Dosage dosage;

    public VersionType getVersionType() {
        return versionType;
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public MedicinePackage getMedicinePackage() {
        return medicinePackage;
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

    public void setMedicinePackage(MedicinePackage medicinePackage) {
        this.medicinePackage = medicinePackage;
    }

    public void setDosage(Dosage dosage) {
        this.dosage = dosage;
    }
}
