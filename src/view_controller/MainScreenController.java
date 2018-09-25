package view_controller;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Part;
import model.Product;
import model.Inventory;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static model.Inventory.*;

public class MainScreenController implements Initializable {

    // PARTS TABLEVIEW
    @FXML
    private TableView<Part>  mainParts;
    @FXML
    private TableColumn<Part, Integer> mainPartIdColumn;
    @FXML
    private TableColumn<Part, String> mainPartNameColumn;
    @FXML
    private TableColumn<Part, Integer> mainPartInvColumn;
    @FXML
    private TableColumn<Part, Double> mainPartPriceColumn;
    @FXML
    private TextField partSearchField;

    private static int modifyPartIndex;

    public static int partToModifyIndex() {
        return modifyPartIndex;
    }

    // PRODUCT TABLEVIEW
    @FXML
    private TableView<Product>  mainProds;
    @FXML
    private TableColumn<Product, Integer> mainProdIdColumn;
    @FXML
    private TableColumn<Product, String> mainProdNameColumn;
    @FXML
    private TableColumn<Product, Integer> mainProdInvColumn;
    @FXML
    private TableColumn<Product, Double> mainProdPriceColumn;
    @FXML
    private TextField prodSearchField;

    private static int modifyProductIndex;

    public static int productToModifyIndex() {
        return modifyProductIndex;
    }

    // Parts Update Table View
    public void updatePartsTV() {
        mainParts.setItems(getPartInventory());
    }
    // Update Products Table View
    public void updateProductsTV() {
        mainProds.setItems(getProductInventory());
    }


    // PARTS SECTION
    // Search Part
    @FXML
    void mainScrPartSearch(ActionEvent actionEvent) {
        String partSearch = partSearchField.getText();
        int partIndex = -1;
        if (Inventory.searchPart(partSearch) == -1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SEARCH ERROR");
            alert.setHeaderText("Part Not Found");
            alert.setContentText("The part you entered was not found.");
            alert.showAndWait();
        } else {
            partIndex = Inventory.searchPart(partSearch);
            Part tempPart = Inventory.getPartInventory().get(partIndex);
            ObservableList<Part> tempPartList = FXCollections.observableArrayList();
            tempPartList.add(tempPart);
            mainParts.setItems(tempPartList);
        }
    }

    // Add Part
    @FXML
    void mainScrAddPart(ActionEvent actionEvent) throws IOException {
        Parent addParts = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        Scene addPartScene = new Scene (addParts, 550, 500);
        Stage addPartWindow = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        addPartWindow.setScene(addPartScene);
        addPartWindow.show();
    }

    // Modify Part
    @FXML
    void mainScrModifyPart(ActionEvent actionEvent) throws IOException {
        Part modifyPart = mainParts.getSelectionModel().getSelectedItem();
        if (modifyPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Part Modification Error");
            alert.setContentText("You have not selected a part to modify.");
            alert.showAndWait();
        } else {
            modifyPartIndex = getPartInventory().indexOf(modifyPart);
            Parent modPartParent = FXMLLoader.load(getClass().getResource("ModifyPart.fxml"));
            Scene modPartScene = new Scene(modPartParent, 550, 500);
            Stage modPartWindow = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            modPartWindow.setScene(modPartScene);
            modPartWindow.show();
        }
    }

    // Delete Part
    @FXML
    void mainScrDelPart(ActionEvent actionEvent) {
        Part part = mainParts.getSelectionModel().getSelectedItem();

//        MIGHT DELETE
        if (part == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Part Deletion Error");
            alert.setContentText("You have not selected a part to delete.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("PART DELETION");
            alert.setHeaderText("Deletion Confirmation");
            alert.setContentText("Please confirm that you would like to delete " + part.getPartName() + "?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                removePart(part);
                updatePartsTV();
            }
        }
    }


    // PRODUCTS SECTION
    // Search Product
    @FXML
    void mainScrProdSearch(ActionEvent actionEvent) {
        String prodSearch = prodSearchField.getText();
        int prodIndex = -1;
        if (Inventory.searchProd(prodSearch) == -1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SEARCH ERROR");
            alert.setHeaderText("Product Not Found");
            alert.setContentText("The product you entered was not found.");
            alert.showAndWait();
        } else {
            prodIndex = Inventory.searchProd(prodSearch);
            Product tempProduct = Inventory.getProductInventory().get(prodIndex);
            ObservableList<Product> tempProductList = FXCollections.observableArrayList();
            tempProductList.add(tempProduct);
            mainProds.setItems(tempProductList);
        }
    }

    // Add Product
    @FXML
    void mainScrProdAdd(ActionEvent actionEvent) throws IOException {
        Parent addProducts = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        Scene addProductScene = new Scene(addProducts, 1160,550);
        Stage addProductWindow = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        addProductWindow.setScene(addProductScene);
        addProductWindow.show();
    }

    // Modify Product
    @FXML
    void mainScrProdMod(ActionEvent actionEvent) throws IOException {
        Product modifyProduct = mainProds.getSelectionModel().getSelectedItem();
        if (modifyProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Product Modification Error");
            alert.setContentText("You have not selected a product to modify.");
            alert.showAndWait();
        }
        modifyProductIndex = getProductInventory().indexOf(modifyProduct);
        Parent modProdParent = FXMLLoader.load(getClass().getResource("ModifyProduct.fxml"));
        Scene modProdScene = new Scene(modProdParent, 1160, 550);
        Stage modProdWindow = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        modProdWindow.setScene(modProdScene);
        modProdWindow.show();
    }

    // Delete Product
    @FXML
    void mainScrProdDel(ActionEvent actionEvent) {
        Product product = mainProds.getSelectionModel().getSelectedItem();
        if (product == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Product Deletion Error");
            alert.setContentText("You have not selected a product to delete.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("PRODUCT DELETION");
            alert.setHeaderText("Deletion Confirmation");
            alert.setContentText("Please confirm that you would like to delete " + product.getProductName() + "?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                removeProduct(product);
                updateProductsTV();
            }
        }
    }


    // EXIT BUTTON
    @FXML
    private void exitBtn(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initModality(Modality.NONE);
        alert.setTitle("EXIT");
        alert.setHeaderText("Exit Confirmation!");
        alert.setContentText("Please confirm that you would like to exit.");
        Optional<ButtonType> confirm = alert.showAndWait();

        if (confirm.get() == ButtonType.OK) {
            System.exit(0);
        } else {
            System.out.println("Exit cancelled.");
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainPartIdColumn.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        mainPartNameColumn.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        mainPartInvColumn.setCellValueFactory(cellData -> cellData.getValue().partInStockProperty().asObject());
        mainPartPriceColumn.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject());
        mainProdIdColumn.setCellValueFactory(cellData -> cellData.getValue().productIDProperty().asObject());
        mainProdNameColumn.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        mainProdInvColumn.setCellValueFactory(cellData -> cellData.getValue().productInStockProperty().asObject());
        mainProdPriceColumn.setCellValueFactory(cellData -> cellData.getValue().productPriceProperty().asObject());
        updatePartsTV();
        updateProductsTV();
    }
}
