package by.epam.akulich.webparser.bean;

public enum VersionType {
    CAPSULES("Капсулы"),
    TABLETS("Таблетки"),
    POWDER("Порошок"),
    DROPS("Капли"),
    SPRAY("Спрей");

    private String name;

    VersionType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static VersionType valueByString(String name) {
        for (VersionType form : VersionType.values()){
            if (form.getName().equals(name)) {
                return form;
            }
        }
        return null;
    }
}
