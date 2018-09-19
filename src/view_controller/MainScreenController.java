package view_controller;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Part;
import model.Product;
import model.Inventory;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

    // PARTS
    @FXML
    void MainScrPartSearch (javafx.event.ActionEvent event) throws IOException {
        String partSearch = PartSearchField.getText();
        int partIndex = -1;
        if (Inventory.lookupPart(partSearch) == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
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

    @FXML
    void MainScrAddPart(javafx.event.ActionEvent event) throws IOException {
        Parent addParts = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        Scene scene = new Scene (addParts, 550, 500);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    // PRODUCTS
    void MainScrProductSearch (javafx.event.ActionEvent event) throws IOException {
        String productSearch = ProductSearchField.getText();
        int prodIndex = -1;
        if (Inventory.lookupProduct(productSearch) == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
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

    @FXML
    void MainScrProductAdd(javafx.event.ActionEvent event) throws IOException {
        Parent addProducts = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        Scene scene = new Scene(addProducts, 1160,550);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
