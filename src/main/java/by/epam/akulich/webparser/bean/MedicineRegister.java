package by.epam.akulich.webparser.bean;

public enum MedicineRegister {
    MINISTRY_OF_HEALTH_CARE("Министерство здравохранения"),
    CETHC("Центр экспертиз и испытаний в здравоохранении");

    private String name;

    MedicineRegister(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static MedicineRegister valueByString(String name) {
        for (MedicineRegister register : MedicineRegister.values()){
            if (register.getName().equals(name)) {
                return register;
            }
        }
        return null;
    }
}
