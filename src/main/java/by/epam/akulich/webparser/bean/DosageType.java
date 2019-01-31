package by.epam.akulich.webparser.bean;

public enum DosageType {
    MILLILITER("миллилитр"),
    MILLIGRAM("миллиграмм"),
    COUNT("штук");

    private String name;

    DosageType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static DosageType valueByString(String name) {
        for (DosageType type : DosageType.values()){
            if (type.getName().equals(name)) {
                return type;
            }
        }
        return null;
    }
}
