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
    private TableView<Part>  MainParts;
    @FXML
    private TableColumn<Part, Integer> MaintPartIdColumn;
    @FXML
    private TableColumn<Part, String> MainPartNameColumn;
    @FXML
    private TableColumn<Part, Integer> MainPartInvColumn;
    @FXML
    private TableColumn<Part, Double> MainPartPriceColumn;
    @FXML
    private TextField PartSearchField;

    private static int modifyPartIndex;

    public static int partToModifyIndex() {
        return modifyPartIndex;
    }

    // PRODUCT TABLEVIEW
    @FXML
    private TableView<Product>  MainProducts;
    @FXML
    private TableColumn<Product, Integer> MaintProductIdColumn;
    @FXML
    private TableColumn<Product, String> MainProductNameColumn;
    @FXML
    private TableColumn<Product, Integer> MainProductInvColumn;
    @FXML
    private TableColumn<Product, Double> MainProductPriceColumn;
    @FXML
    private TextField ProductSearchField;

    private static int modifyProductIndex;

    public static int productToModifyIndex() {
        return modifyProductIndex;
    }

    // Parts Update Table View
    public void updatePartsTableView() {
        MainParts.setItems(getPartInventory());
    }
    // Update Products Table View
    public void updateProductsTableView() {
        MainProducts.setItems(getProductInventory());
    }


    // PARTS SECTION
    // Search Part
    @FXML
    void MainScrPartSearch(ActionEvent actionEvent) {
        String partSearch = PartSearchField.getText();
        int partIndex = -1;
        if (Inventory.lookupPart(partSearch) == -1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Search Problem");
            alert.setHeaderText("Part Not Found");
            alert.setContentText("The part you entered does not match any current part!");
            alert.showAndWait();
        } else {
            partIndex = Inventory.lookupPart(partSearch);
            Part tempPart = Inventory.getPartInventory().get(partIndex);
            ObservableList<Part> tempPartList = FXCollections.observableArrayList();
            tempPartList.add(tempPart);
            MainParts.setItems(tempPartList);
        }
    }

    // Add Part
    @FXML
    void MainScrAddPart(ActionEvent actionEvent) throws IOException {
        Parent addParts = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        Scene addPartScene = new Scene (addParts, 550, 500);
        Stage addPartWindow = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        addPartWindow.setScene(addPartScene);
        addPartWindow.show();
    }

    // Modify Part
    @FXML
    void MainScrModifyPart(ActionEvent actionEvent) throws IOException {
        Part modifyPart = MainParts.getSelectionModel().getSelectedItem();
        modifyPartIndex = getPartInventory().indexOf(modifyPart);
        Parent modPartParent = FXMLLoader.load(getClass().getResource("ModifyPart.fxml"));
        Scene modPartScene = new Scene (modPartParent, 550, 500);
        Stage modPartWindow = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        modPartWindow.setScene(modPartScene);
        modPartWindow.show();
    }

    // Delete Part
    @FXML
    void MainScrDelPart(ActionEvent actionEvent) {
        Part part = MainParts.getSelectionModel().getSelectedItem();
        if (validatePartRemoval(part)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Part Deletion Error!");
            alert.setHeaderText("Part cannot be deleted!");
            alert.setContentText("The part you are trying to delete is currently being used in one or more products.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Part Deletion!");
            alert.setHeaderText("Deletion Confirmation");
            alert.setContentText("Please confirm that you would like to delete " + part.getPartName() + "?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                removePart(part);
                updatePartsTableView();
                System.out.println("Part " + part.getPartName() + " has been deleted.");
            } else {
                System.out.println("Part " + part.getPartName() + " was not deleted.");
            }
        }
    }


    // PRODUCTS SECTION
    // Search Product
    @FXML
    void MainScrProductSearch(ActionEvent actionEvent) {
        String productSearch = ProductSearchField.getText();
        int prodIndex = -1;
        if (Inventory.lookupProduct(productSearch) == -1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Search Problem");
            alert.setHeaderText("Part Not Found");
            alert.setContentText("The part you entered does not match any current product!");
            alert.showAndWait();
        } else {
            prodIndex = Inventory.lookupProduct(productSearch);
            Product tempProduct = Inventory.getProductInventory().get(prodIndex);
            ObservableList<Product> tempProductList = FXCollections.observableArrayList();
            tempProductList.add(tempProduct);
            MainProducts.setItems(tempProductList);
        }
    }

    // Add Product
    @FXML
    void MainScrProductAdd(ActionEvent actionEvent) throws IOException {
        Parent addProducts = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        Scene addProductScene = new Scene(addProducts, 1160,550);
        Stage addProductWindow = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        addProductWindow.setScene(addProductScene);
        addProductWindow.show();
    }

    // Modify Product
    @FXML
    void MainScrProductMod(ActionEvent actionEvent) throws IOException {
        Product modifyProduct = MainProducts.getSelectionModel().getSelectedItem();
        modifyProductIndex = getProductInventory().indexOf(modifyProduct);
        Parent modProdParent = FXMLLoader.load(getClass().getResource("ModifyProduct.fxml"));
        Scene modProdScene = new Scene(modProdParent, 1160, 550);
        Stage modProdWindow = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        modProdWindow.setScene(modProdScene);
        modProdWindow.show();
    }

    // Delete Product
    @FXML
    void MainScrProductDel(ActionEvent actionEvent) {
        Product product = MainProducts.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Product Deletion!");
        alert.setHeaderText("Deletion Confirmation");
        alert.setContentText("Please confirm that you would like to delete " + product.getProductName() + "?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            removeProduct(product);
            updateProductsTableView();
            System.out.println("Part " + product.getProductName() + " has been deleted.");
        } else {
            System.out.println("Part " + product.getProductName() + " was not deleted.");
        }
    }


    // EXIT BUTTON
    @FXML
    private void ExitBtn(ActionEvent actionEvent) {
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

    }
}
