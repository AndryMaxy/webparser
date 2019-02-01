package by.epam.akulich.webparser.bean;

public enum MedicineGroup {
    ANTIBIOTICS("Антибиотики"),
    VITAMINS("Витамины"),
    FRO_THROAT("Для горла"),
    FOR_NOSE("Для носа");

    private String name;

    MedicineGroup(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static MedicineGroup valueByString(String name) {
        for (MedicineGroup drug : MedicineGroup.values()){
            if (drug.getName().equals(name)) {
                return drug;
            }
        }
        return null;
    }
}
