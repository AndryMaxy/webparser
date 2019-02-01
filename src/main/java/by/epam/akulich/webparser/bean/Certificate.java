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
}
