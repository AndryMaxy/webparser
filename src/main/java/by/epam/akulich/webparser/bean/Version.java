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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Version)) {
            return false;
        }
        Version version = (Version) o;
        return versionType == version.versionType &&
                certificate.equals(version.certificate) &&
                medicinePackage.equals(version.medicinePackage) &&
                dosage.equals(version.dosage);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (versionType == null ? 0 : versionType.hashCode());
        result = prime * result + (certificate == null ? 0 : certificate.hashCode());
        result = prime * result + (medicinePackage == null ? 0 : medicinePackage.hashCode());
        result = prime * result + (dosage == null ? 0 : dosage.hashCode());
        return result;
    }
}
