package by.epam.akulich.webparser.bean;

public class Firm {
    private String name;
    private FormType formType;
    private Certificate certificate;
    private DrugPackage drugPackage;
    private Dosage dosage;

    private Firm(){}

    public String getName() {
        return name;
    }

    public FormType getFormType() {
        return formType;
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

    public static class Builder {
        private String name;
        private FormType formType;
        private Certificate certificate;
        private DrugPackage drugPackage;
        private Dosage dosage;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setFormType(FormType formType) {
            this.formType = formType;
            return this;
        }

        public Builder setCertificate(Certificate certificate) {
            this.certificate = certificate;
            return this;
        }

        public Builder setDrugPackage(DrugPackage drugPackage) {
            this.drugPackage = drugPackage;
            return this;
        }

        public Builder setDosage(Dosage dosage) {
            this.dosage = dosage;
            return this;
        }

        public Firm build() {
            Firm firm = new Firm();
            firm.name = name;
            firm.formType = formType;
            firm.certificate = certificate;
            firm.drugPackage = drugPackage;
            firm.dosage = dosage;
            return firm;
        }
    }
}
