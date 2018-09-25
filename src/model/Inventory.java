package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    private static ObservableList<Product> prodInv = FXCollections.observableArrayList();
    private static ObservableList<Part> partInv = FXCollections.observableArrayList();

    private static int partIdCount = 0;
    private static int productIdCount = 0;

    // GETTERS
    public static ObservableList<Product> getProdInv() {
        return prodInv;
    }

    public static ObservableList<Part> getPartInv() {
        return partInv;
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
        prodInv.add(product);
    }

    public static void removeProduct(Product product) {
        prodInv.remove(product);
    }

    public static int searchProd(String sProd) {
        boolean foundProd = false;
        int index = 0;
        if (isInteger(sProd)) {
            for (int i = 0; i < prodInv.size(); i++) {
                if (Integer.parseInt(sProd) == prodInv.get(i).getProductID()) {
                    index = 1;
                    foundProd = true;
                }
            }
        } else {
            for (int i = 0; i < prodInv.size(); i++) {
                if (sProd.equals(prodInv.get(i).getProductName())) {
                    index = i;
                    foundProd = true;
                }
            }
        }

        if (foundProd) {
            return index;
        } else {
            System.out.println("Product was not found.");
            return -1;
        }
    }

//    public static boolean prodDelete(Product product) {
//        boolean foundProd = false;
//        int productID = product.getProductID();
//        for (int i = 0; i < productInventory.size(); i++) {
//            if (productInventory.get(i).getProductID() == productID) {
//                if (!productInventory.get(i).getProductParts().isEmpty()) {
//                    foundProd = true;
//                }
//            }
//        }
//        return foundProd;
//    }

    public static void updateProd (int index, Product product) {
        prodInv.set(index, product);
    }

    // PART
    public static void addPart(Part part) {
        partInv.add(part);
    }

    public static void removePart(Part part) {
        partInv.remove(part);
    }

    public static int searchPart(String sPart) {
        boolean foundPart = false;
        int index = 0;
        if (isInteger(sPart)) {
            for (int i = 0; i < partInv.size(); i++) {
                if (Integer.parseInt(sPart) == partInv.get(i).getPartID()) {
                    index = i;
                    foundPart = true;
                }
            }
        } else {
            for (int i = 0; i < partInv.size(); i++) {
                sPart = sPart.toLowerCase();
                if (sPart.equals(partInv.get(i).getPartName().toLowerCase())) {
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
