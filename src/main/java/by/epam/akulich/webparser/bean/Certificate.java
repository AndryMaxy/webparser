package by.epam.akulich.webparser.bean;

import java.time.LocalDate;

public class Certificate {
    private String number;
    private LocalDate issueDate;
    private LocalDate expirationDate;
    private MedicineRegister register;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public MedicineRegister getRegister() {
        return register;
    }

    public void setRegister(MedicineRegister register) {
        this.register = register;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Certificate)) {
            return false;
        }
        Certificate that = (Certificate) o;
        return number.equals(that.number) &&
                issueDate.equals(that.issueDate) &&
                expirationDate.equals(that.expirationDate) &&
                register == that.register;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (number == null ? 0 : number.hashCode());
        result = prime * result + (issueDate == null ? 0 : issueDate.hashCode());
        result = prime * result + (expirationDate == null ? 0 : expirationDate.hashCode());
        result = prime * result + (register == null ? 0 : register.hashCode());
        return result;
    }
}
