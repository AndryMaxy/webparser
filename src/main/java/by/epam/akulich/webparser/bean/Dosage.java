package by.epam.akulich.webparser.bean;

public class Dosage {
    private String value;
    private String period;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dosage)) {
            return false;
        }
        Dosage dosage = (Dosage) o;
        return value.equals(dosage.value) &&
                period.equals(dosage.period);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (value == null ? 0 : value.hashCode());
        result = prime * result + (period == null ? 0 : period.hashCode());
        return result;
    }
}
