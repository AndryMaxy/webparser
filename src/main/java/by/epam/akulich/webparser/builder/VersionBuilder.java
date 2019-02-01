package by.epam.akulich.webparser.builder;

import by.epam.akulich.webparser.bean.Certificate;
import by.epam.akulich.webparser.bean.Dosage;
import by.epam.akulich.webparser.bean.MedicinePackage;
import by.epam.akulich.webparser.bean.Version;
import by.epam.akulich.webparser.bean.VersionType;

public class VersionBuilder {
    private VersionType type;
    private Certificate certificate;
    private MedicinePackage medicinePackage;
    private Dosage dosage;

    public VersionBuilder buildType(VersionType type) {
        this.type = type;
        return this;
    }

    public VersionBuilder buildCertificate(Certificate certificate) {
        this.certificate = certificate;
        return this;
    }

    public VersionBuilder buildMedicinePackage(MedicinePackage medicinePackage) {
        this.medicinePackage = medicinePackage;
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
        version.setMedicinePackage(medicinePackage);
        version.setDosage(dosage);
        return version;
    }
}
