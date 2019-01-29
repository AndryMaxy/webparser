package by.epam.akulich.webparser.bean;

public class DrugPackage {
    private int count;
    private double price;
    private DrugPackageType type;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public DrugPackageType getType() {
        return type;
    }

    public void setType(DrugPackageType type) {
        this.type = type;
    }
}
