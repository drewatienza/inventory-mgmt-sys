package model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {

    private static ObservableList<Part> parts = FXCollections.observableArrayList();
    private static ObservableList<Part> partInventory = FXCollections.observableArrayList();
    private final StringProperty name;
    private final DoubleProperty price;
    private final IntegerProperty inStock;
    private final IntegerProperty min;
    private final IntegerProperty max;
    private final IntegerProperty productID;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    // CONSTRUCTORS
    public Product() {
        name = new SimpleStringProperty();
        price = new SimpleDoubleProperty();
        inStock = new SimpleIntegerProperty();
        min = new SimpleIntegerProperty();
        max = new SimpleIntegerProperty();
        productID = new SimpleIntegerProperty();
        associatedParts = FXCollections.observableArrayList();
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

    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }

    public static ObservableList<Part> getPartInventory() {
        return partInventory;
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

    public void setProdParts (ObservableList<Part> parts) {
        this.parts = parts;
    }


    public void setAssociatedParts(ObservableList<Part> associatedParts) {
        this.associatedParts = associatedParts;
    }

    // VALIDATION
    public static String isValid(
            String name,
            int inv,
            double price,
            int max,
            int min,
            ObservableList<Part> currentParts, String errMessage
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

    // SEARCH PART
    public static int lookupAssociatedPart(String searchPart) {
        boolean foundPart = false;
        int index = 0;
        if (isInteger(searchPart)) {
            for (int i = 0; i < partInventory.size(); i++) {
                if (Integer.parseInt(searchPart) == partInventory.get(i).getPartID()) {
                    index = i;
                    foundPart = true;
                }
            }
        } else {
            for (int i = 0; i < partInventory.size(); i++) {
                searchPart = searchPart.toLowerCase();
                if (searchPart.equals(partInventory.get(i).getPartName().toLowerCase())) {
                    index = i;
                    foundPart = true;
                }
            }
        }

        if (foundPart) {
            return index;
        } else {
            System.out.println("Part was not found.");
            return -1;
        }
    }


    // HELPER METHOD
    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    // REMOVE PART
    public boolean removeAssociatedPart(Part part) {
        return associatedParts.remove(part);
    }


    // ADD PART
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }
}
