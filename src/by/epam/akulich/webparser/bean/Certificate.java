package by.epam.akulich.webparser.bean;

import java.util.Date;

public class Certificate {
    private String number;
    private Date issueDate;
    private Date expirationDate;
    private DrugRegister register;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public DrugRegister getRegister() {
        return register;
    }

    public void setRegister(DrugRegister register) {
        this.register = register;
    }
}
