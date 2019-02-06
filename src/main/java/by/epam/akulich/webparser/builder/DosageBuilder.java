package by.epam.akulich.webparser.builder;

import by.epam.akulich.webparser.bean.Dosage;

public class DosageBuilder {
    private String value;
    private String period;

    public DosageBuilder buildValue(String value){
        this.value = value;
        return this;
    }

    public DosageBuilder buildPeriod(String period){
        this.period = period;
        return this;
    }

    public Dosage build(){
        Dosage dosage = new Dosage();
        dosage.setValue(value);
        dosage.setPeriod(period);
        return dosage;
    }
}
