package by.epam.akulich.webparser;

public final class XMLData {

    public static final class Tag {
        public static final String ROOT = "ms:medicine";
        public static final String DRUG = "ms:drug";
        public static final String NAME = "ms:name";
        public static final String PRODUCER = "ms:producer";
        public static final String GROUP = "ms:group";
        public static final String ANALOGS = "ms:analogs";
        public static final String ANALOG_NAME = "ms:analogName";
        public static final String VERSIONS = "ms:versions";
        public static final String FIRM = "ms:firm";
        public static final String FIRM_NAME = "ms:firmName";
        public static final String CERTIFICATE = "ms:certificate";
        public static final String NUMBER = "ms:number";
        public static final String ISSUE_DATE = "ms:issueDate";
        public static final String EXPIRATION_DATE = "ms:expirationDate";
        public static final String PACKAGE = "ms:package";
        public static final String COUNT = "ms:count";
        public static final String PRICE = "ms:price";
        public static final String DOSAGE = "ms:dosage";
    }

    public static final class Attribute {
        public static final String CODE = "code";
        public static final String FORM = "form";
        public static final String REGISTER = "register";
        public static final String PACKAGE_TYPE = "packageType";
        public static final String DOSAGE_TYPE = "dosageType";
    }
}
