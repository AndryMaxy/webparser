package by.epam.akulich.webparser.builder;

import by.epam.akulich.webparser.bean.Certificate;
import by.epam.akulich.webparser.bean.DrugRegister;

import java.time.LocalDate;

public class CertificateBuilder {
    private String number;
    private LocalDate issueDate;
    private LocalDate expirationDate;
    private DrugRegister register;

    public CertificateBuilder buildNumber(String number){
        this.number = number;
        return this;
    }

    public CertificateBuilder buildIssueDate(LocalDate date){
        this.issueDate = date;
        return this;
    }

    public CertificateBuilder buildExpirationDate(LocalDate date){
        this.expirationDate = date;
        return this;
    }

    public CertificateBuilder buildRegister(DrugRegister register){
        this.register = register;
        return this;
    }

    public Certificate build(){
        Certificate certificate = new Certificate();
        certificate.setNumber(number);
        certificate.setIssueDate(issueDate);
        certificate.setExpirationDate(expirationDate);
        certificate.setRegister(register);
        return certificate;
    }
}
