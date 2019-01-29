package by.epam.akulich.webparser.bean;

public enum FormType {
    CAPSULES("Капсулы"),
    TABLETS("Таблетки"),
    POWDER("Порошок"),
    DROPS("Капли");

    private String name;

    FormType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static FormType valueByString(String name) {
        for (FormType form : FormType.values()){
            if (form.getName().equals(name)) {
                return form;
            }
        }
        return null;
    }
}
