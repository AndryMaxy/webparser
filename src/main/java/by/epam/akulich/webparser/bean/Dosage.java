package by.epam.akulich.webparser.bean;

public class Dosage {
    private int value;
    private DosageType type;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public DosageType getType() {
        return type;
    }

    public void setType(DosageType type) {
        this.type = type;
    }
}
