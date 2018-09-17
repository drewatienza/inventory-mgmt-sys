package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public abstract class Part {

    private final IntegerProperty partID;
    private final StringProperty name;
    private final DoubleProperty price;
    private final IntegerProperty inStock;
    private final IntegerProperty min;
    private final IntegerProperty max;

    // CONSTRUCTOR
    public Part(IntegerProperty partID,
                StringProperty name,
                DoubleProperty price,
                IntegerProperty inStock,
                IntegerProperty min,
                IntegerProperty max) {
        this.partID = partID;
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.min = min;
        this.max = max;
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
}
