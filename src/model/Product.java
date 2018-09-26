package model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {

    private static ObservableList<Part> parts = FXCollections.observableArrayList();
    private static ObservableList<Part> partInv = FXCollections.observableArrayList();
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

    public ObservableList<Part> getPartInventory() {
        return partInv;
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

    // SEARCH PART
    public static int lookupAssociatedPart(String searchPart) {
        boolean foundPart = false;
        int index = 0;
        if (isInteger(searchPart)) {
            for (int i = 0; i < partInv.size(); i++) {
                if (Integer.parseInt(searchPart) == partInv.get(i).getPartID()) {
                    index = i;
                    foundPart = true;
                }
            }
        } else {
            for (int i = 0; i < partInv.size(); i++) {
                searchPart = searchPart.toLowerCase();
                if (searchPart.equals(partInv.get(i).getPartName().toLowerCase())) {
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
