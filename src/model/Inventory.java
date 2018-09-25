package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    private static ObservableList<Product> productInventory = FXCollections.observableArrayList();
    private static ObservableList<Part> partInventory = FXCollections.observableArrayList();

    private static int partIdCount = 0;
    private static int productIdCount = 0;

    // GETTERS
    public static ObservableList<Product> getProductInventory() {
        return productInventory;
    }

    public static ObservableList<Part> getPartInventory() {
        return partInventory;
    }

    public static int getPartIdCount() {
        partIdCount++;
        return partIdCount;
    }

    public static int getProductIdCount() {
        productIdCount++;
        return productIdCount;
    }

    // PRODUCT
    public static void addProduct(Product product) {
        productInventory.add(product);
    }

    public static void removeProduct(Product product) {
        productInventory.remove(product);
    }

    public static int searchProd(String sProd) {
        boolean foundProduct = false;
        int index = 0;
        if (isInteger(sProd)) {
            for (int i = 0; i < productInventory.size(); i++) {
                if (Integer.parseInt(sProd) == productInventory.get(i).getProductID()) {
                    index = 1;
                    foundProduct = true;
                }
            }
        } else {
            for (int i = 0; i < productInventory.size(); i++) {
                if (sProd.equals(productInventory.get(i).getProductName())) {
                    index = i;
                    foundProduct = true;
                }
            }
        }

        if (foundProduct) {
            return index;
        } else {
            System.out.println("Product was not found.");
            return -1;
        }
    }

    public static void updateProd (int index, Product product) {
        productInventory.set(index, product);
    }

    // PART
    public static void addPart(Part part) {
        partInventory.add(part);
    }

    public static void removePart(Part part) {
        partInventory.remove(part);
    }

    public static int searchPart(String sPart) {
        boolean foundPart = false;
        int index = 0;
        if (isInteger(sPart)) {
            for (int i = 0; i < partInventory.size(); i++) {
                if (Integer.parseInt(sPart) == partInventory.get(i).getPartID()) {
                    index = i;
                    foundPart = true;
                }
            }
        } else {
            for (int i = 0; i < partInventory.size(); i++) {
                sPart = sPart.toLowerCase();
                if (sPart.equals(partInventory.get(i).getPartName().toLowerCase())) {
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
}
