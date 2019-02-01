package by.epam.akulich.webparser.builder;

import by.epam.akulich.webparser.bean.Certificate;
import by.epam.akulich.webparser.bean.MedicineRegister;

import java.time.LocalDate;

public class CertificateBuilder {
    private String number;
    private LocalDate issueDate;
    private LocalDate expirationDate;
    private MedicineRegister register;

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

    public CertificateBuilder buildRegister(MedicineRegister register){
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
