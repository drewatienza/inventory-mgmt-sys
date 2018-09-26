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


    // VALIDATION MESSAGE
    public static String valName (String name) {
        StringBuilder err = new StringBuilder();
        if (name.isEmpty()) {
            err.append("The form cannot have blank fields.");
        }
        return err.toString();
    }

    public static String valMaxMin (String max, String min) {
        int maxVal = Integer.parseInt(max);
        int minVal = Integer.parseInt(min);
        StringBuilder err = new StringBuilder();
        if (max.isEmpty() || min.isEmpty()) {
            err.append("The form cannot have blank fields.");
        }

        if (maxVal < minVal) {
            err.append("The Min value cannot be greater than the Max value.");
        }

        return err.toString();
    }

    public static String valInv (String inv, String max, String min) {
        int maxVal = Integer.parseInt(max);
        int minVal = Integer.parseInt(min);
        int invValue = Integer.parseInt(inv);
        StringBuilder err = new StringBuilder();
        if (inv.isEmpty()) {
            err.append("The form cannot have blank fields.");
        }

        if (invValue < 1) {
            err.append("The inventory cannot have less than 1.");
        }

        if (invValue < minVal || invValue > maxVal){
            err.append("The inventory value must be between the Min or Max values.");
        }
        return err.toString();
    }

    public static String valPrice (String price) {
        double priceVal = Double.parseDouble(price);
        StringBuilder err = new StringBuilder();
        if (price.isEmpty()) {
            err.append("The form cannot have blank fields.");
        }

        if (price.contains("$")) {
            err.append("The price must be a number and cannot contain the $ sign.");
        }

        if (priceVal <= 0) {
            err.append("The price must be more than $0.");
        }
        return err.toString();
    }
}
