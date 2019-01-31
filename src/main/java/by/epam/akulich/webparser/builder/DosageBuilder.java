package by.epam.akulich.webparser.builder;

import by.epam.akulich.webparser.bean.Dosage;
import by.epam.akulich.webparser.bean.DosageType;

public class DosageBuilder {
    private int value;
    private DosageType type;

    public DosageBuilder buildValue(int value){
        this.value = value;
        return this;
    }

    public DosageBuilder buildType(DosageType type){
        this.type = type;
        return this;
    }

    public Dosage build(){
        Dosage dosage = new Dosage();
        dosage.setValue(value);
        dosage.setType(type);
        return dosage;
    }
}
