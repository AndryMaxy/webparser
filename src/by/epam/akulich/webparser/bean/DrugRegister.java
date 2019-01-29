package by.epam.akulich.webparser.bean;

public enum DrugRegister {
    MINISTRY_OF_HEALTH_CARE("Министерство здравохранения"),
    CETHC("Центр экспертиз и испытаний в здравоохранении");

    private String name;

    DrugRegister(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static DrugRegister valueByString(String name) {
        for (DrugRegister register : DrugRegister.values()){
            if (register.getName().equals(name)) {
                return register;
            }
        }
        return null;
    }
}
