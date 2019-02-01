package by.epam.akulich.webparser.bean;

public class MedicinePackage {
    private int count;
    private double price;
    private MedicinePackageType type;

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

    public MedicinePackageType getType() {
        return type;
    }

    public void setType(MedicinePackageType type) {
        this.type = type;
    }
}
