package by.epam.akulich.webparser.bean;

public enum DrugPackageType {
    JAR("Банка"),
    BOX("Коробка"),
    PASTILLE("Пастилка");

    private String name;

    DrugPackageType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static DrugPackageType valueByString(String name) {
        for (DrugPackageType type : DrugPackageType.values()){
            if (type.getName().equals(name)) {
                return type;
            }
        }
        return null;
    }
}
