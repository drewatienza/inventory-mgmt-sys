package model;

import javafx.beans.property.*;

public abstract class Part {

    private final IntegerProperty partID;
    private final StringProperty name;
    private final DoubleProperty price;
    private final IntegerProperty inStock;
    private final IntegerProperty min;
    private final IntegerProperty max;

    // CONSTRUCTOR
    public Part() {
        partID = new SimpleIntegerProperty();
        name = new SimpleStringProperty();
        price = new SimpleDoubleProperty();
        inStock = new SimpleIntegerProperty();
        min = new SimpleIntegerProperty();
        max = new SimpleIntegerProperty();
    }

    // GETTERS
    public int getPartID() {
        return partID.get();
    }

    public IntegerProperty partIDProperty() {
        return partID;
    }

    public String getPartName() {
        return name.get();
    }

    public StringProperty partNameProperty() {
        return name;
    }

    public double getPartPrice() {
        return price.get();
    }

    public DoubleProperty partPriceProperty() {
        return price;
    }

    public int getPartInStock() {
        return inStock.get();
    }

    public IntegerProperty partInStockProperty() {
        return inStock;
    }

    public int getPartMin() {
        return min.get();
    }

    public IntegerProperty partMinProperty() {
        return min;
    }

    public int getPartMax() {
        return max.get();
    }

    public IntegerProperty partMaxProperty() {
        return max;
    }

    // SETTERS
    public void setPartID(int partID) {
        this.partID.set(partID);
    }

    public void setPartName(String name) {
        this.name.set(name);
    }

    public void setPartPrice(double price) {
        this.price.set(price);
    }

    public void setPartInStock(int inStock) {
        this.inStock.set(inStock);
    }

    public void setPartMin(int min) {
        this.min.set(min);
    }

    public void setPartMax(int max) {
        this.max.set(max);
    }

    // VALIDATION
    public static String isValid (
            String name,
            int inv,
            double price,
            int max,
            int min,
            String errMessage
    ) {
        if (name == null) {
            errMessage = "The name field is required.";
        }
        if (inv < 1) {
            errMessage = "The inventory cannot have less than 1.";
        }
        if (price <= 0) {
            errMessage = "The price must be more than $0.";
        }
        if (inv < min || inv > max) {
            errMessage = "The inventory must be between the Min and Max values.";
        }
        if (max < min) {
            errMessage = "The Min value cannot be greater than the Max value.";
        }
        return  errMessage;
    }
}
