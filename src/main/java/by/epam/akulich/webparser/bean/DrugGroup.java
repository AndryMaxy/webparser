package by.epam.akulich.webparser.bean;

public enum DrugGroup {
    ANTIBIOTICS("Антибиотики"),
    VITAMINS("Витамины"),
    FRO_THROAT("Для горла"),
    FOR_NOSE("Для носа");

    private String name;

    DrugGroup(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static DrugGroup valueByString(String name) {
        for (DrugGroup drug : DrugGroup.values()){
            if (drug.getName().equals(name)) {
                return drug;
            }
        }
        return null;
    }
}
