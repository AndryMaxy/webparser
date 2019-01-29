package by.epam.akulich.webparser.parser;

import by.epam.akulich.webparser.bean.Drug;
import by.epam.akulich.webparser.bean.DrugGroup;
import by.epam.akulich.webparser.bean.Firm;

public abstract class BaseBuilder {
    //private Drug drug = new Drug();

    public abstract BaseBuilder buildCode(String code);
    public abstract BaseBuilder buildName(String name);
    public abstract BaseBuilder buildProducer(String producer);
    public abstract BaseBuilder buildGroup(DrugGroup drugGroup);
    public abstract BaseBuilder buildAnalog(String analog);
    public abstract BaseBuilder buildFirm(Firm firm);
}
