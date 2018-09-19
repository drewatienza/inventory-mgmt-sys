package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {

    private static ObservableList<Part> parts = FXCollections.observableArrayList();
    private final StringProperty name;
    private final DoubleProperty price;
    private final IntegerProperty inStock;
    private final IntegerProperty min;
    private final IntegerProperty max;
    private final IntegerProperty productID;

    // CONSTRUCTORS
    public Product(StringProperty name,
                   DoubleProperty price,
                   IntegerProperty inStock,
                   IntegerProperty min,
                   IntegerProperty max,
                   IntegerProperty productID) {
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.min = min;
        this.max = max;
        this.productID = productID;
    }

    // GETTERS

    public static ObservableList<Part> getProductParts() {
        return parts;
    }

    public String getProductName() {
        return name.get();
    }

    public StringProperty productNameProperty() {
        return name;
    }

    public double getProductPrice() {
        return price.get();
    }

    public DoubleProperty productPriceProperty() {
        return price;
    }

    public int getProductInStock() {
        return inStock.get();
    }

    public IntegerProperty productInStockProperty() {
        return inStock;
    }

    public int geProducttMin() {
        return min.get();
    }

    public IntegerProperty productMinProperty() {
        return min;
    }

    public int getProductMax() {
        return max.get();
    }

    public IntegerProperty productMaxProperty() {
        return max;
    }

    public int getProductID() {
        return productID.get();
    }

    public IntegerProperty productIDProperty() {
        return productID;
    }

    // SETTERS
    public static void setProductParts(ObservableList<Part> parts) {
        Product.parts = parts;
    }

    public void setProductName(String name) {
        this.name.set(name);
    }

    public void setProductPrice(double price) {
        this.price.set(price);
    }

    public void setProductInStock(int inStock) {
        this.inStock.set(inStock);
    }

    public void setProductMin(int min) {
        this.min.set(min);
    }

    public void setProductMax(int max) {
        this.max.set(max);
    }

    public void setProductID(int productID) {
        this.productID.set(productID);
    }
}
