package by.epam.akulich.webparser.bean;

public enum MedicinePackageType {
    JAR("Банка"),
    BOX("Коробка"),
    PASTILLE("Пастилка");

    private String name;

    MedicinePackageType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static MedicinePackageType valueByString(String name) {
        for (MedicinePackageType type : MedicinePackageType.values()){
            if (type.getName().equals(name)) {
                return type;
            }
        }
        return null;
    }
}
