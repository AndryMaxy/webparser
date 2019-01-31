package by.epam.akulich.webparser.builder;

import by.epam.akulich.webparser.bean.DrugPackage;
import by.epam.akulich.webparser.bean.DrugPackageType;

public class DrugPackageBuilder {
    private int count;
    private double price;
    private DrugPackageType type;

    public DrugPackageBuilder buildCount(int count){
        this.count = count;
        return this;
    }

    public DrugPackageBuilder buildPrice(double price){
        this.price = price;
        return this;
    }

    public DrugPackageBuilder buildDrugPackegeType(DrugPackageType type){
        this.type = type;
        return this;
    }

    public DrugPackage build(){
        DrugPackage drugPackage = new DrugPackage();
        drugPackage.setCount(count);
        drugPackage.setPrice(price);
        drugPackage.setType(type);
        return drugPackage;
    }
}
