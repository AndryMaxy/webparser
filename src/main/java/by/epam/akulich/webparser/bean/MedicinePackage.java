package by.epam.akulich.webparser.bean;

import java.util.Objects;

public class MedicinePackage {
    private String count;
    private double price;
    private MedicinePackageType type;

//    public int getCount() {
//        return count;
//    }
//
//    public void setCount(int count) {
//        this.count = count;
//    }


    public String getCount() {
        return count;
    }

    public void setCount(String count) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MedicinePackage)) {
            return false;
        }
        MedicinePackage that = (MedicinePackage) o;
        return count == that.count &&
                Double.compare(that.price, price) == 0 &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        //result = prime * result + count;
        result = prime * result + (count == null ? 0 : count.hashCode());
        result = prime * result + (int) price;
        result = prime * result + (type == null ? 0 : type.hashCode());
        return result;
    }
}
