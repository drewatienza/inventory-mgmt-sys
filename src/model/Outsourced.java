package model;

import javafx.beans.property.StringProperty;

public class Outsourced {

    private final StringProperty companyName;

    public Outsourced(StringProperty companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName.get();
    }

    public StringProperty companyNameProperty() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }
}
