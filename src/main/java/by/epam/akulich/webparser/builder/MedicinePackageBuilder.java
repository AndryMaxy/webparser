package by.epam.akulich.webparser.builder;

import by.epam.akulich.webparser.bean.MedicinePackage;
import by.epam.akulich.webparser.bean.MedicinePackageType;

public class MedicinePackageBuilder {
    private int count;
    private double price;
    private MedicinePackageType type;

    public MedicinePackageBuilder buildCount(int count){
        this.count = count;
        return this;
    }

    public MedicinePackageBuilder buildPrice(double price){
        this.price = price;
        return this;
    }

    public MedicinePackageBuilder buildMedicinePackageType(MedicinePackageType type){
        this.type = type;
        return this;
    }

    public MedicinePackage build(){
        MedicinePackage medicinePackage = new MedicinePackage();
        medicinePackage.setCount(count);
        medicinePackage.setPrice(price);
        medicinePackage.setType(type);
        return medicinePackage;
    }
}
